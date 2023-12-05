package com.g3way.sicims.services.sicims300Cc;

import java.beans.PropertyEditorSupport;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.g3way.sicims.exception.SicimsException;
import com.g3way.sicims.services.sicims000Cm.Sicims000CmService;
import com.g3way.sicims.services.sicims300Cc.vo.CcVcadVo;
import com.g3way.sicims.services.sicims300Cc.vo.CcCsexVo;
import com.g3way.sicims.services.sicims300Cc.vo.CcCsmaVo;
import com.g3way.sicims.services.sicims300Cc.vo.CcCsrsVo;
import com.g3way.sicims.services.sicims300Cc.vo.CcIsrdVo;
import com.g3way.sicims.services.sicims300Cc.vo.CcIsrrVo;
import com.g3way.sicims.services.sicims930Menu.Sicims930MenuService;
import com.g3way.sicims.util.common.SessionUtil;

import egovframework.rte.fdl.property.EgovPropertyService;

@Controller
@RequestMapping(value= {"/sicims300Cc"})
public class Sicims300CcController {
	private static final Logger LOG = LoggerFactory.getLogger(Sicims300CcController.class);

	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	@Autowired private Sicims000CmService 	sicims000CmService;
	@Autowired private Sicims300CcService 	sicims300CcService;
	@Autowired private Sicims930MenuService	sicims930MenuService;

	@Resource MappingJackson2JsonView		jsonView;

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
	/*	1. 민원관리-건설업등록기준사전조사 자료관리	*/
	/*	1.1 건설업등록기준사전조사 정보			*/
	/********************************/
	/**
	 * 건설업등록기준사전조사  자료관리 페이지 뷰를 호출한다.
	 * @param param HashMap<String,Object>
	 * @param model ModelAndView
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/ccCsma", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView ccCsma(@RequestParam HashMap<String,Object> param, ModelAndView model) throws Exception {
		sicims930MenuService.insertCmMenu("3000", "3100");	// 민원관리-건설업등록기준 제출자료 관리

		model.addObject("regInstList", 		sicims000CmService.getInst1CdList(param));			// 기관코드
		model.addObject("mfldCdList", 		sicims000CmService.getCmCodeList("CC", "1001"));	// 주력분야코드
		model.addObject("sbmsnSttsCdList", 	sicims000CmService.getCmCodeList("CC", "1004"));	// 제출상태코드

		model.addObject("info", param);
		model.setViewName("sicims300Cc/ccCsma");

		return model;
	}


	/**
	 * 건설업등록기준사전조사 상세정보 페이지 뷰를 호출한다.
	 * @param param HashMap<String,Object>
	 * @param model ModelAndView
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/ccCsmaDetail", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView ccCsmaDetail(@RequestParam HashMap<String,Object> param, ModelAndView model) throws Exception {

		CcCsmaVo ccCsmaVo = new CcCsmaVo();
		if (param.get("cirsSn") != null && !param.get("cirsSn").equals("")) {
			ccCsmaVo = sicims300CcService.getCcCsmaInfo(param);
		} else {
			ccCsmaVo.setRegInstCd(SessionUtil.getInst1Cd());
		}

		model.addObject("ccCsmaVo",			ccCsmaVo);
		model.addObject("regInstList", 		sicims000CmService.getInst1CdList(param));			// 기관코드
		model.addObject("exmnBzentySeList", sicims000CmService.getCmCodeList("CC", "1003"));	// 조사업체구분
		model.addObject("spcoSeList", 		sicims000CmService.getCmCodeList("KC", "1002"));	// 전문업체구분
		model.addObject("mfldCdList", 		sicims000CmService.getCmCodeList("CC", "1001"));	// 주력분야코드
		model.addObject("mmadvnSeList", 	sicims000CmService.getCmCodeList("CC", "1002"));	// 상호시장진출구분
		model.addObject("sbmsnSttsCdList", 	sicims000CmService.getCmCodeList("CC", "1004"));	// 제출상태코드

		model.addObject("ccCsexList", 		sicims300CcService.getCcCsexList(param));			// 자료제출 이력

		model.addObject("info", param);
		model.setViewName("sicims300Cc/ccCsmaDetail");

		return model;
	}


	/**
	 * 페이지 단위로 건설업등록기준사전조사 목록을 조회한다.
	 * @param param HashMap<String,Object>
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectCcCsmaList", method = RequestMethod.POST )
	public ModelAndView selectCcCsmaList(@RequestParam HashMap<String,Object> param) throws Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("result", sicims300CcService.selectCcCsmaList(param));

		return new ModelAndView(jsonView, result);
	}


	/**
	 * 건설업등록기준사전조사 정보를 등록한다.
	 * @param param HashMap<String,Object>
	 * @param userVo UserVo
	 * @param request HttpServletRequest
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertCcCsma", method = RequestMethod.POST)
	public ModelAndView  insertCcCsma(@ModelAttribute("ccCsmaVo") CcCsmaVo ccCsmaVo) throws Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();
		try{
			result.put("result", 	sicims300CcService.insertCcCsma(ccCsmaVo));
		} catch(SicimsException e) {
			e.printStackTrace();
			result.put("resultMsg", e.getMessage());
		} catch(Exception e) {
			e.printStackTrace();
			LOG.error("건설업등록기준사전조사 등록 중 오류 발생");
		}

		return new ModelAndView(jsonView, result);
	}

	/**
	 * 건설업등록기준사전조사 정보를 수정한다.
	 * @param param HashMap<String,Object>
	 * @param userVo UserVo
	 * @param request HttpServletRequest
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateCcCsma", method = RequestMethod.POST)
	public ModelAndView  updateCcCsma(@ModelAttribute("ccCsmaVo") CcCsmaVo ccCsmaVo) throws Exception {

		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("result", sicims300CcService.updateCcCsma(ccCsmaVo));

		return new ModelAndView(jsonView, result);
	}

	/**
	 * 외부업체 사용자의 비밀번호를 초기화 한다.
	 * @param param HashMap<String,Object>
	 * @param userVo UserVo
	 * @param request HttpServletRequest
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/resetPswd", method = RequestMethod.POST)
	public ModelAndView  resetPswd(@ModelAttribute("ccCsmaVo") CcCsmaVo ccCsmaVo) throws Exception {

		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("result", sicims300CcService.resetPswd(ccCsmaVo));

		return new ModelAndView(jsonView, result);
	}





	/**
	 * 건설업등록기준사전조사 정보를  삭제한다.
	 * @param param HashMap<String,Object>
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteCcCsma", method = RequestMethod.POST)
	public ModelAndView deleteCcCsma(@RequestParam HashMap<String,Object> param) throws Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();

		result.put("result", sicims300CcService.deleteCcCsma(param));

		return new ModelAndView(jsonView, result);
	}



	/********************************/
	/*	1.2 민원관리-건설업등록기준사전조사결과 정보	*/
	/********************************/
	/**
	 * 건설업등록기준사전조사결과 상세정보 페이지 뷰를 호출한다.
	 * @param param HashMap<String,Object>
	 * @param model ModelAndView
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/ccCsrsDetail", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView ccCsrsDetail(@RequestParam HashMap<String,Object> param, ModelAndView model) throws Exception {
		model.addObject("ccCsrsVo",	sicims300CcService.getCcCsrsInfo(param));
		model.addObject("info", 	param);

		model.setViewName("sicims300Cc/ccCsrsDetail");

		return model;
	}

	/**
	 * 건설업등록기준사전조사결과 정보를 저장한다.
	 * @param param HashMap<String,Object>
	 * @param userVo UserVo
	 * @param request HttpServletRequest
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveOrUpdateCcCsrs", method = RequestMethod.POST)
	public ModelAndView  saveOrUpdateCcIsrr(@ModelAttribute("ccCsrsInfo") CcCsrsVo ccCsrsVo,
			MultipartHttpServletRequest multipartRequest) throws Exception {

		HashMap<String, Object> result	= new HashMap<String, Object>();

		try {
			result.put("result", 	sicims300CcService.saveOrUpdateCcCsrs(ccCsrsVo, multipartRequest));
		} catch (Exception e) {
			LOG.debug("건설업등록기준사전조사결과 저장 실패");
			e.printStackTrace();
		}

		return new ModelAndView(jsonView, result);
	}


	/**
	 * 건설업등록기준사전조사결과 파일 정보를  삭제한다.
	 * @param param HashMap<String,Object>
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteCcCsrsFile", method = RequestMethod.POST)
	public ModelAndView deleteCcCsrsFile(@RequestParam HashMap<String,Object> param) throws Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();

		param.put("updusrId", SessionUtil.getLoginUserId());
		param.put("updusrIp", SessionUtil.getLoginUserIp());

		result.put("result", sicims300CcService.deleteCcCsrsFile(param));

		return new ModelAndView(jsonView, result);
	}




	/********************************/
	/*	1.3 건설업등록기준사전조사자료 제출		*/
	/********************************/
	/**
	 * 건설업등록기준사전조사자료 제출 페이지 뷰를 호출한다.
	 * @param param HashMap<String,Object>
	 * @param model ModelAndView
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/ccCsex", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView ccCsex(@RequestParam HashMap<String,Object> param, ModelAndView model) throws Exception {
		//param.put("exmnBzentyPicEmlAddr", SessionUtil.getLoginUserId());


/*		model.addObject("regInstList", 		sicims000CmService.getInst1CdList(param));			// 기관코드
		model.addObject("mfldCdList", 		sicims000CmService.getCmCodeList("CC", "1001"));	// 주력분야코드
		model.addObject("sbmsnSttsCdList", 	sicims000CmService.getCmCodeList("CC", "1004"));	// 제출상태코드
*/
		model.addObject("info", param);
		model.setViewName("sicims300Cc/ccCsex");

		return model;
	}


	/**
	 * 건설업등록기준사전조사자료 제출 상세정보 페이지 뷰를 호출한다.
	 * @param param HashMap<String,Object>
	 * @param model ModelAndView
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/ccCsexDetail", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView ccCsexDetail(@RequestParam HashMap<String,Object> param, ModelAndView model) throws Exception {

		CcCsexVo ccCsexVo = new CcCsexVo();
		if (param.get("exmnDataSn") != null && !param.get("exmnDataSn").equals("")) {
			ccCsexVo = sicims300CcService.getCcCsexInfo(param);
		}

		model.addObject("ccCsmaVo",		sicims300CcService.getCcCsmaInfo(param));
		model.addObject("ccCsexVo",		ccCsexVo);
		model.addObject("info", 	param);
		model.setViewName("sicims300Cc/ccCsexDetail");

		return model;
	}


	/**
	 * 건설업등록기준사전조사자료 제출 목록을 조회한다.
	 * @param param HashMap<String,Object>
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/getCcCsexList", method = RequestMethod.POST )
	public ModelAndView getCcCsexList(@RequestParam HashMap<String,Object> param) throws Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("result", sicims300CcService.getCcCsexList(param));

		return new ModelAndView(jsonView, result);
	}



	/**
	 * 건설업등록기준사전조사자료 제출 정보를 등록한다.
	 * @param param HashMap<String,Object>
	 * @param userVo UserVo
	 * @param request HttpServletRequest
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertCcCsex", method = RequestMethod.POST)
	public ModelAndView  insertCcCsex(@ModelAttribute("ccCsexVo") CcCsexVo ccCsexVo) throws Exception {

		HashMap<String, Object> result = new HashMap<String, Object>();

		try{
			result.put("result", 	sicims300CcService.insertCcCsex(ccCsexVo));
		} catch(SicimsException e) {
			e.printStackTrace();
			result.put("resultMsg", e.getMessage());
		} catch(Exception e) {
			e.printStackTrace();
			LOG.error("건설업등록기준사전조사자료 등록 중 오류 발생");
		}

		return new ModelAndView(jsonView, result);
	}


	/********************************/
	/*	2. 민원관리						*/
	/*	2.1 민원관리-위반업체행정처분 정보		*/
	/********************************/
	/**
	 * 위반업체행정처분 자료관리 페이지 뷰를 호출한다.
	 * @param param HashMap<String,Object>
	 * @param model ModelAndView
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/ccVcad", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView ccVcad(@RequestParam HashMap<String,Object> param, ModelAndView model) throws Exception {
		sicims930MenuService.insertCmMenu("3000", "3200");		// 민원관리-민원관리
		//sicims930MenuService.insertCmMenu("3000", "3210");	// 민원관리-민원관리-위반업체 행정처분 자료관리

		model.addObject("regInstList", 		sicims000CmService.getInst1CdList(param));			// 기관코드
		model.addObject("vltnSpcnSeList", 	sicims000CmService.getCmCodeList("CC", "0001"));	// 공종분야

		model.addObject("info", param);
		model.setViewName("sicims300Cc/ccVcad");

		return model;
	}


	/**
	 * 위반업체행정처분 상세정보 페이지 뷰를 호출한다.
	 * @param param HashMap<String,Object>
	 * @param model ModelAndView
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/ccVcadDetail", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView ccVcadDetail(@RequestParam HashMap<String,Object> param, ModelAndView model) throws Exception {

		CcVcadVo ccVcadVo = new CcVcadVo();
		if (param.get("admdspSn") != null && !param.get("admdspSn").equals("")) {
			ccVcadVo = sicims300CcService.getCcVcadInfo(param);
		} else {
			ccVcadVo.setRegInstCd(SessionUtil.getInst1Cd());
		}

		model.addObject("ccVcadVo",			ccVcadVo);
		model.addObject("regInstList", 		sicims000CmService.getInst1CdList(param));			// 기관코드
		model.addObject("vltnSpcnSeList", 	sicims000CmService.getCmCodeList("CC", "0001"));	// 위반혐의구분
		model.addObject("cmptnSeList", 		sicims000CmService.getCmCodeList("CC", "0002"));	// 완료구분(완료여부)
		model.addObject("prgrsSttsCdList", 	sicims000CmService.getCmCodeList("CC", "0003"));	// 진행상태코드(진행상황)


		model.addObject("info", param);
		model.setViewName("sicims300Cc/ccVcadDetail");

		return model;
	}


	/**
	 * 페이지 단위로 위반업체행정처분 목록을 조회한다.
	 * @param param HashMap<String,Object>
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectCcVcadList", method = RequestMethod.POST )
	public ModelAndView selectGcntrctList(@RequestParam HashMap<String,Object> param) throws Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("result", sicims300CcService.selectCcVcadList(param));

		return new ModelAndView(jsonView, result);
	}


	/**
	 * 위반업체행정처분 정보를 등록한다.
	 * @param param HashMap<String,Object>
	 * @param userVo UserVo
	 * @param request HttpServletRequest
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertCcVcad", method = RequestMethod.POST)
	public ModelAndView  insertCcVcad(@ModelAttribute("ccVcadVo") CcVcadVo ccVcadVo
			, HttpServletRequest request) throws Exception {

		HashMap<String, Object> result = new HashMap<String, Object>();

		result.put("result", sicims300CcService.insertCcVcad(ccVcadVo));
		return new ModelAndView(jsonView, result);
	}

	/**
	 * 위반업체행정처분 정보를 수정한다.
	 * @param param HashMap<String,Object>
	 * @param userVo UserVo
	 * @param request HttpServletRequest
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateCcVcad", method = RequestMethod.POST)
	public ModelAndView  updateCcVcad(@ModelAttribute("ccVcadVo") CcVcadVo ccVcadVo
			, HttpServletRequest request) throws Exception {

		HashMap<String, Object> result = new HashMap<String, Object>();

		result.put("result", sicims300CcService.updateCcVcad(ccVcadVo));

		return new ModelAndView(jsonView, result);
	}


	/**
	 * 위반업체행정처분 정보를  삭제한다.
	 * @param param HashMap<String,Object>
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteCcVcad", method = RequestMethod.POST)
	public ModelAndView deleteCcVcad(@RequestParam HashMap<String,Object> param) throws Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();

		result.put("result", sicims300CcService.deleteCcVcad(param));

		return new ModelAndView(jsonView, result);
	}



	/********************************/
	/*		2.2 민원관리-불법하도급신고 정보	*/
	/********************************/
	/**
	 * 불법하도급신고 정보 페이지 뷰를 호출한다.
	 * @param param HashMap<String,Object>
	 * @param model ModelAndView
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/ccIsrd", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView ccIsrd(@RequestParam HashMap<String,Object> param, ModelAndView model) throws Exception {
		sicims930MenuService.insertCmMenu("3000", "3200");		// 민원관리-민원관리
		//sicims930MenuService.insertCmMenu("3000", "3220");	// 민원관리-민원관리-불법하도급신고

		model.addObject("regInstList", 		sicims000CmService.getInst1CdList(param));			// 기관코드

		model.addObject("info", param);
		model.setViewName("sicims300Cc/ccIsrd");

		return model;
	}


	/**
	 * 불법하도급신고 상세 정보  뷰를 호출한다.
	 * @param param HashMap<String,Object>
	 * @param model ModelAndView
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/ccIsrdDetail", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView ccIsrdDetail(@RequestParam HashMap<String,Object> param
			, ModelAndView model) throws Exception {

		CcIsrdVo ccIsrdVo = new CcIsrdVo();
		if (param.get("rcptNo") != null && !param.get("rcptNo").equals("")) {
			ccIsrdVo = sicims300CcService.getCcIsrdInfo(param);
		} else {
			ccIsrdVo.setRegInstCd(SessionUtil.getInst1Cd());
		}

		model.addObject("ccIsrdVo", 	ccIsrdVo);
		model.addObject("regInstList",	sicims000CmService.getInst1CdList(param));		// 기관코드

		model.setViewName("sicims300Cc/ccIsrdDetail");

		return model;
	}

	/**
	 * 불법하도급신고결과 상세 정보  뷰를 호출한다.
	 * @param param HashMap<String,Object>
	 * @param model ModelAndView
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/ccIsrrDetail", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView ccIsrrDetail(@RequestParam HashMap<String,Object> param
			, ModelAndView model) throws Exception {

		CcIsrrVo ccIsrrVo = new CcIsrrVo();
		if (param.get("rcptNo") != null && !param.get("rcptNo").equals("")) {
			ccIsrrVo = sicims300CcService.getCcIsrrInfo(param);
		} else {
			ccIsrrVo.setUpdusrId((String)param.get("updusrId"));
		}

		model.addObject("ccIsrrVo", ccIsrrVo);
		model.addObject("info", 	param);

		model.setViewName("sicims300Cc/ccIsrrDetail");

		return model;
	}




	/**
	 * 페이지 단위로 불법하도급신고 목록을 조회한다.
	 * @param param HashMap<String,Object>
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectCcIsrdList", method = RequestMethod.POST )
	public ModelAndView selectCcIsrdList(@RequestParam HashMap<String,Object> param) throws Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("result", sicims300CcService.selectCcIsrdList(param));

		return new ModelAndView(jsonView, result);
	}


	/**
	 * 불법하도급신고 정보를 등록한다.
	 * @param param HashMap<String,Object>
	 * @param ccIsrdVo CcIsrdVo
	 * @param request HttpServletRequest
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertCcIsrd", method = RequestMethod.POST)
	public ModelAndView  insertCcIsrd(@RequestParam HashMap<String,Object> param
			, @ModelAttribute("ccIsrdInfo") CcIsrdVo ccIsrdVo
			, HttpServletRequest request) throws Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request; // 다중파일
		MultiValueMap<String, MultipartFile> multiValueMap = multipartRequest.getMultiFileMap();

		result.put("result", sicims300CcService.insertCcIsrd(param, ccIsrdVo, multiValueMap));

		return new ModelAndView(jsonView, result);
	}


	/**
	 * 불법하도급신고 정보를 수정한다.
	 * @param param HashMap<String,Object>
	 * @param ccIsrdVo CcIsrdVo
	 * @param request HttpServletRequest
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateCcIsrd", method = RequestMethod.POST)
	public ModelAndView  updateCcIsrd(@RequestParam HashMap<String,Object> param,
			@ModelAttribute("ccIsrdInfo") CcIsrdVo ccIsrdVo,
			HttpServletRequest request) throws Exception {

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request; // 다중파일
		MultiValueMap<String, MultipartFile> multiValueMap = multipartRequest.getMultiFileMap();

		HashMap<String, Object> result	= new HashMap<String, Object>();

		result.put("result", 	sicims300CcService.updateCcIsrd(param, ccIsrdVo, multiValueMap));

		if (Integer.parseInt(String.valueOf(result.get("result"))) > 0) {
			result.put("ccIsrdVo", sicims300CcService.getCcIsrdInfo(param));
		}

		return new ModelAndView(jsonView, result);
	}


	/**
	 * 불법하도급신고 정보를  삭제한다.
	 * @param param HashMap<String,Object>
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteCcIsrd", method = RequestMethod.POST)
	public ModelAndView deleteCcIsrd(@RequestParam HashMap<String,Object> param) throws Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();

		param.put("updusrId", SessionUtil.getLoginUserId());
		param.put("updusrIp", SessionUtil.getLoginUserIp());

		result.put("result", sicims300CcService.deleteCcIsrd(param));


		return new ModelAndView(jsonView, result);
	}


	/**
	 * 불법하도급신고 파일 정보를  삭제한다.
	 * @param param HashMap<String,Object>
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteCcIsrdFile", method = RequestMethod.POST)
	public ModelAndView deleteCcIsrdFile(@RequestParam HashMap<String,Object> param) throws Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();

		param.put("updusrId", SessionUtil.getLoginUserId());
		param.put("updusrIp", SessionUtil.getLoginUserIp());

		result.put("result", sicims300CcService.deleteCcIsrdFile(param));

		return new ModelAndView(jsonView, result);
	}


	/**
	 * 불법하도급신고결과 정보를 저장한다.
	 * @param param HashMap<String,Object>
	 * @param userVo UserVo
	 * @param request HttpServletRequest
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveOrUpdateCcIsrr", method = RequestMethod.POST)
	public ModelAndView  saveOrUpdateCcIsrr(@RequestParam HashMap<String,Object> param,
			@ModelAttribute("ccIsrrInfo") CcIsrrVo ccIsrrVo,
			HttpServletRequest request) throws Exception {

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request; // 다중파일
		MultiValueMap<String, MultipartFile> multiValueMap = multipartRequest.getMultiFileMap();

		HashMap<String, Object> result	= new HashMap<String, Object>();

		try {
			result.put("result", 	sicims300CcService.saveOrUpdateCcIsrr(param, ccIsrrVo, multiValueMap));

			if (Integer.parseInt(String.valueOf(result.get("result"))) > 0) {
				result.put("isrdInfo", sicims300CcService.getCcIsrrInfo(param));
			}
		} catch (Exception e) {
			LOG.debug("불법하도급신고결과 정보 저장 실패");
			e.printStackTrace();
		}

		return new ModelAndView(jsonView, result);
	}


	/**
	 * 불법하도급신고결과 정보를  삭제한다.
	 * @param param HashMap<String,Object>
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteCcIsrr", method = RequestMethod.POST)
	public ModelAndView deleteCcIsrr(@RequestParam HashMap<String,Object> param) throws Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();

		param.put("updusrId", SessionUtil.getLoginUserId());
		param.put("updusrIp", SessionUtil.getLoginUserIp());

		result.put("result", sicims300CcService.deleteCcIsrr(param));


		return new ModelAndView(jsonView, result);
	}


	/**
	 * 불법하도급신고결과 파일 정보를  삭제한다.
	 * @param param HashMap<String,Object>
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteCcIsrrFile", method = RequestMethod.POST)
	public ModelAndView deleteCcIsrrFile(@RequestParam HashMap<String,Object> param) throws Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();

		param.put("updusrId", SessionUtil.getLoginUserId());
		param.put("updusrIp", SessionUtil.getLoginUserIp());

		result.put("result", sicims300CcService.deleteCcIsrrFile(param));

		return new ModelAndView(jsonView, result);
	}





}