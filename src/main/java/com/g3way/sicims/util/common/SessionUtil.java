package com.g3way.sicims.util.common;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;

import com.g3way.sicims.exception.SicimsException;
import com.g3way.sicims.services.sicims910User.vo.CmUserVo;

import egovframework.com.cmm.util.EgovUserDetailsHelper;

/**
 *
 * @author hoTire
 *
 */

public class SessionUtil extends WebUtils{
	private static final Logger LOG = LoggerFactory.getLogger(SessionUtil.class);

	public static Object getAttribute(String name) {
		return (Object)RequestContextHolder.getRequestAttributes().getAttribute(name, RequestAttributes.SCOPE_SESSION);
	}

	public static void setAttribute(String name, Object object)  {
		RequestContextHolder.getRequestAttributes().setAttribute(name, object, RequestAttributes.SCOPE_SESSION);
     }

	public static void removeAttribute(String name)  {
		RequestContextHolder.getRequestAttributes().removeAttribute(name, RequestAttributes.SCOPE_SESSION);

     }

	public static CmUserVo getLoginInfo()  {
		Object member = (CmUserVo) EgovUserDetailsHelper.getAuthenticatedUser();

		if(member != null){
			CmUserVo cmUserVo = (CmUserVo)member;
			return cmUserVo;
		} else {
			return null;
		}
     }


	public static String getLoginUserId() {
		CmUserVo member = (CmUserVo)EgovUserDetailsHelper.getAuthenticatedUser();

		if(member != null ){
			return member.getUserId();
		} else {
			return "ADMIN";
		}
     }

	public static String getLoginUserNm() {
		CmUserVo member = (CmUserVo)EgovUserDetailsHelper.getAuthenticatedUser();

		if(member != null ){
			return member.getUserNm();
		} else {
			return "ADMIN";
		}
     }


	public static String getInst1Cd() {
		CmUserVo member = (CmUserVo)EgovUserDetailsHelper.getAuthenticatedUser();

		if(member != null ){
			return member.getInst1Cd();
		} else {
			return "";
		}
     }


	//접속중인 클라이언트의 ip 정보 조회
	public static String getLoginUserIp()  {
		String ip = "";
		try{
			//RequestContextHolder
			HttpServletRequest request = getCurrentRequest();
			if (request == null) {
				ip = "xxx.xxx.xxx.xxx";
			} else {
				ip = request.getHeader("X-Forwarded-For");
			    if (ip == null) ip = request.getHeader("Proxy-Client-IP");
			    if (ip == null) ip = request.getHeader("WL-Proxy-Client-IP");
			    if (ip == null) ip = request.getHeader("HTTP_CLIENT_IP");
			    if (ip == null) ip = request.getHeader("HTTP_X_FORWARDED_FOR");
			    if (ip == null) ip = request.getRemoteAddr();
			}
		}catch(SicimsException e) {
			e.printStackTrace();
			LOG.error("접속 아이피 가져오는 중 오류 발생(VBMS)");
		} catch(Exception e){
			e.printStackTrace();
			LOG.error("접속 아이피 가져오는 중 오류 발생");
		}

		return ip;
	}


	//로그인 세션 권한 목록에 해당 권한이 있는지 체크 여부
	public static boolean getAuthCheck(String authCoed) {
		boolean returnBoolean = false;

		if(authCoed != null && !"".equals(authCoed)){
			List<String> authList = EgovUserDetailsHelper.getAuthorities();
			String[] authCoedArray = authCoed.split(",");
			for(String strAuthCoed : authCoedArray){
				if(strAuthCoed.indexOf("ROLE_") == -1) {
					strAuthCoed = "ROLE_" + strAuthCoed;
				}
				returnBoolean = authList.contains(strAuthCoed.trim());
				if(returnBoolean) {
					break;
				}
			}
		}
		return returnBoolean;
	}

	public static HttpServletRequest getCurrentRequest(){
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		if (requestAttributes == null) {
			return null;
		} else {
			return ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		}
     }


	public static HttpSession getSession(){
		try {
			return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getSession();
		} catch (NullPointerException e) {
			return null;
		}
	}

	@Bean
	public RequestContextListener requestContextListener(){
	    return new RequestContextListener();
	}
}
