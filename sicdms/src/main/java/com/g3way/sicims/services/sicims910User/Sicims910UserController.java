package com.g3way.sicims.services.sicims910User;

import java.beans.PropertyEditorSupport;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.g3way.sicims.exception.SicimsException;
import com.g3way.sicims.services.sicims000Cm.Sicims000CmService;
import com.g3way.sicims.services.sicims910User.vo.CmUserVo;
import com.g3way.sicims.util.common.StringUtil;

import egovframework.rte.fdl.property.EgovPropertyService;



/***
 * 시스템관리 컨트롤러
 * @author dykim
 * @since 2022.03
 * @version 1.0.0
 */
@Controller
@RequestMapping("/sicims910User")
public class Sicims910UserController {
	private static final Logger LOG = LoggerFactory.getLogger(Sicims910UserController.class);

	@Autowired private Sicims000CmService 	sicims000CmService;
	@Autowired private Sicims910UserService sicims910UserService;

	@Resource(name = "propertiesService") protected EgovPropertyService propertiesService;
	@Resource MappingJackson2JsonView	jsonView;


	@InitBinder
	public void initBinder(WebDataBinder binder) throws Exception {
		binder.registerCustomEditor(BigDecimal.class, new PropertyEditorSupport() {
	        public void setAsText(String text) throws IllegalArgumentException {
	        	String val = text;
	        	val = val.replaceAll("\\,", "");
	            try {
	            	BigDecimal bigDecimal = new BigDecimal(val);
	            	setValue(bigDecimal);
	            } catch (NumberFormatException e) {
	                setValue(null);
	            }
	        }
	    });

		binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
	        public void setAsText(String text) throws IllegalArgumentException {
	            try {
	                setValue(new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).parse(text));
	            } catch (ParseException e) {
	                setValue(null);
	            }
	        }
	    });
	}

	/********************************/
	/*		1. 사용자 관리				*/
	/********************************/
	/**
	 * 사용자승인관리  페이지 뷰를 호출한다.
	 * @param param HashMap<String,Object>
	 * @param model ModelAndView
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value="/cmUserApprove", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView cmUserApprove(@RequestParam HashMap<String,Object> param
			, ModelAndView model) throws Exception {

		model.addObject("userSeList", 	sicims000CmService.getCmCodeList("CM", "0003"));
		model.addObject("inst1CdList", 	sicims000CmService.getInst1CdList(param));			// 기관1목록

		model.setViewName("sicims910User/cmUserApprove");
		return model;
	}


	/**
	 * 사용자승인관리 상세 정보  페이지 뷰를 호출한다.
	 * @param param HashMap<String,Object>
	 * @param model ModelAndView
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/cmUserApproveDetail", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView cmUserApproveDetail(@RequestParam HashMap<String,Object> param
			, ModelAndView model
			, HttpServletRequest httpRequest) throws Exception {

		model.addObject("cmUserVo",		sicims910UserService.getCmUserInfo(param));
		model.addObject("authrtCdList", sicims000CmService.getCmCodeList("CM", "0004"));

		model.setViewName("sicims910User/cmUserApproveDetail");

		return model;
	}


	/**
	 * 사용자관리 페이지 뷰를 호출한다.
	 * @param param HashMap<String,Object>
	 * @param model ModelAndView
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/cmUser", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView cmUser(@RequestParam HashMap<String,Object> param
			, ModelAndView model) throws Exception {

		model.addObject("userSeList", 	sicims000CmService.getCmCodeList("CM", "0004"));	// 사용자구분
		model.addObject("inst1CdList", 	sicims000CmService.getInst1CdList(param));			// 기관1목록

		model.addObject("info", param);
		model.setViewName("sicims910User/cmUser");
		return model;
	}

	/**
	 * 사용자 정보  페이지 뷰를 호출한다.
	 * @param param HashMap<String,Object>
	 * @param model ModelAndView
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/cmUserDetail", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView cmUserDetail(@RequestParam HashMap<String,Object> param
			, ModelAndView model
			, HttpServletRequest httpRequest) throws Exception {
		CmUserVo cmUserVo = new CmUserVo();
		if (!param.get("userId").equals("")) {
			cmUserVo = sicims910UserService.getCmUserInfo(param);
		} else {
			cmUserVo.setUseYn("Y");	/* 사용여부 */
		}

		model.addObject("userSeList", 	sicims000CmService.getCmCodeList("CM", "0004"));

		//기관1코드 검색
		if(cmUserVo != null && cmUserVo.getInst1Cd() != null && !"".equals(cmUserVo.getInst1Cd().trim())){
			param.put("inst1Cd", cmUserVo.getInst1Cd());
		}

		//기관2코드 검색
		if(cmUserVo != null && cmUserVo.getInst2Cd() != null && !"".equals(cmUserVo.getInst2Cd().trim())){
			param.put("inst2Cd", cmUserVo.getInst2Cd());
		}

		//전화번호 포맷형식 변경
		if(cmUserVo != null  && cmUserVo.getTelno() != null && !"".equals(cmUserVo.getTelno())){
			cmUserVo.setTelno(StringUtil.getPhoneFormat(cmUserVo.getTelno()));
		}


		model.addObject("inst1CdList", 	sicims000CmService.getInst1CdList(param));		// 기관1목록
		model.addObject("inst2CdList", 	sicims000CmService.getInst2CdList(param));		// 기관2목록


		model.addObject("aprvYnList", 	sicims000CmService.getCmCodeList("CM", "0001"));	// 승인여부
		model.addObject("useYnList", 	sicims000CmService.getCmCodeList("CM", "0001"));	// 사용여부

		model.addObject("cmUserVo",		cmUserVo);
		model.addObject("info",			param);

		model.setViewName("sicims910User/cmUserDetail");
		return model;
	}


	/**
	 * 페이지 단위로 사용자 목록을 조회한다.
	 * @param param HashMap<String,Object>
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectCmUserList", method = RequestMethod.POST )
	public ModelAndView selectCmUserList(@RequestParam HashMap<String,Object> param) throws Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("result", sicims910UserService.selectCmUserList(param));
		return new ModelAndView(jsonView, result);
	}

	/**
	 * 사용자 정보를 조회한다.
	 * @param param HashMap<String,Object>
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/getCmUserInfo", method = RequestMethod.POST )
	public ModelAndView getCmUserInfo(@RequestParam HashMap<String,Object> param) throws Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("userInfo", sicims910UserService.getCmUserInfo(param));
		return new ModelAndView(jsonView, result);
	}


	/**
	 * 사용자 정보를 등록한다.
	 * @param param HashMap<String,Object>
	 * @param userVo UserVo
	 * @param request HttpServletRequest
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertCmUser", method = RequestMethod.POST)
	public ModelAndView  insertCmUser(@ModelAttribute("cmUserVo") CmUserVo cmUserVo
			, HttpServletRequest request) throws Exception {

		HashMap<String, Object> result = new HashMap<String, Object>();

		result.put("result", sicims910UserService.insertCmUser(cmUserVo));
		return new ModelAndView(jsonView, result);
	}

	/**
	 * 사용자 정보를 수정한다.
	 * @param param HashMap<String,Object>
	 * @param userVo UserVo
	 * @param request HttpServletRequest
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateCmUser", method = RequestMethod.POST)
	public ModelAndView  updateCmUser(@ModelAttribute("cmUserVo") CmUserVo cmUserVo
			, HttpServletRequest request) throws Exception {

		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("userId", cmUserVo.getUserId());

		HashMap<String, Object> result = new HashMap<String, Object>();
		try{
			result.put("result", 	sicims910UserService.updateCmUser(cmUserVo));
			result.put("userInfo", 	sicims910UserService.getCmUserInfo(param));
		} catch(SicimsException e) {
			result.put("resultMsg", e.getMessage());
		} catch(Exception e) {
			e.printStackTrace();
			LOG.error("사용자정보 저장 중 오류 발생");
		}

		return new ModelAndView(jsonView, result);
	}


	/**
	 * 비밀번호를 초기화 한다.
	 * @param param HashMap<String,Object>
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
	public ModelAndView resetPassword(@RequestParam HashMap<String,Object> param) throws Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();

		try{
			result.put("result", sicims910UserService.resetPassword(param));
		} catch(SicimsException e) {
			result.put("resultMsg", e.getMessage());
		} catch(Exception e) {
			LOG.error("비밀번호 초기화 중  오류 발생");
		}

		return new ModelAndView(jsonView, result);
	}



	/**
	 * 사용자 정보를  삭제한다.
	 * @param param HashMap<String,Object>
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteCmUser", method = RequestMethod.POST)
	public ModelAndView deleteCmUser(@RequestParam HashMap<String,Object> param) throws Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("result", sicims910UserService.deleteCmUser(param));
		return new ModelAndView(jsonView, result);
	}


	/**
	 * 사용자 승인정보를 저장한다.
	 * @param param HashMap<String,Object>
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value="/saveOrUpdateUserList", method = RequestMethod.POST, consumes="application/json")
	public ModelAndView saveOrUpdateUserList(@RequestBody ArrayList<CmUserVo> list) throws Exception {

		HashMap<String, Object> result = new HashMap<String, Object>();
		try{
			result.put("result", sicims910UserService.saveOrUpdateUserList(list));
		} catch(SicimsException e) {
			result.put("resultMsg", e.getMessage());
		} catch(Exception e) {
			LOG.error("사용자 내용 저장 중 오류 발생");
		}

		return new ModelAndView(jsonView, result);
	}


	/**
	 * 사용자 승인 정보를 수정한다.
	 * @param param HashMap<String,Object>
	 * @param userVo UserVo
	 * @param request HttpServletRequest
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateCmUserApprove", method = RequestMethod.POST)
	public ModelAndView  updateCmUserApprove(@ModelAttribute("cmUserVo") CmUserVo cmUserVo
			, HttpServletRequest request) throws Exception {

		HashMap<String, Object> result = new HashMap<String, Object>();
		try{
			result.put("result", sicims910UserService.updateCmUserApprove(cmUserVo));
		} catch(SicimsException e) {
			result.put("resultMsg", e.getMessage());
		} catch(Exception e) {
			LOG.error("사용자 승인 정보 저장 중 오류 발생");
		}

		return new ModelAndView(jsonView, result);
	}



	/********************************/
	/*		9. 담당자 검색 팝업			*/
	/********************************/
	/**
	 * 담당자 검색 페이지 뷰를 호출한다.
	 * @param param HashMap<String,Object>
	 * @param model ModelAndView
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/cmUserPopup", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView cmUserPopup(@RequestParam HashMap<String,Object> param
			, ModelAndView model) throws Exception {

		model.addObject("userSeList", 	sicims000CmService.getCmCodeList("CM", "0004"));	// 사용자구분
		model.addObject("inst1CdList", 	sicims000CmService.getInst1CdList(param));			// 기관1목록
		model.addObject("info", 		param);

		model.setViewName("sicims910User/cmUserPopup");

		return model;
	}
}