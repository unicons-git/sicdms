package com.g3way.sicims.mybatis.mappers;

import java.util.HashMap;

import org.springframework.dao.DataAccessException;

import com.g3way.sicims.services.sicims910User.vo.CmUserVo;

import egovframework.rte.psl.dataaccess.mapper.Mapper;


@Mapper("loginMapper")
public interface LoginMapper {

	/********************************/
	/*		1. 사용자 관리			*/
	/********************************/

	/**
	 * 사용자 정보를 조회한다.
	 * @param param CmUserVo
	 * @return CmUserVo
	 * @exception Exception
	 */
	public CmUserVo getLoginInfo(CmUserVo param) throws DataAccessException;


	/**
	 * 사용자 아이디에 해당 권한 부여되어 있는지 조회한다.
	 * @param param HashMap<String, Object>
	 * @return int
	 * @exception Exception
	 */
	public int getCmRhrcInfo(HashMap<String, Object> param) throws DataAccessException;


	/**
	 * 사용자 로그인 실패시 패스워드 오류를 증가한다
	 * @param param CmUserVo
	 * @return int
	 * @exception Exception
	 */
	public int updateLoginFail(CmUserVo param) throws DataAccessException;

	/**
	 * 사용자 로그인 성공시 마지막로그인일자와 패스워드 오류를 수정한다.
	 * @param param CmUserVo
	 * @return int
	 * @exception Exception
	 */
	public int updateLoginSuccess(CmUserVo param) throws DataAccessException;


	/**
	 * 사용자 정보를  등록한다.
	 * @param param CmUserVo
	 * @return int
	 * @exception DataAccessException
	 */
	public int insertCmUser(CmUserVo param) throws DataAccessException;


	/**
	 * 사용자 정보를  수정한다.
	 * @param param CmUserVo
	 * @return int
	 * @exception DataAccessException
	 */
	public int updateCmUser(CmUserVo param) throws DataAccessException;


	/**
	 * 사용자 패스워드를  수정한다.
	 * @param param CmUserVo
	 * @return int
	 * @exception DataAccessException
	 */
	public int updatePswd(CmUserVo param) throws DataAccessException;


	/**
	 * 외부사용자 패스워드를  수정한다.
	 * @param param CmUserVo
	 * @return int
	 * @exception DataAccessException
	 */
	public int ecmpUpdatePswd(CmUserVo param) throws DataAccessException;



	/**
	 * 사용자 패스워드를  수정한다.
	 * @param param CmUserVo
	 * @return int
	 * @exception DataAccessException
	 */
	public int updateCmUserPswd(CmUserVo param) throws DataAccessException;


	/**
	 * 사용자 정보를  삭제한다.
	 * @param param CmUserVo
	 * @return int
	 * @exception DataAccessException
	 */
	public int deleteCmUser(CmUserVo param) throws DataAccessException;



	/**
	 * 인사대체키로 사용자ID를 찾는다.
	 * @param param HashMap<String,Object>
	 * @return LoginVo
	 * @exception DataAccessException
	 */
	public CmUserVo getUserId(HashMap<String,Object> param) throws DataAccessException;

}