package com.g3way.sicims.services.sicims300Cc;

import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.g3way.sicims.exception.SicimsException;
import com.g3way.sicims.services.sicims000Cm.Sicims000CmService;
import com.g3way.sicims.services.sicims000Cm.vo.CmFileVo;
import com.g3way.sicims.services.sicims300Cc.vo.CcCsexVo;
import com.g3way.sicims.services.sicims300Cc.vo.CcCsmaVo;
import com.g3way.sicims.services.sicims300Cc.vo.CcCsrsVo;
import com.g3way.sicims.services.sicims300Cc.vo.CcIsrdVo;
import com.g3way.sicims.services.sicims300Cc.vo.CcIsrrVo;
import com.g3way.sicims.services.sicims300Cc.vo.CcVcadVo;
import com.g3way.sicims.util.common.MailUtil;
import com.g3way.sicims.util.common.SessionUtil;
import com.g3way.sicims.util.common.StringUtil;
import com.g3way.sicims.util.encrypt.EgovFileScrty;

import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.fdl.property.EgovPropertyService;



@Service
public class Sicims300CcServiceImpl implements Sicims300CcService {

	private static final Logger LOG = LoggerFactory.getLogger(Sicims300CcServiceImpl.class);

	@Autowired Sicims000CmService 	sicims000CmService;
	@Autowired Sicims300CcDao 	sicims300CcDao;

	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	//  이메일
	@Resource(name="javaMailSender")
	private JavaMailSender javaMailSender;


	/********************************/
	/*	1. 민원관리-건설업등록기준사전조사 자료관리	*/
	/*	1.1 건설업등록기준사전조사 정보			*/
	/********************************/
	/**
	 * 페이지 단위로 건설업등록기준사전조사 목록을 조회한다.
	 * @param param HashMap<String, Object>
	 * @return List<CcCsmaVo>
	 */
	@Override
	public List<CcCsmaVo> selectCcCsmaList(HashMap<String, Object> param){
		try {
			return sicims300CcDao.selectCcCsmaList(param);
		} catch (DataAccessException e) {
			return new ArrayList<CcCsmaVo>();
		}
	}


	/**
	 * 건설업등록기준사전조사 정보를  조회한다.
	 * @param param HashMap<String, Object>
	 * @return CcCsmaVo
	 */
	@Override
	public CcCsmaVo getCcCsmaInfo(HashMap<String, Object> param){
		try {
			CcCsmaVo ccCsmaVo = sicims300CcDao.getCcCsmaInfo(param);
			if (ccCsmaVo == null) ccCsmaVo = new CcCsmaVo();
			return ccCsmaVo;
		} catch (DataAccessException e) {
			return new CcCsmaVo();
		}
	}


	/**
	 * 건설업등록기준사전조사 정보를  등록한다.
	 * @param ccCsmaVo CcCsmaVo
	 * @return int
	 */
	@Override
	@Transactional(rollbackFor=DataAccessException.class)
	public int insertCcCsma(CcCsmaVo ccCsmaVo){
		// 공고일자/개찰일자/조사업체법인등록번호/제출시작일자/제출종료일자 '-'제거
		ccCsmaVo.setPbancYmd(StringUtil.removeDelimChar(ccCsmaVo.getPbancYmd(), "-", ""));				// 공고일자 '-'제거
		ccCsmaVo.setOpnbdYmd(StringUtil.removeDelimChar(ccCsmaVo.getOpnbdYmd(), "-", ""));				// 개찰일자 '-'제거
		ccCsmaVo.setExmnBzentyCrno(StringUtil.removeDelimChar(ccCsmaVo.getExmnBzentyCrno(), "-", ""));	// 조사업체법인등록번호 '-'제거
		ccCsmaVo.setSbmsnBgngYmd(StringUtil.removeDelimChar(ccCsmaVo.getSbmsnBgngYmd(), "-", ""));		// 제출시작일자 '-'제거
		ccCsmaVo.setSbmsnEndYmd(StringUtil.removeDelimChar(ccCsmaVo.getSbmsnEndYmd(), "-", ""));		// 제출종료일자 '-'제거

		ccCsmaVo.setUpdusrId(SessionUtil.getLoginUserId());
		ccCsmaVo.setUpdusrIp(SessionUtil.getLoginUserIp());

		try {
			//패스워드 암호화
			String resetPswd 	= EgovProperties.getProperty("Global.password.reset");
			String enUserPswd 	= EgovFileScrty.encryptPassword(resetPswd, ccCsmaVo.getExmnBzentyPicEmlAddr());
			ccCsmaVo.setExmnBzentyPicPswd(enUserPswd);

			int recordCnt = sicims300CcDao.insertCcCsma(ccCsmaVo);

			// 해당 이메일의 비밀번호 모두 초기
			sicims300CcDao.updateCcCsmaPswd(ccCsmaVo);

			try {
				// Java Mail
				// HashMap<String, Object> mailResult = MailUtil.javaMailSender(javaMailSender, ccCsmaVo.getExmnBzentyPicEmlAddr());

				// 서울시 통합메일(릴레이)
				HashMap<String, Object> mailResult = MailUtil.relayMailSender( ccCsmaVo.getExmnBzentyPicEmlAddr());

				if (mailResult.get("mailStatus").equals("fail")) {
					LOG.error((String)mailResult.get("mailMessage"));
					recordCnt = -101;
				}
			} catch (MailException e) {
				LOG.error(e.getMessage());
				recordCnt = -101;
			}

			return recordCnt;
		} catch (DataAccessException e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw new SicimsException("데이터 저장 중 오류가 발생하였습니다.");
		} catch (NoSuchAlgorithmException e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw new SicimsException("비밀번호 생성  중 오류가 발생하였습니다.");
		} catch (MailException e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw new SicimsException("메일 발송 중 오류가 발생하였습니다.");
		}
	}


	/**
	 * 건설업등록기준사전조사 정보를  수정한다.
	 * @param ccCsmaVo CcCsmaVo
	 * @return int
	 */
	@Override
	@Transactional(rollbackFor=DataAccessException.class)
	public int updateCcCsma(CcCsmaVo ccCsmaVo){
		// 공고일자/개찰일자/조사업체법인등록번호/제출시작일자/제출종료일자/제출일자 '-'제거
		ccCsmaVo.setPbancYmd(StringUtil.removeDelimChar(ccCsmaVo.getPbancYmd(), "-", ""));				// 공고일자 '-'제거
		ccCsmaVo.setOpnbdYmd(StringUtil.removeDelimChar(ccCsmaVo.getOpnbdYmd(), "-", ""));				// 개찰일자 '-'제거
		ccCsmaVo.setExmnBzentyCrno(StringUtil.removeDelimChar(ccCsmaVo.getExmnBzentyCrno(), "-", ""));	// 조사업체법인등록번호 '-'제거
		ccCsmaVo.setSbmsnBgngYmd(StringUtil.removeDelimChar(ccCsmaVo.getSbmsnBgngYmd(), "-", ""));		// 제출시작일자 '-'제거
		ccCsmaVo.setSbmsnEndYmd(StringUtil.removeDelimChar(ccCsmaVo.getSbmsnEndYmd(), "-", ""));		// 제출종료일자 '-'제거
		ccCsmaVo.setSbmsnYmd(StringUtil.removeDelimChar(ccCsmaVo.getSbmsnYmd(), "-", ""));				// 제출일자 '-'제거

		ccCsmaVo.setUpdusrId(SessionUtil.getLoginUserId());
		ccCsmaVo.setUpdusrIp(SessionUtil.getLoginUserIp());

		try {
			//패스워드 암호화
			String resetPswd = EgovProperties.getProperty("Global.password.reset");
			String enUserPswd = EgovFileScrty.encryptPassword(resetPswd, ccCsmaVo.getExmnBzentyPicEmlAddr());
			ccCsmaVo.setExmnBzentyPicPswd(enUserPswd);

			int recordCnt = sicims300CcDao.updateCcCsma(ccCsmaVo);

			// 업체담당자이 이메일 주소가 변경되었으면 해당 이메일의 비밀번호를 모두 초기화 하고
			// 그 내용을 이메일로 전송한다.
			if (ccCsmaVo.getPicChgYn().equals("Y")) {
				// 해당 이메일의 비밀번호 모두 초기
				sicims300CcDao.updateCcCsmaPswd(ccCsmaVo);

				try {
					// Java Mail
					// HashMap<String, Object> mailResult = MailUtil.javaMailSender(javaMailSender, ccCsmaVo.getExmnBzentyPicEmlAddr());

					// 서울시 통합메일(릴레이)
					HashMap<String, Object> mailResult = MailUtil.relayMailSender( ccCsmaVo.getExmnBzentyPicEmlAddr());

					if (mailResult.get("mailStatus").equals("fail")) {
						LOG.error((String)mailResult.get("mailMessage"));
						recordCnt = -101;
					}
				} catch (MailException e) {
					LOG.error(e.getMessage());
					recordCnt = -101;
				} catch (Exception e) {
					e.printStackTrace();
					recordCnt = -101;
				}
			}

    		return recordCnt;
		} catch (DataAccessException e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw new SicimsException("데이터 저장 중 오류가 발생하였습니다.");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw new SicimsException("비밀번호 생성  중 오류가 발생하였습니다.");
		}
	}


	/**
	 * 외부업체 사용자의 비밀번호를 초기화 한다.
	 * @param ccCsmaVo CcCsmaVo
	 * @return int
	 */
	@Override
	@Transactional(rollbackFor=DataAccessException.class)
	public int resetPswd(CcCsmaVo ccCsmaVo){
		ccCsmaVo.setUpdusrId(SessionUtil.getLoginUserId());
		ccCsmaVo.setUpdusrIp(SessionUtil.getLoginUserIp());

		int	recordCnt = 0;
		try {
			//패스워드 암호화
			String resetPswd = EgovProperties.getProperty("Global.password.reset");
			String enUserPswd = EgovFileScrty.encryptPassword(resetPswd, ccCsmaVo.getExmnBzentyPicEmlAddr());
			ccCsmaVo.setExmnBzentyPicPswd(enUserPswd);

			recordCnt = sicims300CcDao.updateCcCsmaPswd(ccCsmaVo);

			// Java Mail
			// HashMap<String, Object> mailResult = MailUtil.javaMailSender(javaMailSender, ccCsmaVo.getExmnBzentyPicEmlAddr());
			HashMap<String, Object> mailResult = MailUtil.relayMailSender( ccCsmaVo.getExmnBzentyPicEmlAddr());
			if (mailResult.get("mailStatus").equals("fail")) {
				LOG.error((String)mailResult.get("mailMessage"));
				recordCnt = -101;
			}
    		return recordCnt;
		} catch (DataAccessException e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw new SicimsException("데이터 저장 중 오류가 발생하였습니다.");
		} catch (NoSuchAlgorithmException e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw new SicimsException("비밀번호 생성  중 오류가 발생하였습니다.");
		}
	}


	/**
	 * 건설업등록기준사전조사 정보를 삭제한다.
	 * @param param HashMap<String, Object>
	 * @return int
	 */
	@Override
	@Transactional(rollbackFor=DataAccessException.class)
	public int deleteCcCsma(HashMap<String, Object> param){
		try {
			return sicims300CcDao.deleteCcCsma(param);
		} catch (DataAccessException e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw new SicimsException("건설업등록기준사전조사 삭제 중 오류가 발생하였습니다.");
		}
	}


	/********************************/
	/*	1.2 민원관리-건설업등록기준사전조사결과 정보	*/
	/********************************/
	/**
	 * 건설업등록기준사전조사결과 정보를  조회한다.
	 * @param param HashMap<String, Object>
	 * @return CcCsrsVo
	 */
	@Override
	public CcCsrsVo getCcCsrsInfo(HashMap<String, Object> param) {
		try {
			CcCsrsVo ccCsrsVo = sicims300CcDao.getCcCsrsInfo(param);
			if (ccCsrsVo == null) ccCsrsVo = new CcCsrsVo();
			return ccCsrsVo;
		} catch (DataAccessException e) {
			return new CcCsrsVo();
		}
	}


	/**
	 * 건설업등록기준사전조사결과 정보를  저장한다.
	 * @param ccCsrsVo CcCsrsVo
	 * @return int
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public int saveOrUpdateCcCsrs(CcCsrsVo ccCsrsVo, MultipartHttpServletRequest multipartRequest) {
		// 방문일자/행정처분의뢰일자/행정처분시작일자/행정처분종료일자/공문알림일자/결과보고일자/재무과통보일자 '-'제거
		ccCsrsVo.setVstYmd(StringUtil.removeDelimChar(ccCsrsVo.getVstYmd(), "-", ""));					// 방문일자 '-'제거
		ccCsrsVo.setAdmdspRqstYmd(StringUtil.removeDelimChar(ccCsrsVo.getAdmdspRqstYmd(), "-", ""));	// 행정처분의뢰일자 '-'제거
		ccCsrsVo.setAdmdspBgngYmd(StringUtil.removeDelimChar(ccCsrsVo.getAdmdspBgngYmd(), "-", ""));	// 행정처분시작일자 '-'제거
		ccCsrsVo.setAdmdspEndYmd(StringUtil.removeDelimChar(ccCsrsVo.getAdmdspEndYmd(), "-", ""));		// 행정처분종료일자 '-'제거
		ccCsrsVo.setOntcYmd(StringUtil.removeDelimChar(ccCsrsVo.getOntcYmd(), "-", ""));				// 공문알림일자 '-'제거
		ccCsrsVo.setRsltRptYmd(StringUtil.removeDelimChar(ccCsrsVo.getRsltRptYmd(), "-", ""));			// 결과보고일자 '-'제거
		ccCsrsVo.setAcseNtfctnYmd(StringUtil.removeDelimChar(ccCsrsVo.getAcseNtfctnYmd(), "-", ""));	// 재무과통보일자 '-'제거

		ccCsrsVo.setUpdusrId(SessionUtil.getLoginUserId());
		ccCsrsVo.setUpdusrIp(SessionUtil.getLoginUserIp());

		try {
			// 1. 공문알림첨부파일
			MultipartFile ontcAtfl = multipartRequest.getFile("ontcAtfl");
			if (ontcAtfl != null && !ontcAtfl.getOriginalFilename().equals("")) {
	        	CmFileVo cmFileVo	= new CmFileVo();

	        	cmFileVo.setFileKd("csrs");		// 디렉토리 구분 : 건설업등록기준사전조사결과(csrs)
				cmFileVo.setFilePath(propertiesService.getString("uploadPath") + "/" + cmFileVo.getFileKd() + "/" + sicims000CmService.getCmnnYear(null) + "/");	// 디렉토리 : /csrs/{년도}/
				cmFileVo.setUpdusrId(ccCsrsVo.getUpdusrId());	// 갱신자ID
				cmFileVo.setUpdusrIp(ccCsrsVo.getUpdusrIp());	// 갱신자IP

				CmFileVo fileInfo = sicims000CmService.insertCmFile(cmFileVo, ontcAtfl);
				ccCsrsVo.setOntcAtflId(fileInfo.getFileId());
			}

			// 2. 결과보고첨부파일
			MultipartFile rsltRptAtfl = multipartRequest.getFile("rsltRptAtfl");
			if (ontcAtfl != null && !rsltRptAtfl.getOriginalFilename().equals("")) {
	        	CmFileVo cmFileVo	= new CmFileVo();

	        	cmFileVo.setFileKd("csrs");		// 디렉토리 구분 : 건설업등록기준사전조사결과(csrs)
				cmFileVo.setFilePath(propertiesService.getString("uploadPath") + "/" + cmFileVo.getFileKd() + "/" + sicims000CmService.getCmnnYear(null) + "/");	// 디렉토리 : /csrs/{년도}/
				cmFileVo.setUpdusrId(ccCsrsVo.getUpdusrId());	// 갱신자ID
				cmFileVo.setUpdusrIp(ccCsrsVo.getUpdusrIp());	// 갱신자IP

				CmFileVo fileInfo = sicims000CmService.insertCmFile(cmFileVo, rsltRptAtfl);
				ccCsrsVo.setRsltRptAtflId(fileInfo.getFileId());
			}

			// 3. 재무과통보첨부파일
			MultipartFile acseNtfctnAtfl = multipartRequest.getFile("acseNtfctnAtfl");
			if (ontcAtfl != null && !acseNtfctnAtfl.getOriginalFilename().equals("")) {
	        	CmFileVo cmFileVo	= new CmFileVo();

	        	cmFileVo.setFileKd("csrs");		// 디렉토리 구분 : 건설업등록기준사전조사결과(csrs)
				cmFileVo.setFilePath(propertiesService.getString("uploadPath") + "/" + cmFileVo.getFileKd() + "/" + sicims000CmService.getCmnnYear(null) + "/");	// 디렉토리 : /csrs/{년도}/
				cmFileVo.setUpdusrId(ccCsrsVo.getUpdusrId());	// 갱신자ID
				cmFileVo.setUpdusrIp(ccCsrsVo.getUpdusrIp());	// 갱신자IP

				CmFileVo fileInfo = sicims000CmService.insertCmFile(cmFileVo, acseNtfctnAtfl);
				ccCsrsVo.setAcseNtfctnAtflId(fileInfo.getFileId());
			}

			return sicims300CcDao.saveOrUpdateCcCsrs(ccCsrsVo);
		} catch (DataAccessException e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw new SicimsException("건설업등록기준사전조사결과 저장 중 오류가 발생하였습니다.");
		}
	}


	/**
	 * 건설업등록기준사전조사결과의 파일 정보를 삭제한다.
	 * @param param HashMap<String, Object>
	 * @return int
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public int deleteCcCsrsFile(HashMap<String, Object> param) {
		try {
			if (param.get("fileId") != null && !param.get("fileId").equals("")) {
				sicims000CmService.deleteCmFile(new BigDecimal(String.valueOf(param.get("fileId"))));
			}

			return sicims300CcDao.deleteCcCsrsFile(param);
		} catch (DataAccessException e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw new SicimsException("건설업등록기준사전조사결과 파일 삭제 중 오류가 발생하였습니다.");
		}
	}






	/********************************/
	/*	1.3 건설업등록기준사전자료제출			*/
	/********************************/
	/**
	 *건설업등록기준사전조사 제출자료 목록을 조회한다.
	 * @param param HashMap<String, Object>
	 * @return List<CcCsexVo>
	 */
	@Override
	public List<CcCsexVo> getCcCsexList(HashMap<String, Object> param){
		try {
			return sicims300CcDao.getCcCsexList(param);
		} catch (DataAccessException e) {
			return new ArrayList<CcCsexVo>();
		}
	}


	/**
	 * 건설업등록기준사전조사 제출자료 정보를  조회한다.
	 * @param param HashMap<String, Object>
	 * @return CcCsexVo
	 */
	@Override
	public CcCsexVo getCcCsexInfo(HashMap<String, Object> param){
		try {
			CcCsexVo ccCsexVo = sicims300CcDao.getCcCsexInfo(param);
			if (ccCsexVo == null) ccCsexVo = new CcCsexVo();
			return ccCsexVo;
		} catch (DataAccessException e) {
			return new CcCsexVo();
		}
	}


	/**
	 * 건설업등록기준사전조사자료 제출 정보를  등록한다.
	 * @param ccCsexVo CcCsexVo
	 * @return int
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public int insertCcCsex(CcCsexVo ccCsexVo){
		ccCsexVo.setUpdusrId(SessionUtil.getLoginUserId());
		ccCsexVo.setUpdusrIp(SessionUtil.getLoginUserIp());

		try {
			// 1. 파일정보
			CmFileVo cmFileVo = new CmFileVo();
			cmFileVo.setFileKd(ccCsexVo.getFileKd());				// 파일종류(디렉토리 구분) : 자료제출(csex)
			cmFileVo.setFileNm(ccCsexVo.getFileNm());				// 파일명
			cmFileVo.setFileOrgnlNm(ccCsexVo.getFileOrgnlNm());		// 원본파일명
			cmFileVo.setFileExtn(ccCsexVo.getFileExtn());			// 파일확장자
			cmFileVo.setFilePath(ccCsexVo.getFilePath());			// 디렉토리 : /csex/{년도}/
			cmFileVo.setFileSz(ccCsexVo.getFileSz());				//파일크기
			cmFileVo.setUpdusrId(ccCsexVo.getUpdusrId());			// 갱신자ID
			cmFileVo.setUpdusrIp(ccCsexVo.getUpdusrIp());			// 갱신자IP

			sicims000CmService.insertCmFile(cmFileVo);
			ccCsexVo.setExmnDataAtflId(cmFileVo.getFileId());

			// 2. 건설업등록기준사전조사 제출상태 변경
			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("cirsSn", 	ccCsexVo.getCirsSn());
			param.put("updusrIp", 	ccCsexVo.getUpdusrIp());
			sicims300CcDao.updateCcCsmaSbmsn(param);

			// 3. 건설업등록기준사전조사자료 등록
			return sicims300CcDao.insertCcCsex(ccCsexVo);
		} catch (DataAccessException e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw new SicimsException("건설업등록기준사전조사자료 제출 저장 중 오류가 발생하였습니다.");
		}
	}




	/********************************/
	/*		2. 민원관리-위반업체행정처분 정보	*/
	/********************************/
	/**
	 * 페이지 단위로 위반업체행정처분 목록을 조회한다.
	 * @param param HashMap<String, Object>
	 * @return List<CcVcadVo>
	 */
	@Override
	public List<CcVcadVo> selectCcVcadList(HashMap<String, Object> param) {
		try {
			return sicims300CcDao.selectCcVcadList(param);
		} catch (DataAccessException e) {
			return new ArrayList<CcVcadVo>();
		}
	}


	/**
	 * 위반업체행정처분 정보를  조회한다.
	 * @param param HashMap<String, Object>
	 * @return CcVcadVo
	 */
	@Override
	public CcVcadVo getCcVcadInfo(HashMap<String, Object> param) {
		try {
			CcVcadVo ccVcadVo = sicims300CcDao.getCcVcadInfo(param);
			if (ccVcadVo == null) ccVcadVo = new CcVcadVo();
			return ccVcadVo;
		} catch (DataAccessException e) {
			return new CcVcadVo();
		}
	}

	/**
	 * 위반업체행정처분 정보를  등록한다.
	 * @param ccVcadVo CcVcadVo
	 * @return int
	 */
	@Override
	@Transactional(rollbackFor=DataAccessException.class)
	public int insertCcVcad(CcVcadVo ccVcadVo) {
		// 법인등록번호/통보일자/처분종결일자 '-'제거
		ccVcadVo.setCrno(StringUtil.removeDelimChar(ccVcadVo.getCrno(), "-", ""));					// 법인등록번호 '-'제거
		ccVcadVo.setNtfctnYmd(StringUtil.removeDelimChar(ccVcadVo.getNtfctnYmd(), "-", ""));		// 통보일자 '-'제거
		ccVcadVo.setDspsTrmnYmd(StringUtil.removeDelimChar(ccVcadVo.getDspsTrmnYmd(), "-", ""));	// 처분종결일자 '-'제거

		ccVcadVo.setUpdusrId(SessionUtil.getLoginUserId());
		ccVcadVo.setUpdusrIp(SessionUtil.getLoginUserIp());

		try {
			return sicims300CcDao.insertCcVcad(ccVcadVo);
		} catch (DataAccessException e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw new SicimsException("위반업체행정처분 등록 중 오류가 발생하였습니다.");
		}
	}


	/**
	 * 위반업체행정처분 정보를  수정한다.
	 * @param ccVcadVo CcVcadVo
	 * @return int
	 */
	@Override
	@Transactional(rollbackFor=DataAccessException.class)
	public int updateCcVcad(CcVcadVo ccVcadVo) {
		// 법인등록번호/통보일자/처분종결일자 '-'제거
		ccVcadVo.setCrno(StringUtil.removeDelimChar(ccVcadVo.getCrno(), "-", ""));					// 법인등록번호 '-'제거
		ccVcadVo.setNtfctnYmd(StringUtil.removeDelimChar(ccVcadVo.getNtfctnYmd(), "-", ""));		// 통보일자 '-'제거
		ccVcadVo.setDspsTrmnYmd(StringUtil.removeDelimChar(ccVcadVo.getDspsTrmnYmd(), "-", ""));	// 처분종결일자 '-'제거

		ccVcadVo.setUpdusrId(SessionUtil.getLoginUserId());
		ccVcadVo.setUpdusrIp(SessionUtil.getLoginUserIp());

		try {
			return sicims300CcDao.updateCcVcad(ccVcadVo);
		} catch (DataAccessException e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw new SicimsException("위반업체행정처분 수정 중 오류가 발생하였습니다.");
		}
	}


	/**
	 * 위반업체행정처분 정보를 삭제한다.
	 * @param param HashMap<String, Object>
	 * @return int
	 */
	@Override
	@Transactional(rollbackFor=DataAccessException.class)
	public int deleteCcVcad(HashMap<String, Object> param){
		try {
			return sicims300CcDao.deleteCcVcad(param);
		} catch (DataAccessException e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw new SicimsException("위반업체행정처분 삭제 중 오류가 발생하였습니다.");
		}
	}




	/********************************/
	/*		3. 불법하도급신고 관리			*/
	/********************************/
	/**
	 * 페이지 단위로 불법하도급신고 목록을 조회한다.
	 * @param param HashMap<String, Object>
	 * @return List<CcIsrdVo>
	 */
	@Override
	public List<CcIsrdVo> selectCcIsrdList(HashMap<String, Object> param){
		try {
			return sicims300CcDao.selectCcIsrdList(param);
		} catch (DataAccessException e) {
			return new ArrayList<CcIsrdVo>();
		}
	}


	/**
	 * KISCON 관리번호로 불법하도급신고 목록을 조회한다.
	 * @param param HashMap<String, Object>
	 * @return List<CcIsrdVo>
	 */
	public List<CcIsrdVo> getCcIsrdList(HashMap<String, Object> param){
		try {
			return sicims300CcDao.getCcIsrdList(param);
		} catch (DataAccessException e) {
			return new ArrayList<CcIsrdVo>();
		}
	}


	/**
	 * 불법하도급신고 정보를  조회한다.
	 * @param param HashMap<String, Object>
	 * @return CcIsrdVo
	 */
	@Override
	public CcIsrdVo getCcIsrdInfo(HashMap<String, Object> param){
		try {
			CcIsrdVo ccIsrdVo = sicims300CcDao.getCcIsrdInfo(param);
			if (ccIsrdVo == null) ccIsrdVo = new CcIsrdVo();
			return ccIsrdVo;
		} catch (DataAccessException e) {
			return new CcIsrdVo();
		}
	}


	/**
	 * 불법하도급신고 정보를  등록한다.
	 * @param param HashMap<String, Object>
	 * @param  ccIsrd CcIsrdVo
	 * @param multiValueMap MultiValueMap<String, MultipartFile>
	 * @return int
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public int insertCcIsrd(HashMap<String, Object> param, CcIsrdVo ccIsrdVo, MultiValueMap<String, MultipartFile> multiValueMap){
    	// 1. 접수일자/신고자생년월일 '-'제거
		ccIsrdVo.setRcptYmd(StringUtil.removeDelimChar(ccIsrdVo.getRcptYmd(), "-", ""));	// 접수일자 '-'제거
		ccIsrdVo.setDclBrdt(StringUtil.removeDelimChar(ccIsrdVo.getDclBrdt(), "-", ""));	// 신고자생년월일 '-'제거

		ccIsrdVo.setUpdusrId(SessionUtil.getLoginUserId());
		ccIsrdVo.setUpdusrIp(SessionUtil.getLoginUserIp());

		try {
			// 2. 첨부파일 체크
			MultipartFile multipartFile	=  (MultipartFile) multiValueMap.getFirst("file");
			if (multipartFile != null && !multipartFile.getOriginalFilename().equals("")) {
				// 2.1 파일확장자 체크
				String fileExt = sicims000CmService.getFileExtension(multipartFile.getOriginalFilename());
				if (!fileExt.equals("hwp") && !fileExt.equals("pdf") && !fileExt.equals("xls") && !fileExt.equals("xlsx") &&
					!fileExt.equals("zip") && !fileExt.equals("egg") && !fileExt.equals("avi") && !fileExt.equals("mp4") ) {
					throw new SicimsException("첨부파일은 업로드 제한 파일(확장자)입니다.");
				}

				// 2.2 파일업로드 및 파일정보 등록 체크
				CmFileVo cmFileVo = new CmFileVo();
				cmFileVo.setFileKd("isrd");		// 디렉토리 구분 : 불법하도급신고(isrd)
				cmFileVo.setFilePath(propertiesService.getString("uploadPath") + "/" + cmFileVo.getFileKd() + "/" + sicims000CmService.getCmnnYear(null) + "/");	// 디렉토리 : /isrd/{년도}/
				cmFileVo.setUpdusrId(ccIsrdVo.getUpdusrId());		// 갱신자ID
				cmFileVo.setUpdusrIp(ccIsrdVo.getUpdusrIp());		// 갱신자IP

				CmFileVo fileInfo = sicims000CmService.insertCmFile(cmFileVo, multipartFile);
				ccIsrdVo.setDclrFileId(fileInfo.getFileId());
			} else {
				ccIsrdVo.setDclrFileId(null);
			}

			// 3. 데이터 등록
			return sicims300CcDao.insertCcIsrd(ccIsrdVo);
		} catch (DataAccessException e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw new SicimsException("불법하도급신고 등록 중 오류가 발생하였습니다.");
		}
	}



	/**
	 * 불법하도급신고 정보를  수정한다.
	 * @param param HashMap<String, Object>
	 * @param ccIsrd CcIsrdVo
	 * @param multiValueMap MultiValueMap<String, MultipartFile>
	 * @return int
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public int updateCcIsrd(HashMap<String, Object> param, CcIsrdVo ccIsrdVo, MultiValueMap<String, MultipartFile> multiValueMap){
    	// 1. 접수일자/신고자생년월일 '-'제거
		ccIsrdVo.setRcptYmd(StringUtil.removeDelimChar(ccIsrdVo.getRcptYmd(), "-", ""));	// 접수일자 '-'제거
		ccIsrdVo.setDclBrdt(StringUtil.removeDelimChar(ccIsrdVo.getDclBrdt(), "-", ""));	// 신고자생년월일 '-'제거

		ccIsrdVo.setUpdusrId(SessionUtil.getLoginUserId());
		ccIsrdVo.setUpdusrIp(SessionUtil.getLoginUserIp());

		try {
			// 2. 첨부파일 체크
			MultipartFile multipartFile	=  (MultipartFile) multiValueMap.getFirst("file");
			if (multipartFile != null && !multipartFile.getOriginalFilename().equals("")) {
				// 2.1 파일확장자 체크
				String fileExt = sicims000CmService.getFileExtension(multipartFile.getOriginalFilename());
				if (!fileExt.equals("hwp") && !fileExt.equals("pdf") && !fileExt.equals("xls") && !fileExt.equals("xlsx") &&
					!fileExt.equals("zip") && !fileExt.equals("egg") && !fileExt.equals("avi") && !fileExt.equals("mp4") ) {
					throw new SicimsException("첨부파일은 업로드 제한 파일(확장자)입니다.");
				}

				// 2.2 파일업로드 및 파일정보 수정 체크
				CmFileVo cmFileVo = new CmFileVo();
				if (ccIsrdVo.getDclrFileId() != null) {
					cmFileVo = sicims000CmService.getCmFileInfo(ccIsrdVo.getDclrFileId());
					cmFileVo.setUpdusrId(ccIsrdVo.getUpdusrId());		// 갱신자ID
					cmFileVo.setUpdusrIp(ccIsrdVo.getUpdusrIp());		// 갱신자IP

					BigDecimal fileId = sicims000CmService.updateCmFile(cmFileVo, multipartFile);
					ccIsrdVo.setDclrFileId(fileId);
				} else {
					cmFileVo.setFileKd("isrd");		// 디렉토리 구분 : 불법하도급신고(isrd)
					cmFileVo.setFilePath(propertiesService.getString("uploadPath") + "/" + cmFileVo.getFileKd() + "/" + sicims000CmService.getCmnnYear(null) + "/");	// 디렉토리 : /{ntbd|rsrm}/{년도}/
					cmFileVo.setUpdusrId(ccIsrdVo.getUpdusrId());		// 갱신자ID
					cmFileVo.setUpdusrIp(ccIsrdVo.getUpdusrIp());		// 갱신자IP

					CmFileVo fileInfo = sicims000CmService.insertCmFile(cmFileVo, multipartFile);
					ccIsrdVo.setDclrFileId(fileInfo.getFileId());
				}
			}

			// 3. 데이터 수정
			return sicims300CcDao.updateCcIsrd(ccIsrdVo);
		} catch (DataAccessException e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw new SicimsException("불법하도급신고 수정 중 오류가 발생하였습니다.");
		}
	}


	/**
	 * 불법하도급신고 정보를 삭제한다.
	 * @param param HashMap<String, Object>
	 * @return int
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public int deleteCcIsrd(HashMap<String, Object> param){
		try {
			if (param.get("dclrFileId") != null && !param.get("dclrFileId").equals("")) {
				sicims000CmService.deleteCmFile(new BigDecimal(String.valueOf(param.get("dclrFileId"))));
			}

			return sicims300CcDao.deleteCcIsrd(param);
		} catch (DataAccessException e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw new SicimsException("불법하도급신고 삭제 중 오류가 발생하였습니다.");
		}
	}



	/**
	 * 불법하도급신고 파일 정보를 삭제한다.
	 * @param param HashMap<String, Object>
	 * @return int
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public int deleteCcIsrdFile(HashMap<String, Object> param){
		try {
			if (param.get("dclrFileId") != null && !param.get("dclrFileId").equals("")) {
				sicims000CmService.deleteCmFile(new BigDecimal(String.valueOf(param.get("dclrFileId"))));
			}

			return sicims300CcDao.deleteCcIsrdFile(param);
		} catch (DataAccessException e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw new SicimsException("불법하도급신고 파일 삭제 중 오류가 발생하였습니다.");
		}
	}


	/**
	 * 불법하도급신고결과 정보를  조회한다.
	 * @param param HashMap<String, Object>
	 * @return CcIsrrVo
	 */
	@Override
	public CcIsrrVo getCcIsrrInfo(HashMap<String, Object> param) {
		try {
			CcIsrrVo ccIsrrVo = sicims300CcDao.getCcIsrrInfo(param);
			if (ccIsrrVo == null) ccIsrrVo = new CcIsrrVo();
			return ccIsrrVo;
		} catch (DataAccessException e) {
			return new CcIsrrVo();
		}
	}


	/**
	 * 불법하도급신고결과 정보를  저장한다.
	 * @param param HashMap<String, Object>
	 * @param ccIsrr CcIsrrVo
	 * @param multiValueMap MultiValueMap<String, MultipartFile>
	 * @return int
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public int saveOrUpdateCcIsrr(HashMap<String, Object> param, CcIsrrVo ccIsrrVo,
			MultiValueMap<String, MultipartFile> multiValueMap) {
		// 1. 갱신자
		ccIsrrVo.setUpdusrId(SessionUtil.getLoginUserId());
		ccIsrrVo.setUpdusrIp(SessionUtil.getLoginUserIp());

		try {
			// 2. 첨부파일 체크
			MultipartFile multipartFile	=  (MultipartFile) multiValueMap.getFirst("file");
			if (multipartFile != null && !multipartFile.getOriginalFilename().equals("")) {
				// 2.1 파일확장자 체크
				String fileExt = sicims000CmService.getFileExtension(multipartFile.getOriginalFilename());
				if (!fileExt.equals("hwp") && !fileExt.equals("pdf") && !fileExt.equals("xls") && !fileExt.equals("xlsx") &&
					!fileExt.equals("zip") && !fileExt.equals("egg") && !fileExt.equals("avi") && !fileExt.equals("mp4") ) {
					throw new SicimsException("첨부파일은 업로드 제한 파일(확장자)입니다.");
				}


				// 2.2 파일업로드 및 파일정보 수정 체크
				CmFileVo cmFileVo = new CmFileVo();
				if (ccIsrrVo.getRsltFileId() != null) {
					cmFileVo = sicims000CmService.getCmFileInfo(ccIsrrVo.getRsltFileId());
					cmFileVo.setUpdusrId(ccIsrrVo.getUpdusrId());		// 갱신자ID
					cmFileVo.setUpdusrIp(ccIsrrVo.getUpdusrIp());		// 갱신자IP

					BigDecimal fileId = sicims000CmService.updateCmFile(cmFileVo, multipartFile);
					ccIsrrVo.setRsltFileId(fileId);
				} else {
					cmFileVo.setFileKd("isrd");		// 디렉토리 구분 : 불법하도급신고 및 결과(isrd)
					cmFileVo.setFilePath(propertiesService.getString("uploadPath") + "/" + cmFileVo.getFileKd() + "/" + sicims000CmService.getCmnnYear(null) + "/");	// 디렉토리 : /{ntbd|rsrm}/{년도}/
					cmFileVo.setUpdusrId(ccIsrrVo.getUpdusrId());		// 갱신자ID
					cmFileVo.setUpdusrIp(ccIsrrVo.getUpdusrIp());		// 갱신자IP

					CmFileVo fileInfo = sicims000CmService.insertCmFile(cmFileVo, multipartFile);
					ccIsrrVo.setRsltFileId(fileInfo.getFileId());
				}
			}

			// 3. 데이터 저장
			return sicims300CcDao.saveOrUpdateCcIsrr(ccIsrrVo);
		} catch (DataAccessException e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw new SicimsException("불법하도급신고결과 저장 중 오류가 발생하였습니다.");
		}
	}


	/**
	 * 불법하도급신고결과 정보를 삭제한다.
	 * @param param HashMap<String, Object>
	 * @return int
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public int deleteCcIsrr(HashMap<String, Object> param) {
		try {
			if (param.get("rsltFileId") != null && !param.get("rsltFileId").equals("")) {
				sicims000CmService.deleteCmFile(new BigDecimal(String.valueOf(param.get("rsltFileId"))));
			}

			return sicims300CcDao.deleteCcIsrd(param);
		} catch (DataAccessException e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw new SicimsException("불법하도급신고결과 삭제 중 오류가 발생하였습니다.");
		}
	}


	/**
	 * 불법하도급신고결과 파일 정보를 삭제한다.
	 * @param param HashMap<String, Object>
	 * @return int
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public int deleteCcIsrrFile(HashMap<String, Object> param) {
		try {
			if (param.get("rsltFileId") != null && !param.get("rsltFileId").equals("")) {
				sicims000CmService.deleteCmFile(new BigDecimal(String.valueOf(param.get("rsltFileId"))));
			}

			return sicims300CcDao.deleteCcIsrrFile(param);
		} catch (DataAccessException e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw new SicimsException("불법하도급신고결과 파일 삭제 중 오류가 발생하였습니다.");
		}
	}

}
