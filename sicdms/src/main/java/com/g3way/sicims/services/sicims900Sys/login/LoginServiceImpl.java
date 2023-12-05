package com.g3way.sicims.services.sicims900Sys.login;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.g3way.sicims.exception.SicimsException;
import com.g3way.sicims.services.sicims910User.vo.CmUserVo;
import com.g3way.sicims.util.common.SessionUtil;
import com.g3way.sicims.util.common.StringUtil;
import com.g3way.sicims.util.encrypt.EgovFileScrty;
import com.g3way.sicims.util.encrypt.RsaUtil;

/**
 * 일반 로그인, 인증서 로그인을 처리하는 비즈니스 구현 클래스
 * @author 공통서비스 개발팀 박지욱
 * @since 2009.03.06
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2009.03.06  박지욱          최초 생성
 *  2011.08.26  서준식          EsntlId를 이용한 로그인 추가
 *  2014.12.08	이기하			암호화방식 변경(EgovFileScrty.encryptPassword)
 *  </pre>
 */
@Service("loginService")
public class LoginServiceImpl implements LoginService {

    @Resource(name="loginDao")
    private LoginDao loginDao;


    private static final Logger LOG = LoggerFactory.getLogger(LoginServiceImpl.class);

	/**
	 * 사용자 정보를 조회한다.
	 * @param param CmUserVo
	 * @return CmUserVo
	 */
    @Override
	public CmUserVo getLoginInfo(CmUserVo param){
    	// 1. 입력한 비밀번호를 암호화한다.
		String enpassword;
		try {
			if(param.getUserPswd() != null && param.getUserPswd().length() != 0) {
				enpassword = EgovFileScrty.encryptPassword(param.getUserPswd(), param.getUserId());
				param.setUserPswd(enpassword);
			}
		} catch (NoSuchAlgorithmException e) {
			LOG.error("암호화 중 오류가 발생했습니다. NoSuchAlgorithmException");
		}

		// 2. 아이디와 암호화된 비밀번호가 DB와 일치하는지 확인한다.
		CmUserVo cmUserVo = loginDao.getLoginInfo(param);


    	// 3. 결과를 리턴한다.
    	if (cmUserVo != null && !cmUserVo.getUserId().equals("") && !cmUserVo.getUserPswd().equals("")) {
    		return cmUserVo;
    	} else {
    		cmUserVo = new CmUserVo();
    	}

    	return cmUserVo;
    }


	/**
	 * 사용자 아이디에 해당 권한 부여되어 있는지 조회한다.
	 * @param param HashMap<String, Object>
	 * @return int
	 */
    @Override
	public int getCmRhrcInfo(HashMap<String, Object> param){
		try {
			return loginDao.getCmRhrcInfo(param);
		} catch (DataAccessException e) {
			return -1;
		}
    }

	/**
	 * 사용자 아이디에 해당 권한 부여되어 있는지 조회한다.
	 * @param userId String
	 * @param authrtCd String
	 * @return int
	 */
    @Override
	public int getCmRhrcInfo(String userId, String authrtCd){
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("userId", 	userId);
		param.put("authrtCd", authrtCd);

		return this.getCmRhrcInfo(param);
    }


	/**
	 * 사용자 로그인 실패시 패스워드 오류를 증가한다
	 * @param param CmUserVo
	 * @return int
	 * @exception Exception
	 */
    @Override
	public int updateLoginFail(CmUserVo param) {
    	if(param.getUserId() == null || "".equals(param.getUserId())){
    		LOG.error("수정할 로그인 아이디가 없습니다.");
			throw new SicimsException("수정할 로그인 아이디가 없습니다.");
    	}

    	// 외부사용자(ROLE_ECMP)는 정보를 저장하지 않음
    	if (param.getAuthrtCd().equals("ROLE_ECMP")) {
    		return 0;
    	}

    	return loginDao.updateLoginFail(param);
	}


	/**
	 * 사용자 로그인 성공시 마지막로그인일자와 패스워드 오류를 수정한다.
	 * @param param CmUserVo
	 * @return int
	 */
    @Override
	public int updateLoginSuccess(CmUserVo param){
    	if(param.getUserId() == null || "".equals(param.getUserId())){
    		LOG.error("수정할 로그인 아이디가 없습니다.");
			throw new SicimsException("수정할 로그인 아이디가 없습니다.");
    	}

    	// 외부사용자(ROLE_ECMP)는 정보를 저장하지 않음
    	if (param.getAuthrtCd().equals("ROLE_ECMP")) {
    		return 0;
    	}

    	return loginDao.updateLoginSuccess(param);
	}

	/**
	 * 사용자 정보를  등록한다.
	 * @param param CmUserVo
	 * @return int
	 * @exception DataAccessException
	 */
	@Override
	public int insertCmUser(CmUserVo param){
		param.setUpdusrId(param.getUserId());
		param.setUpdusrIp(SessionUtil.getLoginUserIp());
		CmUserVo dncryptVo = (CmUserVo) RsaUtil.getDncryptVo(param);

		try {
			//패스워드 암호화
			String encryptPass = EgovFileScrty.encryptPassword(dncryptVo.getUserPswd1(), dncryptVo.getUserId());
			dncryptVo.setUserPswd(encryptPass);

			if(!StringUtil.checkExp("userId", dncryptVo.getUserId())){
				LOG.error("id값을 확인하세요.");
				throw new SicimsException("입력한 id 값을 확인하세요.");
			} else if(!StringUtil.checkExp("userPswd", dncryptVo.getUserPswd1())){
				LOG.error("패스워드를 확인하세요.");
				throw new SicimsException("입력한 패스워드 값을 확인하세요.");
			} else if(!StringUtil.checkExp("userPswd", dncryptVo.getUserPswd2())){
				LOG.error("패스워드를 확인하세요.");
				throw new SicimsException("입력한 패스워드확인 값을 확인하세요.");
			} else if(!dncryptVo.getUserPswd1().equals(dncryptVo.getUserPswd2())){
				LOG.error("패스워드와 패스워드 확인 값이 일치하지 않습니다.");
				throw new SicimsException("패스워드와 패스워드 확인 값이 일치하지 않습니다.");
			} else if(!StringUtil.checkExp("hrscRplcKey", dncryptVo.getHrscRplcKey())){
				LOG.error("인사대체키 값을 확인하세요.");
				throw new SicimsException("입력한 인사대체키 값을 값을 확인하세요.");
			}

			// 전화번호
			if(dncryptVo.getTelno() != null && !"".equals(dncryptVo.getTelno())){
				dncryptVo.setTelno(dncryptVo.getTelno().replace("-", ""));
			}

			// 서울시 업무담당자일 경우
			if (dncryptVo.getInst1Cd().substring(0, 1).equals("6")) {
				dncryptVo.setUserSe("0010"); 			// 서울시 업무담당자
				dncryptVo.setAuthrtCd("ROLE_CTPV"); 	// ROLE_CTPV
			}
			// 자치구 업무담당자
			if (dncryptVo.getInst1Cd().substring(0, 1).equals("3")) {
				dncryptVo.setUserSe("0020"); 				// 서울시 업무담당자
				dncryptVo.setAuthrtCd("ROLE_SGG"); 	// ROLE_SGG
			}

			return loginDao.insertCmUser(dncryptVo);
		} catch (NoSuchAlgorithmException e) {
			return -2;
		} catch (DataAccessException e) {
			e.printStackTrace();
			return -1;
		}
    }


	/**
	 * 사용자 정보를  수정한다.
	 * @param param CmUserVo
	 * @return int
	 * @exception DataAccessException
	 */
	@Override
	public int updateCmUser(CmUserVo param){
    	int insertRowCnt = 0;
    	CmUserVo dncryptVo = (CmUserVo) RsaUtil.getDncryptVo(param);
    	dncryptVo.setUserId(SessionUtil.getLoginUserId());

    	if(dncryptVo.getUserId() == null || "".equals(dncryptVo.getUserId())){
    		LOG.error("수정할 로그인 아이디가 없습니다.");
			throw new SicimsException("수정할 로그인 아이디가 없습니다.");
    	}
    	if(!dncryptVo.getUserId().equals(SessionUtil.getLoginUserId())){
    		LOG.error("수정할 회원정보와 로그인 아이디가 일치하지 않습니다.");
			throw new SicimsException("자신의 회원정보만 수정할 수 있습니다.");
    	}

    	//전화번호 '-'제거
    	if(dncryptVo.getTelno() != null && !"".equals(dncryptVo.getTelno())){
    		dncryptVo.setTelno(dncryptVo.getTelno().replace("-", ""));
    	}

   		insertRowCnt = loginDao.updateCmUser(dncryptVo);

    	return insertRowCnt;
    }


	/**
	 * 사용자 패스워드를  수정한다.
	 * @param param CmUserVo
	 * @return int
	 * @exception DataAccessException
	 */
	@Override
	public int updatePswd(CmUserVo param){
		int insertRowCnt = 0;
		CmUserVo dncryptVo = (CmUserVo) RsaUtil.getDncryptVo(param);

    	if(!StringUtil.checkExp("userPswd", dncryptVo.getUserPswd1())){
			LOG.error("패스워드를 확인하세요.");
			throw new SicimsException("입력한 패스워드 값을 확인하세요.");
		} else if(!StringUtil.checkExp("userPswd", dncryptVo.getUserPswd2())){
			LOG.error("패스워드를 확인하세요.");
			throw new SicimsException("입력한 패스워드확인 값을 확인하세요.");
		} else if(!dncryptVo.getUserPswd1().equals(dncryptVo.getUserPswd2())){
			LOG.error("패스워드와 패스워드 확인 값이 일치하지 않습니다.");
			throw new SicimsException("패스워드와 패스워드 확인 값이 일치하지 않습니다.");
		}

		//패스워드 암호화
		try {
			String nowPass = EgovFileScrty.encryptPassword(dncryptVo.getUserPswd(), dncryptVo.getUserId());
			dncryptVo.setUserPswd(nowPass);

			// 2. 아이디와 암호화된 비밀번호가 DB와 일치하는지 확인한다.
			CmUserVo cmUserVoCheck = loginDao.getLoginInfo(dncryptVo);

			if(cmUserVoCheck == null || cmUserVoCheck.getUserId() == null || "".equals( cmUserVoCheck.getUserId())){
				LOG.error("현재 패스워드 값이 일치하지 않습니다.");
				throw new SicimsException("현재 패스워드 값이 일치하지 않습니다.");
			}

			String encryptPass = EgovFileScrty.encryptPassword(dncryptVo.getUserPswd1(), dncryptVo.getUserId());
			dncryptVo.setUserPswd(encryptPass);

			insertRowCnt = loginDao.updatePswd(dncryptVo);
		} catch (NoSuchAlgorithmException e) {
			LOG.error("패스워드 암호화중 오류가 발생했습니다.");
			throw new SicimsException("패스워드 암호화중 오류가 발생했습니다.");
		}

    	return insertRowCnt;
    }


	/**
	 * 외부사용자 패스워드를  수정한다.
	 * @param param CmUserVo
	 * @return int
	 * @exception DataAccessException
	 */
	@Override
	public int ecmpUpdatePswd(CmUserVo param){
		int insertRowCnt = 0;
		CmUserVo dncryptVo = (CmUserVo) RsaUtil.getDncryptVo(param);

    	if(!StringUtil.checkExp("userPswd", dncryptVo.getUserPswd1())){
			LOG.error("패스워드를 확인하세요.");
			throw new SicimsException("입력한 패스워드 값을 확인하세요.");
		} else if(!StringUtil.checkExp("userPswd", dncryptVo.getUserPswd2())){
			LOG.error("패스워드를 확인하세요.");
			throw new SicimsException("입력한 패스워드확인 값을 확인하세요.");
		} else if(!dncryptVo.getUserPswd1().equals(dncryptVo.getUserPswd2())){
			LOG.error("패스워드와 패스워드 확인 값이 일치하지 않습니다.");
			throw new SicimsException("패스워드와 패스워드 확인 값이 일치하지 않습니다.");
		}

		//패스워드 암호화
		try {
			String nowPass = EgovFileScrty.encryptPassword(dncryptVo.getUserPswd(), dncryptVo.getUserId());
			dncryptVo.setUserPswd(nowPass);

			// 2. 아이디와 암호화된 비밀번호가 DB와 일치하는지 확인한다.
			CmUserVo cmUserVoCheck = loginDao.getLoginInfo(dncryptVo);

			if(cmUserVoCheck == null || cmUserVoCheck.getUserId() == null || "".equals( cmUserVoCheck.getUserId())){
				LOG.error("현재 패스워드 값이 일치하지 않습니다.");
				throw new SicimsException("현재 패스워드 값이 일치하지 않습니다.");
			}

			String encryptPass = EgovFileScrty.encryptPassword(dncryptVo.getUserPswd1(), dncryptVo.getUserId());
			dncryptVo.setUserPswd(encryptPass);

			insertRowCnt = loginDao.ecmpUpdatePswd(dncryptVo);
		} catch (NoSuchAlgorithmException e) {
			LOG.error("패스워드 암호화중 오류가 발생했습니다.");
			throw new SicimsException("패스워드 암호화중 오류가 발생했습니다.");
		}

    	return insertRowCnt;
    }



	/**
	 * 사용자 패스워드를  수정한다.
	 * @param param CmUserVo
	 * @return int
	 * @exception DataAccessException
	 */
	@Override
	public int updateCmUserPswd(CmUserVo param){
		int insertRowCnt = 0;
		CmUserVo dncryptVo = (CmUserVo) RsaUtil.getDncryptVo(param);
    	//dncryptVo.setUserId(SessionUtil.getLoginUserId());

    	if(dncryptVo.getUserId() == null || "".equals(dncryptVo.getUserId())){
    		LOG.error("수정할 로그인 아이디가 없습니다.");
			throw new SicimsException("수정할 로그인 아이디가 없습니다. 로그인 상태인지 확인하세요.");
    	} else if(!dncryptVo.getUserId().equals(SessionUtil.getLoginUserId())){
    		LOG.error("수정할 회원정보와 로그인 아이디가 일치하지 않습니다.");
			throw new SicimsException("자신의 회원정보만 수정할 수 있습니다.");
    	} else if(!StringUtil.checkExp("userPswd", dncryptVo.getUserPswd1())){
			LOG.error("패스워드를 확인하세요.");
			throw new SicimsException("입력한 패스워드 값을 확인하세요.");
		} else if(!StringUtil.checkExp("userPswd", dncryptVo.getUserPswd2())){
			LOG.error("패스워드를 확인하세요.");
			throw new SicimsException("입력한 패스워드확인 값을 확인하세요.");
		} else if(!dncryptVo.getUserPswd1().equals(dncryptVo.getUserPswd2())){
			LOG.error("패스워드와 패스워드 확인 값이 일치하지 않습니다.");
			throw new SicimsException("패스워드와 패스워드 확인 값이 일치하지 않습니다.");
		}

		//패스워드 암호화
		try {
			String nowPass = EgovFileScrty.encryptPassword(dncryptVo.getUserPswd(), dncryptVo.getUserId());
			dncryptVo.setUserPswd(nowPass);

			// 2. 아이디와 암호화된 비밀번호가 DB와 일치하는지 확인한다.
			CmUserVo cmUserVoCheck = loginDao.getLoginInfo(dncryptVo);

			if(cmUserVoCheck == null || cmUserVoCheck.getUserId() == null || "".equals( cmUserVoCheck.getUserId())){
				LOG.error("현재 패스워드 값이 일치하지 않습니다.");
				throw new SicimsException("현재 패스워드 값이 일치하지 않습니다.");
			}

			String encryptPass = EgovFileScrty.encryptPassword(dncryptVo.getUserPswd1(), dncryptVo.getUserId());
			dncryptVo.setUserPswd(encryptPass);

			insertRowCnt = loginDao.updateCmUserPswd(dncryptVo);
		} catch (NoSuchAlgorithmException e) {
			LOG.error("패스워드 암호화중 오류가 발생했습니다.");
			throw new SicimsException("패스워드 암호화중 오류가 발생했습니다.");
		}

    	return insertRowCnt;
    }



	/**
	 * 사용자 정보를  삭제한다.
	 * @param param CmUserVo
	 * @return int
	 * @exception DataAccessException
	 */
	@Override
	public int deleteCmUser(CmUserVo param) {
    	int deleteRowCnt = 0;
    	CmUserVo dncryptVo = (CmUserVo) RsaUtil.getDncryptVo(param);
      	dncryptVo.setUserId(SessionUtil.getLoginUserId());

      	if(dncryptVo.getUserId() == null || "".equals(dncryptVo.getUserId())){
      		LOG.error("수정할 로그인 아이디가 없습니다.");
  			throw new SicimsException("수정할 로그인 아이디가 없습니다.");
      	}
      	if(!dncryptVo.getUserId().equals(SessionUtil.getLoginUserId())){
      		LOG.error("수정할 회원정보와 로그인 아이디가 일치하지 않습니다.");
  			throw new SicimsException("자신의 회원정보만 수정할 수 있습니다.");
      	}

      	deleteRowCnt = loginDao.deleteCmUser(dncryptVo);

      	return deleteRowCnt;
      }


	/**
	 * 인사대체키로 사용자ID를 찾는다.
	 * @param param HashMap<String,Object>
	 * @return HashMap<String,Object>
	 */
	@Override
	public HashMap<String,Object> getUserId(HashMap<String,Object> param)  {
		HashMap<String,Object> result = new HashMap<String, Object>();

		try {
			CmUserVo dmUserVo = loginDao.getUserId(param);
			if(dmUserVo == null || dmUserVo.getUserId() == null || "".equals(dmUserVo.getUserId())){
				result.put("status", 	"fail");
				result.put("resultMsg", "일치하는 아이디가 없습니다.<br/><br/>인사대체키와 사용자명을 정확히 입력한 후 진행해 주세요.");
			} else {
				result.put("status", 	"success");
				result.put("resultMsg", param.get("userNm") + " 회원님의 인사대체키("+ param.get("hrscRplcKey") + ")로 찾은 아이디 입니다.<br/><br/><b>" + dmUserVo.getUserId() +"</b>" );
			}
		} catch (DataAccessException e) {
			result.put("status", 	"fail");
			result.put("resultMsg", "일치하는 아이디가 없습니다.<br/><br/>인사대체키와 사용자명을 정확히 입력한 후 진행해 주세요.");
		}


    	return result;
	}




}
