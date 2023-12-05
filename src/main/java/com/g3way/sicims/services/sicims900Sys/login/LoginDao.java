package com.g3way.sicims.services.sicims900Sys.login;

import java.util.HashMap;

import org.springframework.dao.DataAccessException;

import com.g3way.sicims.services.sicims910User.vo.CmUserVo;

/**
 * 일반 로그인, 인증서 로그인을 처리하는 DAO 클래스
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
 *  </pre>
 */

public interface LoginDao {
	/**
	 * 사용자 정보를 조회한다.
	 * @param param CmUserVo
	 * @return LoginVo
	 */
	public CmUserVo getLoginInfo(CmUserVo param);


	/**
	 * 사용자 아이디에 해당 권한 부여되어 있는지 조회한다.
	 * @param param HashMap<String, Object>
	 * @return int
	 */
	public int getCmRhrcInfo(HashMap<String, Object> param);


	/**
	 * 사용자 로그인 실패시 패스워드 오류를 증가한다
	 * @param param CmUserVo
	 * @return int
	 */
	public int updateLoginFail(CmUserVo param);


	/**
	 * 사용자 로그인 성공시 마지막로그인일자와 패스워드 오류를 수정한다.
	 * @param param CmUserVo
	 * @return int
	 */
	public int updateLoginSuccess(CmUserVo param);


	/**
	 * 사용자 정보를  등록한다.
	 * @param param CmUserVo
	 * @return int
	 * @exception DataAccessException
	 */
	public int insertCmUser(CmUserVo param);


	/**
	 * 사용자 정보를  수정한다.
	 * @param param CmUserVo
	 * @return int
	 * @exception DataAccessException
	 */
	public int updateCmUser(CmUserVo param);


	/**
	 * 사용자 패스워드를  수정한다.
	 * @param param CmUserVo
	 * @return int
	 * @exception DataAccessException
	 */
	public int updatePswd(CmUserVo param);


	/**
	 * 외부사용자 패스워드를  수정한다.
	 * @param param CmUserVo
	 * @return int
	 * @exception DataAccessException
	 */
	public int ecmpUpdatePswd(CmUserVo param);


	/**
	 * 사용자 패스워드를  수정한다.
	 * @param param CmUserVo
	 * @return int
	 * @exception DataAccessException
	 */
	public int updateCmUserPswd(CmUserVo param);


	/**
	 * 사용자 정보를  삭제한다.
	 * @param param CmUserVo
	 * @return int
	 * @exception DataAccessException
	 */
	public int deleteCmUser(CmUserVo param);



	/**
	 * 인사대체키로 사용자ID를 찾는다.
	 * @param param HashMap<String,Object>
	 * @return CmUserVo
	 */
	public CmUserVo getUserId(HashMap<String,Object> param) ;



}
