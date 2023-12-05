package com.g3way.sicims.interceptor;

import egovframework.com.cmm.util.EgovUserDetailsHelper;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.g3way.sicims.services.sicims900Sys.log.LogService;
import com.g3way.sicims.services.sicims900Sys.log.vo.CmLogVo;
import com.g3way.sicims.services.sicims910User.vo.CmUserVo;
import com.g3way.sicims.util.common.CommonUtil;

/**
 * @Class Name : WebLogInterceptor.java
 * @Description : 웹로그 생성을 위한 인터셉터 클래스
 * @Modification Information
 *
 *    수정일        수정자         수정내용
 *    -------      -------     -------------------
 *    2009. 3. 9.   이삼섭         최초생성
 *    2011. 7. 1.   이기하         패키지 분리(sym.log -> sym.log.wlg)
 *
 * @author 공통 서비스 개발팀 이삼섭
 * @since 2009. 3. 9.
 * @version
 * @see
 *
 */
public class WebLogInterceptor extends HandlerInterceptorAdapter {
	private static final Logger LOG = LoggerFactory.getLogger(WebLogInterceptor.class);

	@Resource(name="logService")
	private LogService logService;

	/**
	 * 웹 로그정보를 생성한다.
	 *
	 * @param HttpServletRequest request, HttpServletResponse response, Object handler
	 * @return
	 * @throws Exception
	 */
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler, ModelAndView modeAndView) {

		String reqURL = request.getRequestURI();
		String contextPath = request.getServletContext().getContextPath();

		//.do만 로그 입력
		if(!reqURL.endsWith(".do")) return;

		//로그인 입력 화면 로그 제외
		if((contextPath+"/login/login.do").equals(reqURL)) return;


		CmLogVo wLog = new CmLogVo();
		String uniqId = "";

    	/* Authenticated  */
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(isAuthenticated.booleanValue()) {
    		CmUserVo cmUserVo = (CmUserVo)EgovUserDetailsHelper.getAuthenticatedUser();
			uniqId = (cmUserVo != null ) ? cmUserVo.getUserId() : "";
    	}

    	request.getParameterNames();
    	String param = CommonUtil.getRequestParams(request);
    	if(param != null && param.length() > 1024) {
    		param = param.substring(0, 1024);
    	}

    	LOG.debug(reqURL);
		wLog.setScrnNm(reqURL);
		wLog.setParamCn(param);
		wLog.setUpdusrId(uniqId);
		wLog.setUpdusrIp(uniqId);
		wLog.setUpdusrIp(request.getRemoteAddr());


		//do로 호출되는것만 저장
		if(reqURL.endsWith(".do")) {
			logService.insertCmWlog(wLog);
		}
	}
}
