package com.g3way.sicims.util.common;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.g3way.sicims.exception.SicimsException;
import com.g3way.sicims.util.encrypt.RsaUtil;

/**
 * @Package Name	: com.g3way.util.common
 * @Class Name		: CommonUtil.java
 * @Description		: 공통 기능 유틸
 * @Modification	:
 * @-----------------------------------------------------------------
 * @ Date			| Name			| Comment
 * @-------------  -----------------   ------------------------------
 * @ 2021. 2. 3.	| JunHee		| 최초생성
 * @-----------------------------------------------------------------
 * @Author			: JunHee
 * @Since			: 2021. 2. 3. 오전 10:18:02
 * @Version			: 1.0
 */
public class CommonUtil {

	@SuppressWarnings("unused")
	private static final Logger LOG = LoggerFactory.getLogger(CommonUtil.class);


	@SuppressWarnings("unchecked")
	public static <T> List<T> deepCoplyList(List<T> list){
		if(list == null || list.size() == 0){
			return list;
		}
		else {
			List<T> returnList = new ArrayList<T>();
			for(T listVo : list){
				try {
					T tempVo = (T) listVo.getClass().newInstance();
					if(tempVo instanceof String){
						tempVo = listVo;
					}
					else {
						BeanUtils.copyProperties(tempVo, listVo);
					}
					returnList.add(tempVo);
				} catch (IllegalAccessException e) {
					LOG.error("객체 복사중 오류가 발생했습니다.{IllegalAccessException}");
				} catch (InvocationTargetException e) {
					LOG.error("객체 복사중 오류가 발생했습니다.{InvocationTargetException}");
				} catch (InstantiationException e) {
					LOG.error("객체 복사중 오류가 발생했습니다.{InstantiationException}");
				}
			}
			return returnList;
		}
     }

	public static <T> T[] deepCoplyList(T[] list){
		if(list == null || list.length == 0){
			return list;
		}
		else {
			T[] returnList = null;
			returnList = list.clone();
			return returnList;
		}
     }

	public static <T> List<HashMap<String, Object>> convertListToMap(Collection<T> target, boolean camelToSnakeConvertYn ) {
	        List<HashMap<String, Object>> resultList =new ArrayList<HashMap<String, Object>>();
	        for (T element : target) {
	        	HashMap<String,Object> resultMap =new HashMap<String,Object>();
	            //Field[] fieldList = element.getClass().getDeclaredFields();
	            List<Field> fieldList = getAllFields(element.getClass(), 0);

	            if (fieldList !=null && fieldList.size() >0) {
	                try {
	                    for (Field  field : fieldList) {
	                        String curInsName = field.getName();
	                        //Field field = element.getClass().getDeclaredField(curInsName);
	                        field.setAccessible(true);
	                        Object targetValue = field.get(element);
	                        if(camelToSnakeConvertYn){
	                        	resultMap.put(stringCamelToSnake(curInsName), targetValue);
	                        }
	                        else {
	                        	resultMap.put(curInsName, targetValue);
	                        }
	                    }
	                    resultList.add(resultMap);

	                } catch (IllegalArgumentException e) {
	                	LOG.error("객체 변환중  오류가 발생했습니다.{IllegalArgumentException}");
					} catch (IllegalAccessException e) {
						LOG.error("객체 변환중  오류가 발생했습니다.{IllegalAccessException}");
					}
	            }
	        }
	        return resultList;
	 }

	public static String stringCamelToSnake(String str) {

		  // Empty String
	        String result = "";

	        // Append first character(in lower case)
	        // to result string
	        char c = str.charAt(0);
	        result = result + Character.toLowerCase(c);

	        // Tarverse the string from
	        // ist index to last index
	        for (int i = 1; i < str.length(); i++) {

	            char ch = str.charAt(i);

	            // Check if the character is upper case
	            // then append '_' and such character
	            // (in lower case) to result string
	            if (Character.isUpperCase(ch)) {
	                result = result + '_';
	                result
	                    = result
	                      + Character.toLowerCase(ch);
	            }

	            // If the character is lower case then
	            // add such character into result string
	            else {
	                result = result + ch;
	            }
	        }

	        // return the result
	        return result;
	 }

	public static Field getField(Class<?> type, String fileName) {
			Field returnField = null;
			List<Field> fields = getAllFields(type, 0);

			for(Field field :fields){
		    	if(field.getName().equals(fileName)){
		    		returnField = field;
		    	}
		    }

			return returnField;
		}

	public static List<Field> getAllFields(Class<?> type, int depth) {
			List<Field> fields  = new ArrayList<Field>();

			fields.addAll(Arrays.asList(type.getDeclaredFields()));
			//재귀함수 호출시 20번 이내만 작동하도록 제한 무한루프 방지
		    if (type.getSuperclass() != null && depth < 20) {
		    	fields.addAll(getAllFields(type.getSuperclass(), depth+1));
		    }

		    return fields;
	 }

	public static String getRequestParams(HttpServletRequest request) {
		String returnParam = "";
		if(request == null )  return returnParam;

		Enumeration<?> parm = request.getParameterNames();
		if(parm != null ) {
			String name = "";
			String value = "";
			String param = "";
			int cnt = 0;
			while (parm.hasMoreElements()) {
				name = (String) parm.nextElement();
				value = request.getParameter(name);
				//256바이트고 빈칸이 없는경우
				if(value != null &&  value.length() == 512 && value.indexOf(" ") == -1) {
					try {
						value = RsaUtil.decryptRsa(value);
					}catch (SicimsException e) {
						LOG.debug("파라미터 복호화중 오류");
					}
				}
				if(cnt==0){
					param += "?";
				}else{
					param += "&";
				}
				param += name + "=";
				param += value;
				cnt++;
	  		}
			return param;
		}
		return returnParam;
	}

	/**
	 * @Method Name		: setLogParms
	 * @Class Name		: CommonUtil
	 * @Description		: 수정 로그에서 사용하는 변수를 dao에 넘기기 전 추가한다.
	 * @Author			: JunHee
	 * @Since			: 2021. 4. 21. 오후 5:45:16
	 * @param param		: vo 또는 Map
	 * @param mlogScrnNm	수정화면 이름
	 * @param tblNm		수정 테이블명
	 * @param whrParam	수정 테이블 where 조건 배열
	 * ex) setLogParms(loginVo, "사용자 정보 수정", "cm_user_t", new Object[]{"text1"})
	 */
	public static void  setLogParms(Object param, String mlogScrnNm, String tblNm) {

		Method setter = null;

		if(param != null ) {
			try {
				if(param instanceof  Map){
					setter = param.getClass().getMethod("put", Object.class, Object.class);
		       		if(setter != null) {
			       		//화면이름
		       			setter.invoke(param, "mlogScrnNm", mlogScrnNm);

			       		//테이블명
		       			setter.invoke(param, "tblNm", tblNm);
					}
		       	}
				else {
		       		//화면이름
					Field mlogScrnNmField = getField(param.getClass(), "mlogScrnNm");
					if(mlogScrnNmField != null ) {
						setter = param.getClass().getMethod("setMlogScrnNm", mlogScrnNmField.getType());
						if(setter != null ) {
			    			setter.invoke(param, mlogScrnNm);
			    		}
					}

		       		//테이블명
					Field tblNmField = getField(param.getClass(), "tblNm");
					if(tblNmField != null ) {
						setter = param.getClass().getMethod("setTblNm", tblNmField.getType());
						if(setter != null ) {
			    			setter.invoke(param, tblNm);
			    		}
					}
				}
			}
			catch (NoSuchMethodException | SecurityException e) {
   				LOG.debug("수정 로그 셋팅중 오류");
			} catch (IllegalAccessException e) {
   				LOG.debug("수정 로그 셋팅중 오류");
			} catch (IllegalArgumentException e) {
   				LOG.debug("수정 로그 셋팅중 오류");
			} catch (InvocationTargetException e) {
   				LOG.debug("수정 로그 셋팅중 오류");
			}
		}
	}

	public static void  setLogParms(Object param, String mlogScrnNm, String tblNm, String  logMode) {
		Method setter = null;

		if(param != null ) {
			try {
				if(param instanceof  Map){
					setter = param.getClass().getMethod("put", Object.class, Object.class);
		       		if(setter != null) {
			       		//화면이름
		       			setter.invoke(param, "logMode", logMode);
					}
		       	}
				else {
					Field logModeField = getField(param.getClass(), "logMode");
					if(logModeField != null ) {
						setter = param.getClass().getMethod("setLogMode", logModeField.getType());
						if(setter != null ) {
			    			setter.invoke(param, logMode);
			    		}
					}
				}
				setLogParms(param, mlogScrnNm, tblNm);
			}
			catch (NoSuchMethodException | SecurityException e) {
   				LOG.debug("수정 로그 셋팅중 오류");
			} catch (IllegalAccessException e) {
   				LOG.debug("수정 로그 셋팅중 오류");
			} catch (IllegalArgumentException e) {
   				LOG.debug("수정 로그 셋팅중 오류");
			} catch (InvocationTargetException e) {
   				LOG.debug("수정 로그 셋팅중 오류");
			}
		}
	}

	public static void  setLogParms(Object param, String mlogScrnNm, String tblNm, HashMap<String, Object> fixUpdtAfterDataColum) {
		Method setter = null;

		if(param != null ) {
			try {
				if(param instanceof  Map){
					setter = param.getClass().getMethod("put", Object.class, Object.class);
		       		if(setter != null) {
			       		//화면이름
		       			setter.invoke(param, "fixUpdtAfterDataColum", fixUpdtAfterDataColum);
					}
		       	}
				else {
					Field fixModifyAfterDataColumField = getField(param.getClass(), "fixUpdtAfterDataColum");
					if(fixModifyAfterDataColumField != null ) {
						setter = param.getClass().getMethod("setFixUpdtAfterDataColum", fixModifyAfterDataColumField.getType());
						if(setter != null ) {
			    			setter.invoke(param, fixUpdtAfterDataColum);
			    		}
					}
				}
				setLogParms(param, mlogScrnNm, tblNm);
			}
			catch (NoSuchMethodException | SecurityException e) {
   				LOG.debug("수정 로그 셋팅중 오류");
			} catch (IllegalAccessException e) {
   				LOG.debug("수정 로그 셋팅중 오류");
			} catch (IllegalArgumentException e) {
   				LOG.debug("수정 로그 셋팅중 오류");
			} catch (InvocationTargetException e) {
   				LOG.debug("수정 로그 셋팅중 오류");
			}
		}
	}
}
