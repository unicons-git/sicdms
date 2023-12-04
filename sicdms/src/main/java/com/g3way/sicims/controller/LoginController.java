package com.g3way.sicims.controller;

import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.g3way.sicims.annotation.PrvcInfoLog;
import com.g3way.sicims.annotation.RSAKeyGeneral;
import com.g3way.sicims.annotation.TopPushTotal;
import com.g3way.sicims.exception.SicimsException;
import com.g3way.sicims.services.sicims000Cm.Sicims000CmService;
import com.g3way.sicims.services.sicims900Sys.login.LoginService;
import com.g3way.sicims.services.sicims910User.Sicims910UserService;
import com.g3way.sicims.services.sicims910User.vo.CmUserVo;
import com.g3way.sicims.util.common.CalendarUtil;
import com.g3way.sicims.util.common.SessionUtil;
import com.g3way.sicims.util.common.StringUtil;
import com.g3way.sicims.util.encrypt.RsaUtil;

import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.rte.fdl.property.EgovPropertyService;



/***
 * 로그인 컨트롤러
 * @author dykim
 * @since 2019.01
 * @version 1.0.0
 */
@Controller
@RequestMapping("/login")
public class LoginController {
	private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

	@Autowired private LoginService			loginService;
	@Autowired private Sicims000CmService 	sicims000CmService;
	@Autowired private Sicims910UserService sicims910UserService;


	@Resource MappingJackson2JsonView	jsonView;
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;





	/*--------------------------------------------------	외부사용자 - 로그인&로그아웃	--------------------------------------------------*/
	/**
	 * 외부사용자 로그인 페이지 뷰를 호출한다.
	 * @param model ModelAndView
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RSAKeyGeneral
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(ModelAndView model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String,Object> result = new HashMap<String, Object>();
		ModelAndView returnModel = new ModelAndView();
		//이미 로그인 상태인경우 메인 페이지로 이동

		EgovUserDetailsHelper.getAuthenticatedUser();
		if(EgovUserDetailsHelper.isAuthenticated()) {
			returnModel.setViewName("redirect:/main.do");
			model.setViewName("redirect:/main.do");
		} else {
			returnModel.addObject("result", result);

			HashMap<String,Object> param = new HashMap<String, Object>();
			param.put("nowDate", CalendarUtil.getDateString());

			model.setViewName("login/login");
		}

		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response.setHeader("Expires", "0"); // Proxies.
		response.setDateHeader("Expires",1L);

		return returnModel;
	}

	/**
	 * 로그인 페이지 뷰를 호출한다.
	 * @param model ModelAndView
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView loginPost(ModelAndView model, HttpServletRequest request) throws Exception {
		HashMap<String,Object> result = new HashMap<String, Object>();
		ModelAndView returnModel = new ModelAndView();
		result.put("status", 	"fail");
		result.put("message", 	"접근권한이 없습니다.");

		returnModel.addObject("result", result);
		returnModel.setView(jsonView);
		returnModel.setStatus(HttpStatus.UNAUTHORIZED);

		return returnModel;
	}

	/**
	 * 로그아웃 페이지 뷰를 호출한다.
	 * @param model ModelAndView
	 * @param request HttpServletRequest
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/logout", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView logout(ModelAndView model, HttpServletRequest request) throws Exception {
		model.setViewName("login/login");

		return model;
	}

	/**
	 * 사용자 정보를 조회한다.
	 * @param param HashMap<String,Object>
	 * @return HashMap<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/getCmUserInfo", method = RequestMethod.POST)
	public ModelAndView getUserInfo(@RequestParam HashMap<String,Object> param
			, @RequestParam(required = false, value = "userId") String userId
			, @RequestParam(required = false, value = "userPw") String userPw
			, HttpServletRequest request) throws Exception {
		HashMap<String,Object> result = new HashMap<String, Object>();

		return new ModelAndView(jsonView, result);
	}

	/**
	 * 사용자 로그인 성공 .
	 * @param param HashMap<String,Object>
	 * @return HashMap<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/loginSuccess", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView loginSuccess(@RequestParam HashMap<String,Object> param
			, HttpServletRequest request) throws Exception {

		HashMap<String,Object> result = new HashMap<String, Object>();
		result.put("status", 	"success");
		result.put("message", 	"");

		return new ModelAndView(jsonView, result);
	}

	/**
	 * 로그인 실패
	 * @param model ModelAndView
	 * @param request HttpServletRequest
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/loginFail", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView loginFail(@RequestParam HashMap<String,Object> param
		, HttpServletRequest request) throws Exception {

		HashMap<String,Object> result = new HashMap<String, Object>();
		result.put("status", 	"fail");
		result.put("message", 	request.getAttribute("message"));
		result.put("errorCode", request.getAttribute("errorCode"));

		return new ModelAndView(jsonView, result);

	}


	/**
	 * 로그아웃 페이지 뷰를 호출한다.
	 * @param model ModelAndView
	 * @param request HttpServletRequest
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RSAKeyGeneral
	@RequestMapping(value = "/loginExpired", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView loginExpired(ModelAndView model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.getHeader("referer");
		String message = "다른곳에서 동일 아이디로 접속하여 로그인 상태가 해제되었습니다.";
		model.addObject("message",  message);
		model.setViewName("login/loginExpired");

		return model;
	}
	/*--------------------------------------------------	회원 - 로그인&로그아웃	--------------------------------------------------*/

	/*--------------------------------------------------	회원 - 회원가입	--------------------------------------------------*/
	/**
	 * 사용자ID 찾기 페이지 뷰를 호출한다.
	 * @param model ModelAndView
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RSAKeyGeneral
	@RequestMapping(value = "/join/findUserId", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView findUserId(ModelAndView model) throws Exception {
		model.addObject("searchbox", "N");
		model.setViewName("login/findUserId");

		return model;
	}


	/**
	 * 인사대체키로 사용자ID를 조회한다.
	 * @param param HashMap<String,Object>
	 * @return HashMap<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/join/getUserId", method = RequestMethod.POST)
	public ModelAndView selectUserId(@RequestParam HashMap<String,Object> param) throws Exception {
		HashMap<String,Object> result = new HashMap<String, Object>();

		result.put("result",  loginService.getUserId(param));

		return new ModelAndView(jsonView, result);
	}

	/**
	 * 사용자 비밀번호 변경 페이지를 호출한다.
	 * @param param HashMap<String,Object>
	 * @return HashMap<String, Object>
	 * @throws Exception
	 */
	@RSAKeyGeneral
	@RequestMapping(value = "/join/changePswd", method=RequestMethod.GET)
	public ModelAndView changePswd(ModelAndView model, HttpServletRequest request) throws Exception {
		model.setViewName("login/changePswd");

		return model;
	}

	/**
	 * 외부사용자 비밀번호 변경 페이지를 호출한다.
	 * @param param HashMap<String,Object>
	 * @return HashMap<String, Object>
	 * @throws Exception
	 */
	@RSAKeyGeneral
	@RequestMapping(value = "/join/ecmpChangePswd", method=RequestMethod.GET)
	public ModelAndView ecmpChangePswd(ModelAndView model, HttpServletRequest request) throws Exception {
		model.setViewName("login/ecmpChangePswd");

		return model;
	}


	/**
	 * 사용자 비밀번호를 변경한다.
	 * @param param HashMap<String,Object>
	 * @return HashMap<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/join/updatePswd", method=RequestMethod.POST)
	public ModelAndView updatePswd(@ModelAttribute("CmUserVo") CmUserVo cmUserVo) {
		HashMap<String, Object> result = new HashMap<String, Object>();

		try{
			result.put("result", loginService.updatePswd(cmUserVo));
		} catch(SicimsException e) {
			result.put("resultMsg", e.getMessage());
		} catch(Exception e) {
			LOG.error("회원 암호 수정 중 오류 발생");
		}

		return new ModelAndView(jsonView, result);
	}

	/**
	 * 외부사용자 비밀번호를 변경한다.
	 * @param param HashMap<String,Object>
	 * @return HashMap<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/join/ecmpUpdatePswd", method=RequestMethod.POST)
	public ModelAndView ecmpUpdatePswd(@ModelAttribute("CmUserVo") CmUserVo cmUserVo) {
		HashMap<String, Object> result = new HashMap<String, Object>();

		try{
			result.put("result", loginService.ecmpUpdatePswd(cmUserVo));
		} catch(SicimsException e) {
			result.put("resultMsg", e.getMessage());
		} catch(Exception e) {
			LOG.error("회원 암호 수정 중 오류 발생");
		}

		return new ModelAndView(jsonView, result);
	}


	/**
	 * 사용자 비밀번호 찾기 페이지 뷰를 호출한다.
	 * @param model ModelAndView
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/join/findUserPswd", method=RequestMethod.GET)
	public ModelAndView findUserPswd(ModelAndView model
			,@RequestParam(required = false,  value = "action") String action
			,@RequestParam(required = false,  value = "findUserId") String findUserId
			,HttpServletRequest request
			,HttpServletResponse response
			) {

		//본인인증인 페이지
		if("contactCheck".equals(action)){
			model.addObject("action", action);
			model.addObject("findUserId", findUserId);
			model.setViewName("login/findUserPswd");

		     SessionUtil.setAttribute("findUserId", findUserId);
		} else if("passwordChange".equals(action)){
			//본인인증후 비밀번호 재설정 페이지
			model.setViewName("login/findUserPswd");
		} else {
			model.setViewName("login/findUserPswd");
		}

		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response.setHeader("Expires", "0"); // Proxies.
		response.setDateHeader("Expires",1L);

		return model;
	}



	@RSAKeyGeneral
	@RequestMapping(value = "/join/passWordFindChange", method = {RequestMethod.GET})
	public ModelAndView passWordFindChange(ModelAndView model
			,HttpServletResponse response
			){

		if(SessionUtil.getAttribute("niceVo") == null  ||  SessionUtil.getAttribute("niceVo").getClass() != CmUserVo.class){
			model.setViewName("login/findUserPswd");
			model.addObject("errorMsg", "본인인증 유효기간이 완료되어 비밀번호 찾기를 완료 할 수 없습니다.");
		}
		else {
			model.addObject("findPassword", "Y");
			model.setViewName("login/newPassWord");
		}

		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response.setHeader("Expires", "0"); // Proxies.
		response.setDateHeader("Expires",1L);

		return model;
	}


	/**
	 * 회원 가입 화면  페이지 뷰를 호출한다.
	 * @param model ModelAndView
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RSAKeyGeneral
	@RequestMapping(value = "/join/joinMember", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView joinMember(ModelAndView model) throws Exception {
		model.addObject("inst1CdList", 	sicims000CmService.getInst1CdList(null));			// 기관1목록

		model.setViewName("login/joinMember");

		return model;
	}


	/**
	 * 인사대체키 얻는법 페이지 뷰를 호출한다.
	 * @param param HashMap<String,Object>
	 * @param model ModelAndView
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/join/hrscRplcKeyPopup", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView hrscRplcKeyPopup(@RequestParam HashMap<String,Object> param
			, ModelAndView model) throws Exception {

		model.setViewName("login/hrscRplcKeyPopup");
		return model;
	}



	/**
	 * 사용자ID 중복을 체크한다.
	 * @param param HashMap<String,Object>
	 * @return HashMap<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/join/checkUserId", method = RequestMethod.POST)
	public ModelAndView checkUserId(@RequestParam HashMap<String,Object> param) throws Exception {
		HashMap<String,Object> result = new HashMap<String, Object>();

		result.put("result", sicims910UserService.getCmUserInfo(param));

		return new ModelAndView(jsonView, result);
	}


	/**
	 * 기관2 목록을 조회한다.
	 * @param param HashMap<String,Object>
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/join/getInst2CdList", method = RequestMethod.POST )
	public ModelAndView getInst2CdList(@RequestParam HashMap<String,Object> param) throws Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("result", sicims000CmService.getInst2CdList(param));

		return new ModelAndView(jsonView, result);
	}

	/**
	 * 회원정보를 등록한다.
	 * @param loginVo LoginVo
	 * @return HashMap<String, Object>
	 * @throws Exception
	 */
	@PrvcInfoLog
	@RequestMapping(value = "/join/regMember", method=RequestMethod.POST)
	public ModelAndView regMember(@ModelAttribute("cmUserInfo") CmUserVo cmUserVo) {
		HashMap<String, Object> result = new HashMap<String, Object>();

		try{
			result.put("result", loginService.insertCmUser(cmUserVo));
		} catch(SicimsException e) {
			e.printStackTrace();
			result.put("resultMsg", e.getMessage());
		} catch(Exception e) {
			e.printStackTrace();
			LOG.error("회원 가입 중 오류 발생");
		}

		return new ModelAndView(jsonView, result);
	}



	/*--------------------------------------------------	회원 - 회원가입	--------------------------------------------------*/

	/*--------------------------------------------------	회원 - 회원정보 수정 --------------------------------------------------*/
	/**
	 * 회원정보를 수정하기 위한 비밀번호 체크 화면 페이지를 호출한다.
	 * @param model ModelAndView
	 * @param request HttpServletRequest
	 * @return ModelAndView
	 * @throws Exception
	 */
	@TopPushTotal
	@RSAKeyGeneral
	@RequestMapping(value = "/memberInfo/modifyMemberCheck", method=RequestMethod.GET)
	public ModelAndView modifyMemberCheck(ModelAndView model, HttpServletRequest request) throws Exception {
		if(SessionUtil.getLoginUserId() == null || "".equals(SessionUtil.getLoginUserId())) {
			model.addObject("message", "로그인 정보가 없습니다. 로그인 후 다시 시도해주시기 바랍니다.");
		}

		model.setViewName("login/modifyMemberPswdCheck");

		return model;
	}


	/**
	 * 회원정보를 수정하기 위한 비밀번호 체크 화면 페이지를 호출한다.
	 * @param loginInfo LoginVo
	 * @param model ModelAndView
	 * @param request HttpServletRequest
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/memberInfo/modifyMemberPswdCheck", method=RequestMethod.POST)
	public ModelAndView modifyMemberPswdCheck(@ModelAttribute("CmUserVo") CmUserVo cmUserVo, ModelAndView model, HttpServletRequest request) throws Exception {
		HashMap<String,Object> result = new HashMap<String, Object>();

		String userId = SessionUtil.getLoginUserId();
		cmUserVo.setUserId(userId);

		//비밀번호 복호화
		if(cmUserVo.getUserPswd() != null ) {
			cmUserVo.setUserPswd(RsaUtil.decryptRsa(cmUserVo.getUserPswd()));
		}

		CmUserVo searchCmUserVo = loginService.getLoginInfo(cmUserVo);
		if(searchCmUserVo == null || searchCmUserVo.getUserId() == null || "".equals(searchCmUserVo.getUserId())){
			result.put("status", 	"fail");
			result.put("resultMsg", "입력하신 비밀번호가 일치하지 않습니다.");
		} else {
			result.put("status", 	"success");
			SessionUtil.setAttribute("modifyMemberPswdCheck", "Y");
		}

		return new ModelAndView(jsonView, result);
	}


	/**
	 * 회원정보 수정 화면 페이지를 호출한다.
	 * @param model ModelAndView
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @return ModelAndView
	 * @throws Exception
	 */
	@TopPushTotal
	@RSAKeyGeneral
	@RequestMapping(value = "/memberInfo/modifyMember", method=RequestMethod.GET)
	public ModelAndView modifyMember(ModelAndView model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if(!"Y".equals(SessionUtil.getAttribute("modifyMemberPswdCheck"))) {
			model.addObject("errorMsg", "잘못된 접근입니다.");
			model.setViewName("login/modifyMemberPswdCheck");
		} else {
			//회원정보 수정 인증 세션 삭제
			SessionUtil.removeAttribute("modifyMemberPswdCheck");

			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("userId", SessionUtil.getLoginUserId());

			CmUserVo  userVo  = sicims910UserService.getCmUserInfo(param);

			model.addObject("userInfo", 	userVo);
			model.addObject("userSeList", 	sicims000CmService.getCmCodeList("CM", "0004"));

			//기관1코드 검색
			if(userVo != null && userVo.getInst1Cd() != null && !"".equals(userVo.getInst1Cd().trim())){
				param.put("inst1Cd", userVo.getInst1Cd());
			}

			//기관2코드 검색
			if(userVo != null && userVo.getInst2Cd() != null && !"".equals(userVo.getInst2Cd().trim())){
				param.put("inst2Cd", userVo.getInst2Cd());
			}


			//전화번호 포맷형식 변경
			if(userVo != null  && userVo.getTelno() != null && !"".equals(userVo.getTelno())){
				userVo.setTelno(StringUtil.getPhoneFormat(userVo.getTelno()));
			}

			model.addObject("inst1CdList", 	sicims000CmService.getInst1CdList(param));		// 기관1목록
			model.addObject("inst2CdList", 	sicims000CmService.getInst2CdList(param));		// 기관2목록

			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
			response.setHeader("Pragma", "no-cache"); 	// HTTP 1.0.
			response.setHeader("Expires", "0");			// Proxies.
			response.setDateHeader("Expires",1L);

			model.addObject("searchbox", "N");
			model.setViewName("login/modifyMember");
		}

		return model;
	}


	@RSAKeyGeneral
	@TopPushTotal
	@RequestMapping(value = "/memberInfo/changePassword", method=RequestMethod.GET)
	public ModelAndView changePassword(ModelAndView model, HttpServletRequest request) throws Exception {
		model.setViewName("login/changePassword");
		SessionUtil.setAttribute("modifyMemberPswdCheck", "Y");

		return model;
	}


	@PrvcInfoLog
	@RequestMapping(value = "/memberInfo/updateCmUser", method=RequestMethod.POST)
	public ModelAndView updateUserInfo(@ModelAttribute("CmUserVo") CmUserVo cmUserVo) {
		HashMap<String, Object> result = new HashMap<String, Object>();

		try{
			result.put("result", loginService.updateCmUser(cmUserVo));
		} catch(SicimsException e) {
			result.put("resultMsg", e.getMessage());
		}  catch(Exception e) {
			LOG.error("회원 정보 수정 중 오류 발생");
		}

		return new ModelAndView(jsonView, result);
	}

	@RequestMapping(value = "/memberInfo/updatePassWord", method=RequestMethod.POST)
	public ModelAndView updatePassWord(@ModelAttribute("CmUserVo") CmUserVo cmUserVo) {
		HashMap<String, Object> result = new HashMap<String, Object>();

		try{
			result.put("result", loginService.updateCmUserPswd(cmUserVo));
		} catch(SicimsException e) {
			result.put("resultMsg", e.getMessage());
		} catch(Exception e) {
			LOG.error("회원 암호 수정 중 오류 발생");
		}

		return new ModelAndView(jsonView, result);
	}


	/**
	 * 회원탈퇴 뷰를 호출한다.
	 * @param model ModelAndView
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/memberInfo/deleteMember", method=RequestMethod.GET)
	public ModelAndView deleteMember(ModelAndView model) throws Exception {
		model.addObject("searchbox", "N");
		model.setViewName("login/deleteMember");

		return model;
	}

	/**
	 * 회원탈퇴
	 * @param model ModelAndView
	 * @return ModelAndView
	 * @throws Exception
	 */
	@PrvcInfoLog
	@RequestMapping(value = "/memberInfo/deleteCmUser", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView deleteCmUser(@ModelAttribute("CmUserVo") CmUserVo cmUserVo) throws Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();

		try{
			result.put("result", loginService.deleteCmUser(cmUserVo));
		} catch(SicimsException e) {
			result.put("resultMsg", e.getMessage());
		} catch(Exception e) {
			LOG.error("회원 탈퇴 중 오류 발생");
		}
		return new ModelAndView(jsonView, result);
	}

	/*--------------------------------------------------	회원 - 회원정보 수정 --------------------------------------------------*/




	/**
	 * 세션 타임아웃  페이지 뷰를 호출한다.
	 * @param model ModelAndView
	 * @param request HttpServletRequest
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/sessionTimeout", method = {RequestMethod.GET})
	public ModelAndView sessionTimeout(ModelAndView model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.getSession().removeAttribute("member");

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null){
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}

		model.setViewName("login/sessionTimeout");

		return model;
	}

	/**
	 * 자동 로그아웃 페이지 뷰를 호출한다.
	 * @param model ModelAndView
	 * @param request HttpServletRequest
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/autoLogout", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView autoLogout(ModelAndView model, HttpServletRequest request) throws Exception {
		model.setViewName("login/autoLogout");

		return model;
	}

	/**
	 * 세션 유지
	 * @param model ModelAndView
	 * @param request HttpServletRequest
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/keepSessionAjax", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public @ResponseBody String keepSessionAjax() throws Exception {

		return "success";
	}




}
