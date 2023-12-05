package com.g3way.sicims.services.sicims930Menu;

import java.util.HashMap;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.g3way.sicims.services.sicims000Cm.Sicims000CmService;

import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.fdl.property.EgovPropertyService;



/***
 * 메뉴접속로그관리 컨트롤러
 * @author dykim
 * @since 2022.03
 * @version 1.0.0
 */
@Controller
@RequestMapping("/sicims930Menu")
public class Sicims930MenuController {
	private static final Logger LOG = LoggerFactory.getLogger(Sicims930MenuController.class);

	@Autowired private Sicims000CmService 	sicims000CmService;
	@Autowired private Sicims930MenuService sicims930MenuService;

	@Resource(name = "propertiesService") protected EgovPropertyService propertiesService;
	@Resource MappingJackson2JsonView	jsonView;


	/********************************/
	/*		1. 메뉴접속로그 관리			*/
	/********************************/
	/**
	 * 메뉴접속로그 페이지 뷰를 호출한다.
	 * @param param HashMap<String,Object>
	 * @param model ModelAndView
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/cmMenu", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView cmMenu(@RequestParam HashMap<String,Object> param
			, ModelAndView model) throws Exception {

		model.addObject("reportingServer", propertiesService.getString("reportingServer"));

		model.addObject("mmenuSeList", 	sicims000CmService.getCmCodeList("CM", "0006"));	// 주메뉴구분
		model.setViewName("sicims930Menu/cmMenu");

		return model;
	}


	/**
	 * 월별 메뉴접속 현황 페이지 뷰를 호출한다.
	 * @param param HashMap<String,Object>
	 * @param model ModelAndView
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/cmMenuStat", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView cmMenuStat(@RequestParam HashMap<String,Object> param
			, ModelAndView model) throws Exception {

		model.addObject("reportingServer", propertiesService.getString("reportingServer"));

		model.addObject("mmenuSeList", 	sicims000CmService.getCmCodeList("CM", "0006"));	// 주메뉴구분
		model.setViewName("sicims930Menu/cmMenuStat");

		return model;
	}



	/**
	 * 페이지 단위로 메뉴 접속로그 목록을 조회한다.
	 * @param param HashMap<String,Object>
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectCmMenuList", method = RequestMethod.POST )
	public ModelAndView selectCmMenuList(@RequestParam HashMap<String,Object> param) throws Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("result", sicims930MenuService.selectCmMenuList(param));

		return new ModelAndView(jsonView, result);
	}


	/**
	 * 월별 메뉴접속 현황을 조회한다.
	 * @param param HashMap<String,Object>
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/getCmMenuStatList", method = RequestMethod.POST )
	public ModelAndView getCmMenuStatList(@RequestParam HashMap<String,Object> param) throws Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("result", sicims930MenuService.getCmMenuStatList(param));

		return new ModelAndView(jsonView, result);
	}


}