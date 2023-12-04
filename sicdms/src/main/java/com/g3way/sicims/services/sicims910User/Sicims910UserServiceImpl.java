package com.g3way.sicims.services.sicims910User;

import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.g3way.sicims.exception.SicimsException;
import com.g3way.sicims.services.sicims910User.vo.CmUserVo;
import com.g3way.sicims.util.common.SessionUtil;
import com.g3way.sicims.util.common.StringUtil;
import com.g3way.sicims.util.encrypt.EgovFileScrty;
import com.g3way.sicims.util.encrypt.RsaUtil;

import egovframework.com.cmm.service.EgovProperties;



@Service
public class Sicims910UserServiceImpl implements Sicims910UserService {
	private static final Logger LOG = LoggerFactory.getLogger(Sicims910UserServiceImpl.class);

	@Autowired	private Sicims910UserDao 	sicims910UserDao;

	/********************************/
	/*		1. 사용자 관리				*/
	/********************************/

	/**
	 * 사용자 목록을 조회한다.
	 * @param param HashMap<String, Object>
	 * @return List<CmUserVo>
	 */
	@Override
	public List<CmUserVo> getCmUserList(HashMap<String, Object> param) {
		try {
			return sicims910UserDao.getCmUserList(param);
		} catch (DataAccessException e) {
			return new ArrayList<CmUserVo>();
		}
	}

	/**
	 * 페이지 단위로 사용자 목록을 조회한다.
	 * @param param HashMap<String, Object>
	 * @return List<CmUserExtendsVo>
	 */
	@Override
	public List<CmUserVo> selectCmUserList(HashMap<String, Object> param) {
		try {
			return sicims910UserDao.selectCmUserList(param);
		} catch (DataAccessException e) {
			return new ArrayList<CmUserVo>();
		}
	}

	/**
	 * 사용자 정보를 조회한다.
	 * @param param HashMap<String, Object>
	 * @return CmUserVo
	 */
	@Override
	public CmUserVo getCmUserInfo(HashMap<String, Object> param) {
		try {
			CmUserVo cmUserVo = sicims910UserDao.getCmUserInfo(param);
			if (cmUserVo == null) cmUserVo = new CmUserVo();
			return cmUserVo;
		} catch (DataAccessException e) {
			return new CmUserVo();
		}
	}

	/**
	 * 사용자 정보를  저장한다.
	 * @param cmUserVo CmUserVo
	 * @return int
	 */
	@Override
	@Transactional(rollbackFor=DataAccessException.class)
	public int insertCmUser(CmUserVo cmUserVo) {
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("userId", 	cmUserVo.getUserId());

		CmUserVo dncryptVo = (CmUserVo) RsaUtil.getDncryptVo(cmUserVo);
		try {
			//패스워드 암호화
			String encryptPass = EgovFileScrty.encryptPassword(dncryptVo.getUserPswd(), dncryptVo.getUserId());
			dncryptVo.setUserPswd(encryptPass);

			if(!StringUtil.checkExp("id", dncryptVo.getUserId())){
				LOG.error("id값을 확인하세요.");
				throw new SicimsException("입력한 id 값을 확인하세요.");
			}

			sicims910UserDao.insertCmUser(dncryptVo);
			sicims910UserDao.saveCmScrt(param);
			return 1;
		} catch (NoSuchAlgorithmException e) {
			return -2;
		} catch (DataAccessException e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return -1;
		}
	}

	/**
	 * 사용자 정보를  수정한다.
	 * @param cmUserVo CmUserVo
	 * @return int
	 */
	@Override
	@Transactional(rollbackFor=DataAccessException.class)
	public int updateCmUser(CmUserVo cmUserVo) {
		int insertRowCnt = 0;

		// 사용자정보 조회(기존 정보와 비교하기 위해)
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("userId", cmUserVo.getUserId());
		CmUserVo userInfo = this.getCmUserInfo(param);

		boolean insAuthrtYn = false;	// 권한등록여부
		boolean delAuthrtYn = false;	// 권한삭제여부

		// 1. 가입/탈퇴
		if (cmUserVo.getUseYn() != null) {
			if (!cmUserVo.getUseYn().equals(userInfo.getUseYn())) {
				cmUserVo.setAprvUpdtYn("Y");
				cmUserVo.setAprvYn("N");
				if ("Y".equals(cmUserVo.getUseYn())) {
					// 1-1 탈퇴에서 가입으로 변경
					cmUserVo.setWhdwlRsn("");
				} else {
					// 1-2 가입에서 탈퇴로 변경  -> 권한 삭제
					if (cmUserVo.getWhdwlRsn().equals("")) {
						cmUserVo.setWhdwlRsn("시스템관리자에 의해 탈퇴 처리");
					}
					delAuthrtYn = true;
				}
			}
		}

		// 2. 승인여부가 다른 경우
		if (cmUserVo.getAprvYn() != null) {
			if (!"Y".equals(cmUserVo.getAprvUpdtYn())) {
				if (!cmUserVo.getAprvYn().equals(userInfo.getAprvYn())) {
					cmUserVo.setAprvUpdtYn("Y");
				} else {
					cmUserVo.setAprvUpdtYn("N");
				}
			}
			// 권한 등록 or 삭제
			if ("Y".equals(cmUserVo.getAprvUpdtYn())) {
				if ("Y".equals(cmUserVo.getAprvYn())) {
					insAuthrtYn = true;
				} else {
					delAuthrtYn = true;
				}
			}
		}

		// 3. 권한이 변경된 경우
		if (cmUserVo.getAuthrtCd() != null && !cmUserVo.getAuthrtCd().equals(userInfo.getAuthrtCd())) {
			insAuthrtYn = true;
		}



		// 4. 인사이동이 발생한 경우
		if (cmUserVo.getInst1Cd() != null && !"".equals(cmUserVo.getInst1Cd()) &&
			cmUserVo.getInst2Cd() != null && !"".equals(cmUserVo.getInst2Cd())) {
			// 기존 기관1, 기관2와 다를 경우
			if (!cmUserVo.getInst1Cd().equals(userInfo.getInst1Cd()) ||
				!cmUserVo.getInst2Cd().equals(userInfo.getInst2Cd()) ) {

				cmUserVo.setAprvUpdtYn("Y");
				// 서울시 업무담당자
				if (cmUserVo.getInst1Cd().substring(0, 1).equals("6")) {
					cmUserVo.setUserSe("0010"); 		// 서울시 업무담당자
					cmUserVo.setAuthrtCd("ROLE_CTPV"); 	// ROLE_CTPV
				}
				// 자치구 업무담당자
				if (cmUserVo.getInst1Cd().substring(0, 1).equals("3")) {
					cmUserVo.setUserSe("0020"); 		// 자치구 업무담당자
					cmUserVo.setAuthrtCd("ROLE_SGG"); 	// ROLE_SGG
				}
			}
		}


    	// 4.전화번호 '-'제거
    	if (cmUserVo.getTelno() != null && !"".equals(cmUserVo.getTelno())){
    		cmUserVo.setTelno(cmUserVo.getTelno().replace("-", ""));
    	}

    	try {
    		insertRowCnt = sicims910UserDao.updateCmUser(cmUserVo);

    		// 권한 등록
    		if (insAuthrtYn) {
    			param.put("authrtCd", cmUserVo.getAuthrtCd());
				param.put("updusrId", cmUserVo.getUpdusrId());
				param.put("updusrIp", cmUserVo.getUpdusrIp());
				sicims910UserDao.saveCmScrt(param);
    		}

    		// 권한 삭제
    		if (delAuthrtYn) {
    			sicims910UserDao.deleteCmScrt(param);
    		}

    		return insertRowCnt;
		} catch (DataAccessException e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return -1;
		}

	}


	/**
	 * 비밀번호를 초기화한다.
	 * @param param HashMap<String, Object>
	 * @return int
	 */
	@Override
	public int resetPassword(HashMap<String, Object> param) {
		try {
			//패스워드 암호화
			String resetPswd = EgovProperties.getProperty("Global.password.reset");
			String enUserPswd = EgovFileScrty.encryptPassword(resetPswd, (String)param.get("userId"));
			param.put("userPswd", enUserPswd);

			return sicims910UserDao.resetPassword(param);
		} catch (DataAccessException e) {
			LOG.error(e.getMessage());
			throw new SicimsException("비밀번호 초기화 중 오류가 발생하였습니다.");
		} catch ( NoSuchAlgorithmException e) {
			LOG.error(e.getMessage());
			throw new SicimsException("비밀번호 초기화 중 오류가 발생하였습니다.");
		}
	}

	/**
	 * 사용자 정보를 삭제한다.
	 * @param param HashMap<String, Object>
	 * @return HashMap<String, Object>
	 */
	@Override
	@Transactional(rollbackFor=DataAccessException.class)
	public int deleteCmUser(HashMap<String, Object> param) {
		try {
			sicims910UserDao.deleteCmUser(param);
			sicims910UserDao.deleteCmScrt(param);
			return 1;
		} catch (DataAccessException e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return -1;
		}
	}


	/**
	 * 사용자 승인정보를   저장한다.
	 * @param list ArrayList<CmUserVo>
	 * @return int
	 */
	@Override
	public int saveOrUpdateUserList(ArrayList<CmUserVo> list) {
		int rowCnt = 0;

		for(CmUserVo cmUserVo : list){
			cmUserVo.setUpdusrId(SessionUtil.getLoginUserId());
			cmUserVo.setUpdusrIp(SessionUtil.getLoginUserIp());
			//rowCnt += this.saveUserInfo(accepUser);
		}

		return rowCnt;
	}



	/**
	 * 사용자 승인 정보를  수정한다.
	 * @param cmUserVo CmUserVo
	 * @return int
	 */
	@Override
	public int updateCmUserApprove(CmUserVo cmUserVo) {
		int insertRowCnt = 0;

		// 사용자정보 조회(기존 승인 정보와 비교하기 위해)
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("userId", cmUserVo.getUserId());
		CmUserVo userInfo = this.getCmUserInfo(param);

		// 승인여부가 다른 경우
		if (!cmUserVo.getAprvYn().equals(userInfo.getAprvYn())) {
			cmUserVo.setAprvUpdtYn("Y");
			cmUserVo.setAutzrId(SessionUtil.getLoginUserIp());
			if (cmUserVo.getAprvYn().equals("Y")) {
				//비밀번호 오류수는 0으로 설정
				cmUserVo.setPswdErrCnt(new BigDecimal(0));
			}
		} else {
			cmUserVo.setAprvUpdtYn("N");
		}


		// 가입/탈퇴인 경우
		if (cmUserVo.getUseYn().equals("Y")) {
			cmUserVo.setWhdwlRsn("");
		} else {
			if (cmUserVo.getWhdwlRsn().equals("")) {
				cmUserVo.setWhdwlRsn("시스템관리자에 의해 탈퇴 처리");
			}
		}

		cmUserVo.setUpdusrId(SessionUtil.getLoginUserId());
		cmUserVo.setUpdusrIp(SessionUtil.getLoginUserIp());

		insertRowCnt = sicims910UserDao.updateCmUserApprove(cmUserVo);

		return insertRowCnt;
	}

}
