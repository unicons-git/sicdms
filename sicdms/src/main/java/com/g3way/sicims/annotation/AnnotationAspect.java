package com.g3way.sicims.annotation;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.g3way.sicims.services.sicims900Sys.log.LogService;
import com.g3way.sicims.services.sicims910User.vo.CmUserVo;
import com.g3way.sicims.util.common.SessionUtil;
import com.g3way.sicims.util.encrypt.RsaUtil;

import egovframework.com.cmm.util.EgovUserDetailsHelper;

@Aspect
@Component
public class AnnotationAspect {
	@SuppressWarnings("unused")
	private static final Logger LOG = LoggerFactory.getLogger(AnnotationAspect.class);

	@Autowired private LogService	logService;


	@After("@annotation(com.g3way.sicims.annotation.RSAKeyGeneral)")
    public void keyGeneral(JoinPoint jp) {
		//개인정보 전송전 암호화를 위한 RSA 키 생성
		RsaUtil.initRsa();
    }

    @Before("execution(* com.g3way.sicims.controller.LoginController.loginSuccess(..))")
    public void loginSuccess(JoinPoint jp) {
    	//접속된 사용자 정보를 가져온다
    	CmUserVo loginVo = (CmUserVo) EgovUserDetailsHelper.getAuthenticatedUser();
    	//스프링에서 제공하는 request관리 유틸에서 request를 조회하여 접속 uri를 가져온다.
    	HttpServletRequest httpServletRequest = SessionUtil.getCurrentRequest();
    	if(loginVo != null){
    		loginVo.setLogPage(httpServletRequest.getRequestURI());
    	}

    	if (loginVo.getUpdusrId() == null || loginVo.getUpdusrId().equals("")) {
			loginVo.setUpdusrId(SessionUtil.getLoginUserId());
			loginVo.setUpdusrIp(SessionUtil.getLoginUserIp());
		}


    	logService.insertCmAlog(loginVo);
    }
}
