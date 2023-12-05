package com.g3way.sicims.services.sicims000Cm;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.g3way.sicims.services.sicims000Cm.vo.CmCodeVo;
import com.g3way.sicims.services.sicims000Cm.vo.CmFileVo;


public interface Sicims000CmService {
	/********************************/
	/*		0. 공통정보					*/
	/********************************/
	/**
	 * 시스템의 년도를 조회한다.
	 * @param param HashMap<String, Object>
	 * @return String
	 */
	public String getCmnnYear(HashMap<String, Object> param);




	/********************************/
	/*		1. 공통코드 관리				*/
	/********************************/
	/**
	 * 공통코드 목록을 조회한다.
	 * @param cmcdTy String
	 * @param cmcdSe String
	 * @return List<CmCodeVo>
	 */
	public List<CmCodeVo> getCmCodeList(String cmcdTy,  String cmcdSe);





	/********************************/
	/*		2. 기관 목록 조회				*/
	/********************************/
	/**
	 * 기관코드 목록을 조회한다.
	 * @param  param HashMap<String, Object>
	 * @return List<String>
	 */
	public List<String> getInstCdList(HashMap<String, Object> param);


	/**
	 * 기관1 목록을 조회한다.
	 * @param  gubun String
	 * @return List<CmMainVo>
	 */
	public List<HashMap<String, Object>> getInst1CdList(HashMap<String, Object> param);


	/**
	 * 기관2 목록을 조회한다.
	 * @param  gubun String
	 * @return List<CmMainVo>
	 */
	public List<HashMap<String, Object>> getInst2CdList(HashMap<String, Object> param);






	/********************************/
	/*		3. 공통 파일 관리				*/
	/********************************/
	/**
	 * 파일정보를 조회한다.
	 * @param fileSn BigDecimal
	 * @return CmFileVo
	 */
	public CmFileVo getCmFileInfo(BigDecimal fileId);

	/**
	 * 업로드 파일 및 파일정보를 저장한다.
	 * @param param CmFileVo
	 * @param multipartFile MultipartFile
	 * @return BigDecimal
	 */
	public BigDecimal saveCmFile(CmFileVo param, MultipartFile multipartFile);


	/**
	 * 업로드 파일정보를 저장한다.
	 * @param param CmFileVo
	 * @return int
	 */
	public int insertCmFile(CmFileVo param);

	/**
	 * 업로드 파일 및 파일정보를 등록한다.
	 * @param param CmFileVo
	 * @param multipartFile MultipartFile
	 * @return CmFileVo
	 */
	public CmFileVo insertCmFile(CmFileVo param, MultipartFile multipartFile);

	/**
	 * 업로드 파일 및 파일정보를 저장한다.
	 * @param param CmFileVo
	 * @param multipartFile MultipartFile
	 * @return CmFileVo
	 */
	public CmFileVo insertCmFileNm(CmFileVo param, MultipartFile multipartFile);

	/**
	 * 업로드 파일 및 파일정보를 수정한다.
	 * @param param CmFileVo
	 * @param multipartFile MultipartFile
	 * @return BigDecimal
	 */
	public BigDecimal updateCmFile(CmFileVo param, MultipartFile multipartFile);



	/**
	 * 파일 정보를 삭제한다.
	 * @param fileId BigDecimal
	 * @return HashMap<String, Object>
	 */
	public HashMap<String, Object> deleteCmFile(BigDecimal fileId);

	/**
	 * 파일확장자를 조회한다.
	 * @param String String
	 * @return String
	 */
	public String getFileExtension(String fileNm);

	/**
	 * 파일이름를 조회한다.
	 * @param String String
	 * @return String
	 */
	public String getFileOrgnNm(String fileNm);



	/**
	 * 파일 스트림(stream)을 가져온다.
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @return void
	 * @throws Exception
	 */
	public void getFileStream(HashMap<String,Object> param, HttpServletRequest request, HttpServletResponse response) throws IOException;

	/**
	 * 파일을 업로드한다.
	 * @param param HashMap<String,Object>
	 * @param request HttpServletRequest
	 * @param fileList List<MultipartFile>
	 * @return HashMap<String, Object>
	 * @throws Exception
	 */
	public HashMap<String, Object> insertFile(HashMap<String,Object> param, HttpServletRequest request, List<MultipartFile> fileList) throws IOException;

	/**
	 * 파일을 다운로드 한다.
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return void
	 * @throws IOException
	 * @throws Exception
	 */
	public void downloadFile(HashMap<String,Object> param, HttpServletRequest request, HttpServletResponse response ) throws IOException ;

	/**
	 * 파일을 다운로드 한다.
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return void
	 * @throws IOException
	 */
	public void downloadFileNm(HashMap<String,Object> param, HttpServletRequest request, HttpServletResponse response ) throws IOException ;


	/**
	 * 파일을 다운로드 한다.
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return void
	 * @throws IOException
	 */
	public void downloadFileNm(String fileNm, HttpServletRequest request, HttpServletResponse response ) throws IOException ;

}
