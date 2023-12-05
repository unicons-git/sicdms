package com.g3way.sicims.services.sicims900Sys.login;


import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.g3way.sicims.mybatis.mappers.LoginMapper;
import com.g3way.sicims.services.sicims910User.vo.CmUserVo;

@Repository("loginDao")
public class LoginDaoImpl implements LoginDao {

	@Resource(name = "loginMapper")
	private LoginMapper loginMapper;

	/********************************/
	/*		1. 사용자 관리				*/
	/********************************/
	/**
	 * 사용자 정보를 조회한다.
	 * @param param CmUserVo
	 * @return LoginVo
	 */
	@Override
	public CmUserVo getLoginInfo(CmUserVo param){
    	return loginMapper.getLoginInfo(param);
    }


	/**
	 * 사용자 아이디에 해당 권한 부여되어 있는지 조회한다.
	 * @param param HashMap<String, Object>
	 * @return int
	 */
	@Override
	public int getCmRhrcInfo(HashMap<String, Object> param){
    	return loginMapper.getCmRhrcInfo(param);
    }


	/**
	 * 사용자 로그인 실패시 패스워드 오류를 증가한다
	 * @param param CmUserVo
	 * @return int
	 * @exception Exception
	 */
	@Override
	public int updateLoginFail(CmUserVo param){
    	return loginMapper.updateLoginFail(param);
    }


	/**
	 * 사용자 로그인 성공시 마지막로그인일자와 패스워드 오류를 수정한다.
	 * @param param CmUserVo
	 * @return int
	 */
	@Override
	public int updateLoginSuccess(CmUserVo param){
    	return loginMapper.updateLoginSuccess(param);
    }


	/**
	 * 사용자 정보를  등록한다.
	 * @param param CmUserVo
	 * @return int
	 * @exception DataAccessException
	 */
	@Override
	public int insertCmUser(CmUserVo param){
    	return loginMapper.insertCmUser(param);
    }


	/**
	 * 사용자 정보를  수정한다.
	 * @param param CmUserVo
	 * @return int
	 * @exception DataAccessException
	 */
	@Override
	public int updateCmUser(CmUserVo param){
    	return loginMapper.updateCmUser(param);
    }



	/**
	 * 사용자 패스워드를  수정한다.
	 * @param param CmUserVo
	 * @return int
	 * @exception DataAccessException
	 */
	@Override
	public int updatePswd(CmUserVo param){
    	return loginMapper.updatePswd(param);
    }


	/**
	 * 외부사용자 패스워드를  수정한다.
	 * @param param CmUserVo
	 * @return int
	 * @exception DataAccessException
	 */
	@Override
	public int ecmpUpdatePswd(CmUserVo param){
    	return loginMapper.ecmpUpdatePswd(param);
    }


	/**
	 * 사용자 패스워드를  수정한다.
	 * @param param CmUserVo
	 * @return int
	 * @exception DataAccessException
	 */
	@Override
	public int updateCmUserPswd(CmUserVo param){
    	return loginMapper.updateCmUserPswd(param);
    }



	/**
	 * 사용자 정보를  삭제한다.
	 * @param param CmUserVo
	 * @return int
	 * @exception DataAccessException
	 */
	@Override
	public int deleteCmUser(CmUserVo param){
    	return loginMapper.deleteCmUser(param);
    }


	/**
	 * 인사대체키로 사용자ID를 찾는다.
	 * @param param HashMap<String,Object>
	 * @return CmUserVo
	 */
	@Override
	public CmUserVo getUserId(HashMap<String,Object> param){
    	return loginMapper.getUserId(param);
    }



}
