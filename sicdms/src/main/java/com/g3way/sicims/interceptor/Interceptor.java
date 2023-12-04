package com.g3way.sicims.interceptor;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import egovframework.rte.fdl.property.EgovPropertyService;

public class Interceptor extends HandlerInterceptorAdapter {

	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (request.getSession(true).getAttribute("yearSessionInfo") == null) {
			request.getSession(true).setAttribute("yearSessionInfo", getYearInfo());
		}
		/*
		UserVo member = (UserVo) request.getSession(true).getAttribute("member");

		//로그인확인 세션확인
		if ( member == null || member.getUserId() == null ) {
			if (request.getHeader("AJAX") != null && request.getHeader("AJAX").equals("Yes")) {
				response.sendError(403);
				return false;
			} else {
				response.sendRedirect(contextPath + "/login/sessionTimeout.do");
				return false;
			}
		} else {
			return true;
		}
		*/
		return true;
	}


	private HashMap<String, Object> getYearInfo() throws Exception {
		HashMap<String, Object> yearInfo = new HashMap<String, Object>();
		Calendar cal = null;
		// 현재일자
		cal = Calendar.getInstance();
		yearInfo.put("NOWDATE", (Date)cal.getTime());

		// 1주일 전 일자
		cal.add(Calendar.DAY_OF_MONTH, -7);
		yearInfo.put("PREWEEKDATE", (Date)cal.getTime());


		// 1개월전 일자
		cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		yearInfo.put("PREMONTHDATE", (Date)cal.getTime());

		// 1년월전 일자
		cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -12);
		yearInfo.put("PREYEARDATE", (Date)cal.getTime());
		return yearInfo;
	}


}