package com.g3way.sicims.interceptor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.g3way.sicims.util.common.SessionUtil;
import egovframework.com.cmm.util.EgovUserDetailsHelper;

@Aspect
@Component
public class SessionUserAspect {
	@SuppressWarnings("unused")
	private static final Logger LOG = LoggerFactory.getLogger(SessionUserAspect.class);

	public void beforeMethod(JoinPoint jp) throws Exception {
		Object[] iterator = jp.getArgs();
		if(iterator != null && iterator.length > 0 ){
		    for(Object arg : iterator){
		       	//select 함수에서 파라미터로 map 넘길때
		    	if(arg == null ){
		    		continue;
		    	} else if(arg instanceof  HashMap){
		       		Method setter = arg.getClass().getMethod("put", Object.class, Object.class);
					/* edited by dykim 2023-10-23
					 * 생성자만 데이터를 수정가능하기 위해 "updusrId" 사용하므로
					 */
		       		//setter.invoke(arg, "updusrId", SessionUtil.getLoginUserId());

					setter.invoke(arg, "sessionAuthorities", EgovUserDetailsHelper.getAuthorities());

					//select에 page 값이 없는경우 기본값 적용
					if(((HashMap<?, ?>)arg).get("page") == null){
						setter.invoke(arg, "page", 1);
					}

					//select에 rows 값이 없는경우 기본값 적용
					if(((HashMap<?, ?>)arg).get("rows") == null){
						setter.invoke(arg, "rows", 10);
					}
		       	} else {
		       		try {
			       		Field idField = getField(arg.getClass(), "updusrId");
			       		if(idField != null){
			        		Method setter = arg.getClass().getMethod("setUpdusrId", idField.getType());
							setter.invoke(arg, SessionUtil.getLoginUserId());
			       		}

			       		Field ipField = getField(arg.getClass(), "updusrIp");
			       		if(ipField != null){
			        		Method setter = arg.getClass().getMethod("setUpdusrIp", ipField.getType());
							setter.invoke(arg, SessionUtil.getLoginUserIp());
			       		}

			       		Field sessionAuthoritiesField = getField(arg.getClass(), "sessionAuthorities");
			       		if(sessionAuthoritiesField != null && EgovUserDetailsHelper.isAuthenticated()){
			        		Method setter = arg.getClass().getMethod("setSessionAuthorities", sessionAuthoritiesField.getType());
							setter.invoke(arg, EgovUserDetailsHelper.getAuthorities());
			       		}
		      		} catch(IllegalAccessException e){
		       			LOG.error("세션의 updusrId 설정중 오류가 발생했습니다.");
		       			LOG.error(e.getMessage());
		       		}
		       	}
		    }
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
}