package com.g3way.sicims.services.sicims000Cm;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.g3way.sicims.services.sicims000Cm.vo.CmCodeVo;
import com.g3way.sicims.services.sicims000Cm.vo.CmFileVo;
import com.g3way.sicims.util.common.UploadUtil;
import com.google.gson.JsonObject;

import egovframework.rte.fdl.property.EgovPropertyService;


@Service
public class Sicims000CmServiceImpl implements Sicims000CmService{

	private static final Logger LOG = LoggerFactory.getLogger(Sicims000CmServiceImpl.class);

	 private static final Object SYNC1 = new Object() ;

	 private static final Object SYNC2 = new Object() ;

	 private static final Object SYNC3 = new Object() ;

	@Autowired	private Sicims000CmDao 		sicims000CmDao;
	@Autowired 	private	Sicims000CmService	sicims000CmService;

	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;



	/********************************/
	/*		0. 공통정보					*/
	/********************************/
	/**
	 * 시스템의 년도를 조회한다.
	 * @param param HashMap<String, Object>
	 * @return String
	 */
	@Override
	public String getCmnnYear(HashMap<String, Object> param){
		String info = "9999";
		try {
			info = sicims000CmDao.getCmnnYear(param);
		} catch (DataAccessException e) {
			info = "9999";
		}
		return info;
	}



	/********************************/
	/*		1. 공통코드 관리				*/
	/********************************/
	/**
	 * 공통코드 목록을 조회한다.
	 * @param cmcdTy String
	 * @param cmcdSe String
	 * @return List<CmCodeVo>
	 */
	@Override
	public List<CmCodeVo> getCmCodeList(String cmcdTy,  String cmcdSe){
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("cmcdTy", cmcdTy);
		param.put("cmcdSe", cmcdSe);

		try {
			return sicims000CmDao.getCmCodeList(param);
		} catch (DataAccessException e) {
			return new ArrayList<CmCodeVo>();
		}
	}





	/********************************/
	/*		2. 기관 목록 조회				*/
	/********************************/
	/**
	 * 기관코드 목록을 조회한다.
	 * @param  param HashMap<String, Object>
	 * @return List<String>
	 */
	public List<String> getInstCdList(HashMap<String, Object> param){
		try {
			return sicims000CmDao.getInstCdList(param);
		} catch (DataAccessException e) {
			return new ArrayList<String>();
		}
	}


	/**
	 * 기관1 목록을 조회한다.
	 * @param  gubun String
	 * @return List<CmMainVo>
	 */
	@Override
	public List<HashMap<String, Object>> getInst1CdList(HashMap<String, Object> param) {
		try {
			return sicims000CmDao.getInst1CdList(param);
		} catch (DataAccessException e) {
			return new ArrayList<HashMap<String, Object>>();
		}
	}


	/**
	 * 기관2 목록을 조회한다.
	 * @param  gubun String
	 * @return List<CmMainVo>
	 */
	@Override
	public List<HashMap<String, Object>> getInst2CdList(HashMap<String, Object> param){
		try {
			return sicims000CmDao.getInst2CdList(param);
		} catch (DataAccessException e) {
			return new ArrayList<HashMap<String, Object>>();
		}
	}





	/********************************/
	/*		3.공통 파일 관리				*/
	/********************************/
	/**
	 * 파일정보를 조회한다.
	 * @param fileId String
	 * @return CmFileVo
	 */
	@Override
	public CmFileVo getCmFileInfo(BigDecimal fileId){
		try {
			CmFileVo info = sicims000CmDao.getCmFileInfo(fileId);
			if (info == null) info = new CmFileVo();
			return info;
		} catch (DataAccessException e) {
			return new CmFileVo();
		}
	}

	/**
	 * 업로드 파일 및 파일정보를 저장한다.
	 * @param param CmFileVo
	 * @param multipartFile MultipartFile
	 * @return BigDecimal
	 */
	@Override
	public BigDecimal saveCmFile(CmFileVo param, MultipartFile multipartFile) {
		BigDecimal returnValue	= null;
		String fileNm 			= multipartFile.getOriginalFilename();
		String allowFileExt		= (propertiesService.getString("uploadExt") != null )? propertiesService.getString("uploadExt") : "hwp,pdf";	//파일업로드 허용 확장자
		String[] fileExts   	= allowFileExt.split(",");

		for(String fileExtNm : fileExts) {
			if(fileNm == null || !fileNm.toLowerCase().endsWith(fileExtNm)) {
				continue;
			}
			else {
				param.setFileOrgnlNm(multipartFile.getOriginalFilename());			/* 파일원본명 */
				param.setFileExtn(getFileExtension(param.getFileOrgnlNm()));		/* 파일확장자 */
				param.setFileSz(new BigDecimal(multipartFile.getSize()));			/* 파일사이즈 */

				if (sicims000CmDao.insertCmFile(param) == 1) {
					try {
						if (!saveToFile(param, multipartFile)) {
							sicims000CmDao.deleteCmFile(param.getFileId());
							param.setFileId(null);
						}
					} catch (IOException e) {
						sicims000CmDao.deleteCmFile(param.getFileId());
						param.setFileId(null);
					}
				}
				return param.getFileId();

			}
		}
		return returnValue;
	}

	/**
	 * 업로드 파일정보를 저장한다.
	 * @param param CmFileVo
	 * @return int
	 */
	@Override
	public int insertCmFile(CmFileVo param ) {
		int returnValue	= -1;
		String fileNm 			= param.getFileOrgnlNm();
		String allowFileExt		= (propertiesService.getString("uploadExt") != null )? propertiesService.getString("uploadExt") : "hwp,pdf";	//파일업로드 허용 확장자
		String[] fileExts   	= allowFileExt.split(",");

		for(String fileExtNm : fileExts) {
			//허용 확장자인 경우만 업로드 하도록 확장자 체크
			if(fileNm == null || !fileNm.toLowerCase().endsWith(fileExtNm)) {
				continue;
			}
			else {
				returnValue = sicims000CmDao.insertCmFile(param);
			}
		}
		return returnValue;
	}



	/**
	 * 업로드 파일 및 파일정보를 저장한다.
	 * @param param CmFileVo
	 * @param multipartFile MultipartFile
	 * @return BigDecimal
	 */
	@Override
	public CmFileVo insertCmFile(CmFileVo param, MultipartFile multipartFile) {
		CmFileVo cmFileVo 		= new CmFileVo();
		String fileNm 			= multipartFile.getOriginalFilename();
		String allowFileExt		= (propertiesService.getString("uploadExt") != null )? propertiesService.getString("uploadExt") : "hwp,pdf";	//파일업로드 허용 확장자
		String[] fileExts   	= allowFileExt.split(",");

		for(String fileExtNm : fileExts) {
			if(fileNm == null || !fileNm.toLowerCase().endsWith(fileExtNm)) {
				continue;
			}
			else {
				param.setFileOrgnlNm(multipartFile.getOriginalFilename());			/* 파일원본명 */
				param.setFileExtn(getFileExtension(param.getFileOrgnlNm()));		/* 파일확장자 */
				param.setFileSz(new BigDecimal(multipartFile.getSize()));			/* 파일사이즈 */

				if (sicims000CmDao.insertCmFile(param) == 1) {
					cmFileVo = sicims000CmDao.getCmFileInfo(param.getFileId());
					try {
						if (!saveToFile(cmFileVo, multipartFile)) {
							sicims000CmDao.deleteCmFile(cmFileVo.getFileId());
							param.setFileId(null);
						}
					} catch (IOException e) {
						sicims000CmDao.deleteCmFile(cmFileVo.getFileId());
						param.setFileId(null);
					}
				}

				return cmFileVo;
			}
		}
		return cmFileVo;
	}

	/**
	 * 업로드 파일 및 파일정보를 저장한다.
	 * @param param CmFileVo
	 * @param multipartFile MultipartFile
	 * @return BigDecimal
	 */
	@Override
	public CmFileVo insertCmFileNm(CmFileVo param, MultipartFile multipartFile) {
		CmFileVo cmFileVo	= new CmFileVo();
		String fileNm 		= multipartFile.getOriginalFilename();
		String allowFileExt	= (propertiesService.getString("uploadExt") != null )? propertiesService.getString("uploadExt") : "hwp,pdf";	//파일업로드 허용 확장자
		String[] fileExts   = allowFileExt.split(",");

		for(String fileExtNm : fileExts) {
			if(fileNm == null || !fileNm.toLowerCase().endsWith(fileExtNm)) {
				continue;
			}
			else {
				param.setFileOrgnlNm(multipartFile.getOriginalFilename());			/* 파일원본명 */
				param.setFileExtn(getFileExtension(param.getFileOrgnlNm()));		/* 파일확장자 */
				param.setFileSz(new BigDecimal(multipartFile.getSize()));			/* 파일사이즈 */

				if (sicims000CmDao.insertCmFile(param) == 1) {
					cmFileVo = sicims000CmDao.getCmFileInfo(param.getFileId());
					try {
						if (!saveToFileNm(cmFileVo, multipartFile)) {
							sicims000CmDao.deleteCmFile(cmFileVo.getFileId());
							param.setFileId(null);
						}
					} catch (IOException e) {
						sicims000CmDao.deleteCmFile(cmFileVo.getFileId());
						param.setFileId(null);
					}
				}
				return cmFileVo;
			}
		}
		return cmFileVo;
	}

	/**
	 * 업로드 파일 및 파일정보를 수정한다.
	 * @param param CmFileVo
	 * @param multipartFile MultipartFile
	 * @return String
	 */
	@Override
	public BigDecimal updateCmFile(CmFileVo param, MultipartFile multipartFile) {
		BigDecimal returnValue	= null;
		String fileNm 			= multipartFile.getOriginalFilename();
		String allowFileExt		= (propertiesService.getString("uploadExt") != null )? propertiesService.getString("uploadExt") : "hwp,pdf";	//파일업로드 허용 확장자
		String[] fileExts   	= allowFileExt.split(",");

		for(String fileExtNm : fileExts) {
			if(fileNm == null || !fileNm.toLowerCase().endsWith(fileExtNm)) {
				continue;
			}
			else {

				param.setFileOrgnlNm(multipartFile.getOriginalFilename());			/* 파일원본명 */
				param.setFileExtn(getFileExtension(param.getFileOrgnlNm()));		/* 파일확장자 */
				param.setFileSz(new BigDecimal(multipartFile.getSize()));			/* 파일사이즈 */

				if (sicims000CmDao.updateCmFile(param) == 1) {
					CmFileVo cmFileVo = sicims000CmDao.getCmFileInfo(param.getFileId());

					try {
						if (deleteFile(param)) {
							LOG.info("파일 삭제 성공");
						}else {
							LOG.info("파일 삭제 실패");
						}

						if (!saveToFile(cmFileVo, multipartFile)) {
							sicims000CmDao.deleteCmFile(cmFileVo.getFileId());
							param.setFileId(null);
						}
					} catch (IOException e) {
						sicims000CmDao.deleteCmFile(cmFileVo.getFileId());
						param.setFileId(null);
					}
				}

				return param.getFileId();
			}
		}
		return returnValue;
	}



	/**
	 * 업로드 파일을 저장한다.
	 * @param param CmFileVo
	 * @param multipartFile MultipartFile
	 * @return String
	 */
	@SuppressWarnings("resource")
	private boolean saveToFile(CmFileVo param, MultipartFile multipartFile) throws IOException {
		String savePath 	= param.getFilePath();
		String fileNm		= savePath + param.getFileNm();

		boolean retFlag = false;
		//첨부파일 업로드 확장자 체크
		String allowFileExt	= (propertiesService.getString("uploadExt") != null )? propertiesService.getString("uploadExt") : "hwp,pdf";	//파일업로드 허용 확장자
		String[] fileExts   = allowFileExt.split(",");
		for(String fileExtNm : fileExts) {
			if(fileNm == null || !fileNm.toLowerCase().endsWith(fileExtNm)) {
				continue;
			} else {
				try {
					java.io.File file = new java.io.File(savePath);
					if (!file.isDirectory()) {
						if (file.mkdirs()) {
							LOG.info("디렉토리 생성 성공");
						} else {
							LOG.info("디렉토리 생성 실패");
						}
					}

					FileOutputStream fos = new FileOutputStream(fileNm);
					BufferedOutputStream bos = new BufferedOutputStream(fos);

					byte[] bytes = multipartFile.getBytes();

					bos.write(bytes);
					bos.flush();
					retFlag = true;
				} catch (FileNotFoundException ex) {
					LOG.info("Error : FileNotFoundException opening the input file: " + fileNm);
				} catch (IOException ex) {
					LOG.info("Error : IOException reading the input file.\n");
					throw new IOException(ex);
				} catch (Exception ex) {
					LOG.info("Error : Exception reading the input file.\n");
					throw new IOException(ex);
				}
				return retFlag;
			}
		}

		return retFlag;
	}

	/**
	 * 업로드 파일을 저장한다.
	 * @param param CmFileVo
	 * @param multipartFile MultipartFile
	 * @return String
	 */
	@SuppressWarnings("unused")
	private boolean saveToFile(CmFileVo param, File uploadFile) throws IOException {
		String savePath 	= param.getFilePath();
		String fileNm		= savePath + param.getFileNm();

		boolean retFlag = false;
		//첨부파일 업로드 확장자 체크
		String allowFileExt	= (propertiesService.getString("uploadExt") != null )? propertiesService.getString("uploadExt") : "hwp,pdf";	//파일업로드 허용 확장자
		String[] fileExts   = allowFileExt.split(",");
		for(String fileExtNm : fileExts) {
			if(fileNm == null || !fileNm.toLowerCase().endsWith(fileExtNm)) {
				continue;
			}
			else {
				if (uploadFile.exists()){
					try {
						synchronized(SYNC3) {

							java.io.File file = new java.io.File(savePath);
							if (!file.isDirectory()) {
								if (file.mkdirs()) {
									LOG.info("디렉토리 생성 성공");
								} else {
									LOG.info("디렉토리 생성 실패");
								}
							}
							// 파일이 존재하는 경우 리네임이 되지 않으므로 파일을 제거
							UploadUtil.deleteFile(fileNm);

							File storageFile = new File(fileNm);

							boolean isMoved = uploadFile.renameTo(storageFile);
							// renameTo 로 리네임처리를 하지 못 한 경우 직접 파일을 이동시키도록
							if (!isMoved) {
								com.google.common.io.Files.move(uploadFile, storageFile);
							}
							retFlag = true;
						}
					} catch (FileNotFoundException ex) {
						LOG.info("Error : FileNotFoundException opening the input file: " + fileNm);
					} catch (IOException ex) {
						LOG.info("Error : IOException reading the input file.\n");
						throw new IOException(ex);
					} catch (Exception ex) {
						LOG.info("Error : Exception reading the input file.\n");
						throw new IOException(ex);
					}
					return retFlag;
				}
			}
		}

		return retFlag;
	}



	/**
	 * 업로드 파일을 파일명으로 저장한다.
	 * @param param CmFileVo
	 * @param multipartFile MultipartFile
	 * @return String
	 */
	private boolean saveToFileNm(CmFileVo param, MultipartFile multipartFile) throws IOException {
		String savePath 	= param.getFilePath();
		String saveFileNm	= savePath + param.getFileNm() + "." + param.getFileExtn();

		boolean retFlag = false;

		try (
			FileOutputStream fos = new FileOutputStream(saveFileNm);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
		) {
			java.io.File file = new java.io.File(savePath);
			if (!file.isDirectory()) {
				if (file.mkdirs()) {
					LOG.info("디렉토리 생성 성공");
				} else {
					LOG.info("디렉토리 생성 실패");
				}
			}

			byte[] bytes = multipartFile.getBytes();

			bos.write(bytes);
			bos.flush();
			retFlag = true;
		} catch (FileNotFoundException ex) {
			LOG.info("Error : FileNotFoundException opening the input file: " + saveFileNm);
		} catch (IOException ex) {
			LOG.info("Error : IOException reading the input file.\n");
			throw new IOException(ex);
		} catch (Exception ex) {
			LOG.info("Error : Exception reading the input file.\n");
			throw new IOException(ex);
		}

		return retFlag;
	}

	/**
	 * 파일 및 정보를 삭제한다.
	 * @param fileSeq String
	 * @return HashMap<String, Object>
	 */
	@Override
	public HashMap<String, Object> deleteCmFile(BigDecimal fileSn) {

		HashMap<String, Object> result = new HashMap<String, Object>();
		synchronized(SYNC1) {
			CmFileVo cmFileVo = sicims000CmDao.getCmFileInfo(fileSn);

			String savePath 	= cmFileVo.getFilePath();
			String fileNm	 	= savePath + cmFileVo.getFileNm();

			File file = new java.io.File(fileNm);
			if (file.exists()) {
				if (file.delete()){
					sicims000CmDao.deleteCmFile(fileSn);
					result.put("status", "success");
				}else{
					result.put("status", "fail");
				}
			} else {
				result.put("status", "success");
			}
		}
		return result;
	}


	/**
	 * 파일이름으로부터 확장자를 반환하는 메서드
	 * 파일이름에 확장자 구분을 위한 . 문자가 없거나. 가장 끝에 있는 경우는 빈문getFileExtension자열 ""을 리턴
	 */
	@Override
	public String getFileExtension(String fileNm) {
		int dotPosition = fileNm.lastIndexOf('.');

		if (dotPosition != -1 && dotPosition < fileNm.length() - 1) {
			return fileNm.substring(dotPosition + 1).toLowerCase();
		} else {
			return "";
		}
	}

	/**
	 * 파일이름으로부터 확장자를 제외한 파일이름을 반환하는 메서드
	 * 파일이름에 확장자 구분을 위한 . 문자가 없거나. 가장 끝에 있는 경우는 빈문getFileExtension자열 ""을 리턴
	 */
	@Override
	public String getFileOrgnNm(String fileNm) {
		int dotPosition = fileNm.lastIndexOf('.');

		if (dotPosition != -1 && dotPosition < fileNm.length() - 1) {
			return fileNm.substring(0, dotPosition).toLowerCase();
		} else {
			return "";
		}
	}


	/**
	 * 파일을 삭제한다.
	 * @param fileSeq String
	 * @return HashMap<String, Object>
	 */
	private boolean deleteFile(CmFileVo param) {

		boolean retFlag = false;
		synchronized(SYNC2) {
			String savePath 	= param.getFilePath();
			String fileNm	 	= savePath + param.getFileNm();

			File file = new java.io.File(fileNm);
			if (file.exists()) {
				if (file.delete()){
					retFlag = true;
				}
			}
		}
		return retFlag;
	}



	/**
	 *  파일 스트림(stream)을 가져온다.
	 * @param HashMap<String,Object> param
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return void
	 * @throws Exception
	 */
	@Override
	public void getFileStream(HashMap<String,Object> param, HttpServletRequest request, HttpServletResponse response)  throws IOException {
		String uploadPath 	= propertiesService.getString("uploadPath");
		String savePath 	= "";
		String fileNm	 	= "";
		String saveFileNm 	= "";

		CmFileVo cmFileVo	 	= sicims000CmDao.getCmFileInfo(new BigDecimal((String) param.get("fileSn")));
		if (cmFileVo.getFileId().compareTo(BigDecimal.ZERO) == 1) {
			savePath 	= cmFileVo.getFilePath();
			fileNm	 	= savePath + cmFileVo.getFileNm();
			saveFileNm 	= cmFileVo.getFileNm();
		} else {
			savePath 	= uploadPath;
			fileNm		= savePath + "no_img.png";
			saveFileNm	= "no_img";
		}

		String strClient 	= request.getHeader("User-Agent").toLowerCase();

		if (StringUtils.contains(strClient, "trident")) {		 // for IE
            response.setContentType("doesn/matter");
            saveFileNm = URLEncoder.encode(saveFileNm, "UTF-8").replaceAll("\\+", "$20");
            response.setHeader("Content-Disposition", "attachment;filename=" + saveFileNm+ ";");
        } else {
        	saveFileNm = new String(saveFileNm.getBytes("UTF-8"), "ISO-8859-1");
			response.setContentType("application/octet-stream; charset=euc-kr");
			response.setHeader("Content-Disposition", "attachment;filename=\"" + saveFileNm+ "\";");
        }
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.setHeader("Connection", "close");


        try {
        	File file = new File (fileNm);
     		if(file.exists()) {
    			byte[] bytestream = new byte[(int)file.length()];

    			int read = 0;
    			try (
    					FileInputStream 	 fis 	= new FileInputStream(file);
    					BufferedInputStream  fin  	= new BufferedInputStream(fis);
    	    			BufferedOutputStream fouts 	= new BufferedOutputStream(response.getOutputStream(), 4096);
   				) {
    				while (true) {
    					read = fin.read(bytestream);
    					if (read == -1) break;
    					fouts.write(bytestream, 0, read);
    				}
    				fouts.flush();
    			} catch (IOException e) {
    				LOG.error("첨부파일 다운로드 중 Connection Exception occurred이 발생했습니다.");
    			}
    		} else {
    			notFound(request, response);
    		}
        } catch (IOException ex) {
        	notFound(request, response);
		}
	}

	/**
	 *  파일을 업로드한다
	 * @param HashMap<String, Object> param
	 * @param HttpServletRequest request
	 * @param List<MultipartFile> fileList
	 * @return HashMap<String, Object>
	 * @throws Exception
	 */
	@Override
	public HashMap<String, Object> insertFile(HashMap<String, Object> param, HttpServletRequest request, List<MultipartFile> fileList) throws IOException {
		HashMap<String, Object> result = new HashMap<String, Object>();
		List<CmFileVo> cmFileList = new ArrayList<CmFileVo>();

		try {
			// 1. 첨부파일 체크
			if(fileList.isEmpty()) {
				result.put("status",	"fail");
			    result.put("message",	"첨부파일이 없습니다.");
			    return result;
			}

			for(MultipartFile mf : fileList) {

				// 2. 파일확장자 체크
				String fileExt = sicims000CmService.getFileExtension(mf.getOriginalFilename());

				if (!fileExt.equals("gif") && !fileExt.equals("jpg") && !fileExt.equals("jpeg") && !fileExt.equals("png") &&
				    !fileExt.equals("hwp") && !fileExt.equals("pdf") && !fileExt.equals("doc") && !fileExt.equals("docx") &&
				    !fileExt.equals("ppt") && !fileExt.equals("pptx") && !fileExt.equals("xls") && !fileExt.equals("xlsx") &&
				    !fileExt.equals("zip")) {
				    result.put("status",	"fail");
				    result.put("message",	"업로드 제한 파일(확장자)");
				    return result;
				}

				// 3. 파일업로드 및 파일정보 등록 체크
				CmFileVo cmFileVo = new CmFileVo();

				cmFileVo.setFileKd((String)param.get("fileKd"));

				String filePath = propertiesService.getString("uploadPath") +  "/" + cmFileVo.getFileKd() + "/";
				cmFileVo.setFilePath(filePath);

				cmFileVo.setUpdusrId("ADMIN");

				CmFileVo cmFileInfo = sicims000CmService.insertCmFile(cmFileVo, mf);

				if (cmFileInfo == null || cmFileInfo.getFileId() == null || cmFileInfo.getFileId().compareTo(BigDecimal.ZERO) == 0) {
				    result.put("status",	"fail");
				    result.put("message",	"파일업로드 및 파일정보 등록 실패");
				    return result;
				} else {
					cmFileList.add(cmFileInfo);
					result.put("status",	"success");
					result.put("message",	"데이터 등록 성공");
				}
			}

			// 4. 파일 순번 목록 리턴
			result.put("cmFileList", cmFileList);
			return result;

		}  catch ( DataAccessException e) {
			result.put("status", 	"fail");
			result.put("message",	"데이터 등록 오류");
			return result;
		}
	}

	/**
	 * 파일을 다운로드 한다.
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return void
	 * @throws Exception
	 */
	@Override
	public void downloadFile(HashMap<String,Object> param, HttpServletRequest request, HttpServletResponse response ) throws IOException  {
		String strClient 	= request.getHeader("User-Agent").toLowerCase();

		CmFileVo fileVo	 	= sicims000CmDao.getCmFileInfo(new BigDecimal((String) param.get("fileId")));

		if(fileVo == null ) {
			//해당파일의 정보가 없는경우
			notFound(request, response);
		}	else {
			String fileNm	 	= fileVo.getFilePath() + fileVo.getFileNm();
			String saveFileNm 	= fileVo.getFileOrgnlNm();

			if (StringUtils.contains(strClient, "trident")) {		 // for IE
	            response.setContentType("doesn/matter");
	            saveFileNm = new String(saveFileNm.getBytes("EUC-KR"), "ISO-8859-1");		// Tomcat
	            //saveFileNm = URLEncoder.encode(saveFileNm, "UTF-8").replaceAll("\\+", "%20");
	            response.setHeader("Content-Disposition", "attachment;filename=" + saveFileNm+ ";");
	        } else {
	        	saveFileNm = new String(saveFileNm.getBytes("UTF-8"), "ISO-8859-1");
				response.setContentType("application/octet-stream; charset=euc-kr");
				response.setHeader("Content-Disposition", "attachment;filename=\"" + saveFileNm+ "\";");
	        }

			response.setHeader("Content-Transfer-Encoding", "binary");
			response.setHeader("Connection", "close");

	        try {
	        	File file = new File (fileNm);
	     		if(file.exists()) {
	    			byte[] bytestream = new byte[(int)file.length()];

	    			int read = 0;
	    			try (
    					FileInputStream 	 fis 	= new FileInputStream(file);
    					BufferedInputStream  fin  	= new BufferedInputStream(fis);
    	    			BufferedOutputStream fouts 	= new BufferedOutputStream(response.getOutputStream(), 4096);
   					) {
	    				while (true) {
	    					read = fin.read(bytestream);
	    					if (read == -1) break;
	    					fouts.write(bytestream, 0, read);
	    				}
	    				fouts.flush();
	    			} catch (IOException e) {
	    				LOG.error("첨부파일 다운로드 중 Connection Exception occurred이 발생했습니다.");
	    			}
	    		} else {
	    			notFound(request, response);
	    		}
	        } catch (IOException ex) {
	        	ex.printStackTrace();
	        	notFound(request, response);
			}
		}
	}

	/**
	 * 파일을 다운로드 한다.
	 * @param HashMap<String,Object> param
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return void
	 */
	@Override
	public void downloadFileNm(HashMap<String,Object> param, HttpServletRequest request, HttpServletResponse response)  throws IOException {
		String filePath	 	= StringUtils.defaultString((String) param.get("filePath"));
		filePath 			= filePath.replaceAll("\r","").replaceAll("\n","").replaceAll("\\../","").replaceAll("\\./","");
		String fileName	 	= StringUtils.defaultString((String) param.get("fileName"));
		fileName 			= fileName.replaceAll("\r","").replaceAll("\n","").replaceAll("\\../","").replaceAll("\\./","");
		String fileExt	 	= StringUtils.defaultString((String) param.get("fileExt"));
		fileExt 			= fileExt.replaceAll("\r","").replaceAll("\n","").replaceAll("\\../","").replaceAll("\\./","");

		String saveFileNm	= fileName + "." + fileExt;
		String downloadPath = propertiesService.getString("uploadPath");

		String strClient 	= request.getHeader("User-Agent").toLowerCase();
		String fileFullPath = downloadPath + filePath + "/" + fileName + "." + fileExt;

		if (StringUtils.contains(strClient, "trident")) {							// for IE
			saveFileNm = new String(saveFileNm.getBytes("EUC-KR"), "ISO-8859-1");	// Tomcat
			//saveAs = URLEncoder.encode(saveAs, "UTF-8");							// JEUS
            response.setContentType("doesn/matter");
        } else {
        	saveFileNm = new String(saveFileNm.getBytes("UTF-8"), "ISO-8859-1");	// for FF, chrome, safari, opera
			response.setContentType("application/octet-stream; charset=euc-kr");
        }

    	response.setHeader("Content-Disposition", "attachment;filename=" + saveFileNm + ";");
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.setHeader("Connection", "close");

		try {
        	File file = new File (fileFullPath);
     		if(file.exists()) {
    			byte[] bytestream = new byte[(int)file.length()];

    			int read = 0;
    			try (
					FileInputStream fis = new FileInputStream(file);
					BufferedInputStream  fin  = new BufferedInputStream(fis);
	    			BufferedOutputStream fouts = new BufferedOutputStream(response.getOutputStream(), 4096);
    			) {
    				while (true) {
    					read = fin.read(bytestream);
    					if (read == -1) break;
    					fouts.write(bytestream, 0, read);
    				}
    				fouts.flush();
    				fouts.close();
    				fin.close();
    				fis.close();
    			} catch (IOException e) {
    				LOG.error("파일 다운로드 중 Connection Exception occurred이 발생했습니다.");
    			}
    		} else {
    			notFound(request, response);
    		}
        } catch (IOException ex) {
        	notFound(request, response);
		}
 	}


	/**
	 * 파일을 다운로드 한다.
	 * @param HashMap<String,Object> param
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return void
	 */
	@Override
	public void downloadFileNm(String fileName, HttpServletRequest request, HttpServletResponse response)  throws IOException {
		String saveFileNm	= fileName.substring(fileName.lastIndexOf("/") + 1);
		String strClient 	= request.getHeader("User-Agent").toLowerCase();

		if (StringUtils.contains(strClient, "trident")) {							// for IE
			saveFileNm = new String(saveFileNm.getBytes("EUC-KR"), "ISO-8859-1");	// Tomcat
			//saveAs = URLEncoder.encode(saveAs, "UTF-8");							// JEUS
            response.setContentType("doesn/matter");
        } else {
        	saveFileNm = new String(saveFileNm.getBytes("UTF-8"), "ISO-8859-1");	// for FF, chrome, safari, opera
			response.setContentType("application/octet-stream; charset=euc-kr");
        }

    	response.setHeader("Content-Disposition", "attachment;filename=" + saveFileNm + ";");
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.setHeader("Connection", "close");

		try {
        	File file = new File (fileName);
     		if(file.exists()) {
    			byte[] bytestream = new byte[(int)file.length()];

    			int read = 0;
    			try (
					FileInputStream fis = new FileInputStream(file);
					BufferedInputStream  fin  = new BufferedInputStream(fis);
	    			BufferedOutputStream fouts = new BufferedOutputStream(response.getOutputStream(), 4096);
    			) {
    				while (true) {
    					read = fin.read(bytestream);
    					if (read == -1) break;
    					fouts.write(bytestream, 0, read);
    				}
    				fouts.flush();
    				fouts.close();
    				fin.close();
    				fis.close();
    			} catch (IOException e) {
    				LOG.error("파일 다운로드 중 Connection Exception occurred이 발생했습니다.");
    			}
    		} else {
    			notFound(request, response);
    		}
        } catch (IOException ex) {
        	notFound(request, response);
		}
 	}

	// File Not Found
	private void notFound(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String uploadPath 	= propertiesService.getString("uploadPath");
		String savePath 	= uploadPath;
		String fileNm	 	= savePath + "no_img.png";
		String saveFileNm 	= "no_img";

		String strClient 	= request.getHeader("User-Agent").toLowerCase();

		if (StringUtils.contains(strClient, "trident")) {		 // for IE
            response.setContentType("doesn/matter");
        } else {
			response.setContentType("application/octet-stream; charset=euc-kr");
        }
    	response.setHeader("Content-Disposition", "attachment;filename=" + saveFileNm+ ";");
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.setHeader("Connection", "close");

		File file = new File (fileNm);
		byte[] bytestream = new byte[(int)file.length()];

		int read = 0;
		try (
				FileInputStream 	 fis 	= new FileInputStream(file);
				BufferedInputStream  fin  	= new BufferedInputStream(fis);
				BufferedOutputStream fouts 	= new BufferedOutputStream(response.getOutputStream(), 4096);
		) {
			while (true) {
				read = fin.read(bytestream);
				if (read == -1) break;
				fouts.write(bytestream, 0, read);
			}
			fouts.flush();
		} catch (IOException e) {
			LOG.error("첨부파일 다운로드 중 Connection Exception occurred이 발생했습니다.");
		}
	}

	/**
	 * 이미지파일  파일을 등록한다.
	 * @param param HashMap<String, Object>
	 * @param multipartFile MultipartFile
	 * @param response HttpServletResponse
	 * @return HashMap<String, Object>
	 * @throws IOException
	 */
	public void insertImageFileNm(HashMap<String, Object> param, MultipartFile multipartFile, HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter printWriter = null;
		JsonObject json = new JsonObject();

		try {
			// 1. 첨부파일 체크
			if (multipartFile == null) {
				json.addProperty("uploaded", 0);
				json.addProperty("fileName", "");
				json.addProperty("url", "");

				printWriter = response.getWriter();
				printWriter.println(json);
				printWriter.close();
			}

			String fileExt =null;
			// 2. 파일확장자 체크
			if(multipartFile != null ){
				fileExt = sicims000CmService.getFileExtension(multipartFile.getOriginalFilename());
			}
			if ( fileExt != null &&  !fileExt.equals("gif") && !fileExt.equals("jpg") && !fileExt.equals("jpeg") && !fileExt.equals("png") &&
				!fileExt.equals("hwp") && !fileExt.equals("pdf") && !fileExt.equals("doc") && !fileExt.equals("docx") &&
				!fileExt.equals("ppt") && !fileExt.equals("pptx") && !fileExt.equals("xls") && !fileExt.equals("xlsx") &&
				!fileExt.equals("zip")) {

				json.addProperty("uploaded", 0);
				json.addProperty("fileName", "");
				json.addProperty("url", "");

				printWriter = response.getWriter();
				printWriter.println(json);
				printWriter.close();
			}


			// 3. 파일업로드 및 파일정보 등록 체크
			CmFileVo cmFileVo = new CmFileVo();

			cmFileVo.setFileKd((String) param.get("fileKd"));		// 디렉토리 구분
			cmFileVo.setFilePath("/" + (String) param.get("fileKd") + "/");
			cmFileVo.setUpdusrId("ADMIN");

			CmFileVo resultCmFileInfo = sicims000CmService.insertCmFile(cmFileVo, multipartFile);

			if (resultCmFileInfo.getFileId() == null) {
				json.addProperty("uploaded", 0);
				json.addProperty("fileName", "");
				json.addProperty("url", "");

				printWriter = response.getWriter();
				printWriter.println(json);
				printWriter.close();
			}

			// 4. 데이터 등록
			response.setContentType("text/html");

			String fileUrl 		= request.getContextPath() + "/sicims000Cm/getFileStream.do?" + "fileId=" + resultCmFileInfo.getFileId() + "&fileKd=" + resultCmFileInfo.getFileKd();

			json.addProperty("uploaded", 1);
			json.addProperty("fileName", resultCmFileInfo.getFileNm() + "." + resultCmFileInfo.getFileExtn());
			json.addProperty("url", fileUrl);

			printWriter = response.getWriter();
			printWriter.println(json);
			printWriter.close();
		} catch ( DataAccessException e) {
			json.addProperty("uploaded", 0);
			json.addProperty("fileName", "");
			json.addProperty("url", "");

			printWriter = response.getWriter();
			printWriter.println(json);
			printWriter.close();
		}
	}



}
