package com.g3way.sicims.services.sicims000Cm;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.g3way.sicims.exception.SicimsException;
import com.innorix.transfer.InnorixUpload;
import egovframework.rte.fdl.property.EgovPropertyService;




/***
 * 공통 컨트롤러
 */
@Controller
@RequestMapping("/sicims000Cm")
public class Sicims000CmController {
	private static final Logger LOG = LoggerFactory.getLogger(Sicims000CmController.class);

	@Autowired private Sicims000CmService 	sicims000CmService;

	@Resource(name = "propertiesService") protected EgovPropertyService propertiesService;
	@Resource MappingJackson2JsonView		jsonView;


	/********************************/
	/*		1. 기관 목록 조회				*/
	/********************************/
	/**
	 * 기관2 목록을 조회한다.
	 * @param param HashMap<String,Object>
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/getInst2CdList", method = RequestMethod.POST )
	public ModelAndView getInst2CdList(@RequestParam HashMap<String,Object> param) throws Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("result", sicims000CmService.getInst2CdList(param));

		return new ModelAndView(jsonView, result);
	}





	/********************************/
	/*		2. 메세지 박스				*/
	/********************************/
	/**
	 * 메세지  페이지 뷰를 호출한다.
	 * @param model ModelAndView
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/messageBox", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView messageBox(@RequestParam HashMap<String,Object> param
			, ModelAndView model) throws Exception {
		model.addObject("info", param);
		model.setViewName("sicims000Cm/messageBox");

		return model;
	}





	/********************************/
	/*		3. 파일 다운로드				*/
	/********************************/
	/**
	 * 파일을 다운로드 한다.
	 * @param model ModelAndView
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/downloadFile", method = {RequestMethod.GET, RequestMethod.POST})
	public void downloadFile(@RequestParam HashMap<String,Object> param
			, HttpServletRequest request
			, HttpServletResponse response) throws Exception {

		HashMap<String, Object> result = new HashMap<String, Object>();
		try {
			sicims000CmService.downloadFile(param, request, response);
		} catch(SicimsException e) {
			result.put("resultMsg", e.getMessage());
		} catch(Exception e) {
			e.printStackTrace();
			LOG.error("파일다운로드 중 오류 발생");
		}
	}


	/**
	 * 파일을 다운로드 한다.
	 * @param param HashMap<String,Object>
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return void
	 * @throws Exception
	 */
	@RequestMapping(value = "/downloadFileNm", method = RequestMethod.GET)
	public void downloadFileNm(@RequestParam HashMap<String,Object> param
			, HttpServletRequest request
			, HttpServletResponse response ) throws Exception {
		if (param.get("filePath") == null || param.get("filePath").equals("")) {
			sicims000CmService.downloadFileNm((String)param.get("fileName"), request, response);
		} else {
			sicims000CmService.downloadFileNm(param, request, response);
		}
	}





	/********************************/
	/*		4. 로그인					*/
	/********************************/
	@RequestMapping(value = "/accessDenied", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView accessDenied(@RequestParam HashMap<String,Object> param
			, ModelAndView model
			, HttpServletRequest request) throws Exception {
		HashMap<String,Object> result = new HashMap<String, Object>();
		ModelAndView returnModel = new ModelAndView();
		result.put("status", 	"fail");
		result.put("message", 	"접근권한이 없습니다.");

		returnModel.addObject("result", result);

		if("GET".equals(request.getMethod())){
			returnModel.setViewName("sicims000Cm/accessDenied");
			returnModel.setStatus(HttpStatus.OK);
		} else {
			returnModel.setView(jsonView);
			returnModel.setStatus(HttpStatus.NOT_ACCEPTABLE);
		}

		return returnModel;
	}


	@RequestMapping(value = "/egovError", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView egovError(ModelAndView model, HttpServletRequest request, HttpServletResponse response) throws Exception {

		model.setViewName("sicims000Cm/egovError");

		return model;
	}



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

		model.setViewName("sicims000Cm/sessionTimeout");

		return model;
	}



	/********************************/
	/*		5. 출력					*/
	/********************************/
	/**
	 * 출력 페이지 뷰를 호출한다.
	 * @param param HashMap<String,Object>
	 * @param model ModelAndView
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/rdPrint", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView rdPrint(@RequestParam HashMap<String,Object> param
			, ModelAndView model) throws Exception {

		model.addObject("reportingServer", propertiesService.getString("reportingServer"));
		model.addObject("info", param);

		model.setViewName("sicims000Cm/rdPrint");

		return model;
	}


	/********************************/
	/*		6. 카카오맵					*/
	/********************************/
	/**
	 * 카카오맵 페이지 뷰를 호출한다.
	 * @param param HashMap<String,Object>
	 * @param model ModelAndView
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/kakaoMap", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView kakaoMap(@RequestParam HashMap<String,Object> param
			,ModelAndView model) throws Exception {

		model.addObject("info", param);
		model.setViewName("sicims000Cm/kakaoMap");

		return model;
	}



	/********************************/
	/*		7. INNORIX 파일 업로드		*/
	/********************************/
	/**
	 * innorix 툴을 이용하여 파일을 업로드 한다.
	 * @param param HashMap<String,Object>
	 * @param model ModelAndView
	 * @return
	 * @throws Exception
	 */

	@RequestMapping(value = "/innorix/uploadFile", method = {RequestMethod.POST})
	public void uploadFile(HttpServletRequest request
			, HttpServletResponse response
			) throws Exception {

		String directory = propertiesService.getString("uploadPath");
		directory += System.getProperty("file.separator") + "csex" + System.getProperty("file.separator") + sicims000CmService.getCmnnYear(null);
		int maxPostSize = propertiesService.getInt("maxUploadSize");

		request.setCharacterEncoding("UTF-8");
		// CORS 관련 헤더 추가
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Methods", "POST, OPTIONS");
		response.setHeader("Access-Control-Allow-Headers", "Authorization,DNT,X-Mx-ReqToken,Keep-Alive,User-Agent,X-Requested-With,If-Modified-Since,Cache-Control,Content-Type");
		response.setContentType("text/html; charset=UTF-8");

		try {
			InnorixUpload uploader = new InnorixUpload(request, response, maxPostSize, "UTF-8", directory);

			/*
				전달되는 _action Param 정보
				speedCheck          : 전송속도 측정
				getServerInfo       : 서버정보 확인
				getFileInfo         : 파일정보 확인
				attachFile          : 파일전송 진행
				attachFileCompleted : 파일전송 완료
			 */

			String action          = uploader.getParameter("_action");         // 동작 플래그
			String orig_filename   = uploader.getParameter("_orig_filename");  // 원본 파일명
			String new_filename    = uploader.getParameter("_new_filename");   // 저장 파일명
			String filesize        = uploader.getParameter("_filesize");       // 파일 사이즈
			String start_offset    = uploader.getParameter("_start_offset");   // 파일저장 시작지점
			String end_offset      = uploader.getParameter("_end_offset");     // 파일저장 종료지점
			String filepath        = uploader.getParameter("_filepath");       // 파일 저장경로
			String el              = uploader.getParameter("el");              // 컨트롤 엘리먼트 ID
			String type            = uploader.getParameter("type");            // 커스텀 정의 POST Param 1
			String part            = uploader.getParameter("part");            // 커스텀 정의 POST Param 2
			String transferId	   = uploader.getParameter("_transferId");		// TransferId
			String run_retval = uploader.run();

			// 개별파일 업로드 완료
			if (uploader.isUploadDone()) {
				LOG.debug("========== uploader.isUploadDone() " + System.currentTimeMillis() + " ==========");

				LOG.debug("_orig_filename \t = " + orig_filename);
				LOG.debug("_new_filename \t = " + new_filename);
				LOG.debug("_filesize \t = " + filesize);
				LOG.debug("_filepath \t = " + filepath);
				LOG.debug("_el \t = " + el);
			}
			LOG.debug("========== innorix transfer " + System.currentTimeMillis() + " ==========");
			LOG.debug("_action \t = " + action);
			LOG.debug("_run_retval \t = " + run_retval);
			LOG.debug("_orig_filename \t = " + orig_filename);
			LOG.debug("_new_filename \t = " + new_filename);
			LOG.debug("_filesize \t = " + filesize);
			LOG.debug("_start_offset \t = " + start_offset);
			LOG.debug("_end_offset \t = " + end_offset);
			LOG.debug("_filepath \t = " + filepath);
			LOG.debug("_el \t = " + el);
			LOG.debug("_type \t = " + type);
			LOG.debug("_part \t = " + part);
		} catch (IOException e) {
			e.printStackTrace();
			LOG.error("첨부파일 업로드 중 IOException이 발생했습니다.");
			String xml = "<file_code>" + "1001" + "</file_code>\n" + "<file_customerrorcode>" + "1001" + "</file_customerrorcode>\n" + "<file_custommsg>" + "업로드중 파일 오류가 발생했습니다." + "</file_custommsg>\n" + "<file_customdetail>" + "업로드중 파일 오류가 발생했습니다" + "</file_customdetail>\n" + "<file_confirm>" + false + "</file_confirm>\n";
			try(
				PrintWriter out = response.getWriter();
			){
				out.println(xml);
			} catch (IOException e1) {
				LOG.error("response.getWriter 중 IOException이 발생했습니다.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("첨부파일 업로드 중 Exception이 발생했습니다.");
			String xml = "<file_code>" + "1002" + "</file_code>\n" + "<file_customerrorcode>" + "1002" + "</file_customerrorcode>\n" + "<file_custommsg>" + "업로드중 오류가 발생했습니다." + "</file_custommsg>\n" + "<file_customdetail>" + "업로드중 오류가 발생했습니다" + "</file_customdetail>\n" + "<file_confirm>" + false + "</file_confirm>\n";
			try(
				PrintWriter out = response.getWriter();
			){
				out.println(xml);
			} catch (IOException e1) {
				LOG.error("response.getWriter 중 IOException이 발생했습니다.");
			}
		}
	}

}
