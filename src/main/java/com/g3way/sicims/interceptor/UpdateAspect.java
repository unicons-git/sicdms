package com.g3way.sicims.interceptor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import com.g3way.sicims.services.sicims900Sys.log.LogService;
import egovframework.com.cmm.service.EgovProperties;

@Aspect
@Component
public class UpdateAspect {
	private static final Logger LOG = LoggerFactory.getLogger(UpdateAspect.class);

	@Autowired private LogService	logService;

	@Autowired @Qualifier("sqlSession")
    private SqlSessionFactory sqlSession;

	@SuppressWarnings("unchecked")
	public void beforeMethod(JoinPoint jp) throws Exception {
		try {
			//수정로그 입력 여부 값이 Y 가 이닌경우
			if(!"Y".equals(EgovProperties.getProperty("Globals.updtLog"))) return;

			List<HashMap<String, Object>> list = null;
			HashMap<String, Object> param = new HashMap<String, Object>();

			Object[] iterator = jp.getArgs();
			if(iterator != null && iterator.length > 0 ){
			    for(Object arg : iterator){
			       	//select 함수에서 파라미터로 map 넘길때
			    	if(arg == null ){
			    		continue;
			    	} else if(arg instanceof  HashMap){
			       		Method setter = arg.getClass().getMethod("put", Object.class, Object.class);
			       		Method getter = arg.getClass().getMethod("get", Object.class);
			       		//테이블명 여부 체크
			       		if(getter != null && getter.invoke(arg, "tblNm") != null ) {
			       			//arg 객체를 Map 객체로 변환
			       			param = (HashMap<String, Object>) arg;
			        		//테이블 데이터 수정전 해당 테이블 정보 조회
				       		list = logService.getTblDataList(param);
							setter.invoke(arg, "updtBeforeDataList", list);

							return;
			       		}
			       	} else if(!(arg instanceof List)){
			       		Method tblNmGetter = arg.getClass().getMethod("getTblNm");
			       		if(tblNmGetter != null) {
				        	Object tblNm = tblNmGetter.invoke(arg);
				        	//수정테이블을 입력한경우 수정 로그 입력
				        	if(tblNm != null && tblNm instanceof  String && !"".equals(tblNm.toString())) {
				        		//vo  객체를 Map 객체로 변환
				        		param = (HashMap<String, Object>) PropertyUtils.describe(arg);
				        		//테이블 데이터 수정전 해당 테이블 정보 조회
				        		list = logService.getTblDataList(param);

				        		//수정전 데이터 저장
				        		Field updtBeforeDataList = getField(arg.getClass(), "updtBeforeDataList");
						   		if(updtBeforeDataList != null){
						    		Method setter = arg.getClass().getMethod("setUpdtBeforeDataList", updtBeforeDataList.getType());
						    		if(setter != null ) {
						    			setter.invoke(arg, list);
						    		}
						   		}
						   		return;
				        	}
			       		}
			       	}
		       	}
		    }
		}catch(IllegalAccessException e){
			LOG.debug("수정로그 이전 데이터 입력중 오류가 발생했습니다.");
		}catch(Exception e) {
			LOG.debug("수정로그 이전 데이터 입력중 오류가 발생했습니다.");
		}
	}


	@SuppressWarnings("unchecked")
	public void afterMethod(JoinPoint jp) throws Exception {
		try {
			if(!"Y".equals(EgovProperties.getProperty("Globals.updtLog"))) return;

			HashMap<String, Object> param = new HashMap<String, Object>();
			SqlSession session = null;

			if(jp.getTarget() != null) {
				jp.getTarget().getClass().getCanonicalName();
			}

        	session = sqlSession.openSession();

			List<HashMap<String, Object>> updtAfterDataList = null;
			List<HashMap<String, Object>> updtBeforeDataList = null;

			Object[] iterator = jp.getArgs();
			if(iterator != null && iterator.length > 0 ){
			    for(Object arg : iterator){
			       	//select 함수에서 파라미터로 map 넘길때
			    	if(arg == null ){
			    		continue;
			    	} else if(arg instanceof  HashMap){
			    		arg.getClass().getMethod("put", Object.class, Object.class);
			       		Method getter = arg.getClass().getMethod("get", Object.class);
			       		//테이블명 여부 체크
			       		if(getter != null && getter.invoke(arg, "tblNm") != null ) {
			       			if(getter.invoke(arg , "updtBeforeDataList")  != null){
			    				updtBeforeDataList = (List<HashMap<String, Object>>) ((HashMap<String, Object>)arg).get("updtBeforeDataList");
				    		}

			       			//arg 객체를 Map 객체로 변환
			       			param = (HashMap<String, Object>) arg;

			       			//수정후 데이터 조회 sql 문 생성
							String sql =  getSql(session, "getTblDataList", param);
							param.put("sqlWhrCn", sql);

			        		//테이블 데이터 수정후 해당 테이블 정보 조회
			       			updtAfterDataList = logService.getTblDataList(param);
			       		}
			       	} else if(!(arg instanceof List)){
			    		Method tblNmGetter = arg.getClass().getMethod("getTblNm");
			       		if(tblNmGetter != null) {
			        		//vo  객체를 Map 객체로 변환
			        		param = (HashMap<String, Object>) PropertyUtils.describe(arg);

			        		//테이블 데이터 수정후 해당 테이블 정보 조회
			        		updtAfterDataList = logService.getTblDataList(param);

			        		//수정후 데이터 조회 sql 문 생성
			        		String sql =  getSql(session, "getTblDataList", param);
			        		param.put("sqlWhrCn", sql);

			        		//수정후 데이터 가져오기
					   		Field updtBeforeDataListField = getField(arg.getClass(), "updtBeforeDataList");
					   		if(updtBeforeDataListField != null){
					    		Method getter = arg.getClass().getMethod("getUpdtBeforeDataList");
					    		if(getter != null) {
					        		Object temp = getter.invoke(arg);
					        		if(temp != null && temp instanceof  ArrayList) {
					        			updtBeforeDataList = (List<HashMap<String, Object>>) temp;
					        		}
					    		}
					   		}
			       		}
			       	}
			    }
			}


			//수정 로그 입력
			logService.insertCmMlog(param, updtBeforeDataList, updtAfterDataList);
		}
		catch(IllegalAccessException e){
   			LOG.debug("테이블 수정 로그 입력중 오류가 발생했습니다.");
   		}
   		catch(Exception e){
   			LOG.debug("테이블 수정 로그 입력중 오류가 발생했습니다.");
   		}
	}

	private Field getField(Class<?> type, String fileName) {
		Field returnField = null;
		List<Field> fields = getAllFields(type, 0);

		for(Field field :fields){
	    	if(field.getName().equals(fileName)){
	    		returnField = field;
	    	}
	    }

		return returnField;
	}

	private List<Field> getAllFields(Class<?> type, int depth) {
		List<Field> fields  = new ArrayList<Field>();

		fields.addAll(Arrays.asList(type.getDeclaredFields()));
		//재귀함수 호출시 20번 이내만 작동하도록 제한 무한루프 방지
	    if (type.getSuperclass() != null && depth < 20) {
	    	fields.addAll(getAllFields(type.getSuperclass(), depth+1));
	    }

	    return fields;
	}

	private String getSql(SqlSession session, String id, HashMap<String, Object> params){
		String sql = "";
		BoundSql boundSql = session.getConfiguration().getMappedStatement(id).getBoundSql(params);
		if(boundSql != null ) {
			// 쿼리실행시 맵핑되는 파라미터를 구한다
			Object param = boundSql.getParameterObject();
			// 쿼리문을 가져온다(이 상태에서의 쿼리는 값이 들어갈 부분에 ?가 있다)
			sql = boundSql.getSql();
			if( sql != null ) {
				try {
					// 바인딩 파라미터가 없으면
					if (param == null && sql != null ) {
						sql = sql.replaceFirst("\\?", "''");
						return sql;
					}

					// 해당 파라미터의 클래스가 Integer, Long, Float, Double 클래스일 경우
					if (param instanceof Integer || param instanceof Long || param instanceof Float || param instanceof Double) {
						sql = sql.replaceFirst("\\?", param.toString());
					}
					// 해당 파라미터의 클래스가 String인 경우
					else if (param instanceof String) {
						sql = sql.replaceFirst("\\?", "'" + param + "'");
					}
					// 해당 파라미터의 클래스가 Map인 경우
					else if (param instanceof Map) {
						List<ParameterMapping> paramMapping = boundSql.getParameterMappings();
						for (ParameterMapping mapping : paramMapping) {
							String propValue = mapping.getProperty();
							Object value = ((Map) param).get(propValue);
							if(boundSql.hasAdditionalParameter(propValue)) {
								value = boundSql.getAdditionalParameter(propValue);  // 해당 추가 동적인수의 Value를 가져옴
				            }
							if (value == null) {
								continue;
							}

							if (value instanceof String) {
								sql = sql.replaceFirst("\\?", "'" + value + "'");
							} else {
								sql = sql.replaceFirst("\\?", value.toString());
							}
						}
					}
					// 해당 파라미터의 클래스가 사용자 정의 클래스인 경우
					else {
						List<ParameterMapping> paramMapping = boundSql.getParameterMappings();
						Class<? extends Object> paramClass = param.getClass();

						for (ParameterMapping mapping : paramMapping) {
							String propValue = mapping.getProperty();
							Field field;
							field = paramClass.getDeclaredField(propValue);
							field.setAccessible(true);
							Class<?> javaType = mapping.getJavaType();
							if (String.class == javaType) {
								sql = sql.replaceFirst("\\?", "'" + field.get(param) + "'");
							} else {
								sql = sql.replaceFirst("\\?", field.get(param).toString());
							}
						}
					}
				} catch (NoSuchFieldException | SecurityException e) {
					LOG.debug("테이블 수정 쿼리 생성중 오류가 발생했습니다.");
				} catch (Exception e) {
					LOG.debug("테이블 수정 쿼리 생성중 오류가 발생했습니다.");
				}
			}
		}
		return sql;
    }
}