package com.g3way.sicims.controller;

import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.g3way.sicims.services.sicims910User.vo.CmUserVo;

import egovframework.com.cmm.service.EgovProperties;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.rte.fdl.property.EgovPropertyService;


/**
 * @Package Name	: com.g3way.sicims.services.main
 * @Class Name		: MainController.java
 * @Description		: 메인 컨트롤러
 * @Modification	:
 * @-----------------------------------------------------------------
 * @ Date			| Name			| Comment
 * @-------------  -----------------   ------------------------------
 * @ 2020. 9. 11.	| dykim			| 최초생성
 * @-----------------------------------------------------------------
 * @Author			: dykim
 * @Since			: 2020. 9. 11. 오전 9:28:11
 * @Version			: 1.0
 */
@Controller
public class MainController {
	@SuppressWarnings("unused")
	private static final Logger LOG = LoggerFactory.getLogger(MainController.class);

	@Resource MappingJackson2JsonView jsonView;

	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	/************************************/
	/* 1. 메인 							*/
	/************************************/


	/**
	 * 메인 페이지 뷰를 호출한다.
	 * @param param HashMap<String,Object>
	 * @param model ModelAndView
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = {"/main", "/sicims500Cb/main"}, method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView main(@RequestParam HashMap<String,Object> param
			, HttpServletRequest  request
			, HttpServletResponse response
			, ModelAndView model) throws Exception {

		response.setHeader("Content-Security-Policy", "default-src http:; script-src http: 'unsafe-inline' 'unsafe-eval'; style-src http: 'unsafe-inline'; object-src http:; img-src http: data:; worker-src http: blob:;");

		CmUserVo cmUserVo = (CmUserVo) EgovUserDetailsHelper.getAuthenticatedUser();

		model.addObject("member", cmUserVo);
		model.addObject("info", param);

		if (cmUserVo == null || request.getRequestURI().contains("sicims500Cb")) {
			// 건설업자가진단서비스
			model.setViewName("redirect:/sicims500Cb/cbNtbd.do");
		} else {
			if (cmUserVo.getAuthrtCd().equals("ROLE_ECMP")) {
				// 건설업사전자료 제출
				model.setViewName("redirect:/sicims300Cc/ccCsex.do");
			} else {
				// 업무시스템
				model.setViewName("redirect:/sicims110Kc/kcCcma.do");
			}
		}

		return model;
	}

	/**
	 * 메뉴 페이지 뷰를 호출한다.
	 * @param param HashMap<String,Object>
	 * @param model ModelAndView
	 * @param request HttpServletRequest
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/main/include", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView include(@RequestParam HashMap<String,Object> param
			, ModelAndView model
			, HttpServletResponse response
			, HttpServletRequest request) throws Exception {

		model.addObject("info", param);

		response.setHeader("Content-Security-Policy", "default-src http:; script-src http: 'unsafe-inline' 'unsafe-eval'; style-src http: 'unsafe-inline'; object-src http:; img-src http: data:; worker-src http: blob:;");

		model.setViewName("main/include");

		return model;
	}


	/**
	 * 메뉴 페이지 뷰를 호출한다.
	 * @param param HashMap<String,Object>
	 * @param model ModelAndView
	 * @param request HttpServletRequest
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/main/header", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView header(@RequestParam HashMap<String,Object> param
			, ModelAndView model
			, HttpServletRequest request) throws Exception {

		model.addObject("info", param);
		HashMap<String, Object > params = new HashMap<String, Object>();
		params.put("bbsSe", "01");
		model.setViewName("main/header");

		if(param.get("loginYn") != null && "Y".equals(param.get("loginYn"))) {
			model.addObject("loginYn", param.get("loginYn"));
		}

		return model;
	}

	/**
	 * 서브메뉴 페이지 뷰를 호출한다.
	 * @param param HashMap<String,Object>
	 * @param model ModelAndView
	 * @param request HttpServletRequest
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/main/nav", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView nav(@RequestParam HashMap<String,Object> param
			, ModelAndView model
			, HttpServletRequest request) throws Exception {

		model.addObject("info", param);
		model.setViewName("main/nav");
		return model;
	}

	/**
	 * 컨텐트 페이지 뷰를 호출한다.
	 * @param param HashMap<String,Object>
	 * @param model ModelAndView
	 * @param request HttpServletRequest
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/main/article", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView article(@RequestParam HashMap<String,Object> param
			, ModelAndView model
			, HttpServletRequest request) throws Exception {

		model.addObject("info", 		param);
		model.setViewName("main/article");
		return model;
	}

	/**
	 * 푸터  페이지 뷰를 호출한다.
	 * @param param HashMap<String,Object>
	 * @param model ModelAndView
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/main/footer", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView footer(@RequestParam HashMap<String,Object> param, ModelAndView model) throws Exception {
		model.addObject("info", param);
		model.setViewName("main/footer");
		return model;
	}


}
