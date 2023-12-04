package com.g3way.sicims.services.sicims910User;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.g3way.sicims.mybatis.mappers.Sicims910UserMapper;
import com.g3way.sicims.services.sicims910User.vo.CmUserVo;



@Repository
public class Sicims910UserDaoImpl implements Sicims910UserDao {
	@Resource(name = "sicims910UserMapper")
	private Sicims910UserMapper sicims910UserMapper;


	/********************************/
	/*		1. 사용자 관리				*/
	/********************************/
	/**
	 * 사용자 목록을 조회한다.
	 * @param param HashMap<String, Object>
	 * @return List<CmUserVo>
	 */
	@Override
	public List<CmUserVo> getCmUserList(HashMap<String, Object> param){
		return sicims910UserMapper.getCmUserList(param);
	}

	/**
	 * 페이지 단위로 사용자 목록을 조회한다.
	 * @param param HashMap<String, Object>
	 * @return List<CmUserVo>
	 */
	@Override
	public List<CmUserVo> selectCmUserList(HashMap<String, Object> param){
		return sicims910UserMapper.selectCmUserList(param);
	}


	/**
	 * 사용자 정보를 조회한다.
	 * @param param HashMap<String, Object>
	 * @return CmUserVo
	 */
	@Override
	public CmUserVo getCmUserInfo(HashMap<String, Object> param) {
		return sicims910UserMapper.getCmUserInfo(param);
	}


	/**
	 * 사용자 정보를  등록한다.
	 * @param cmUserVo CmUserVo
	 * @return int
	 */
	@Override
	public int insertCmUser(CmUserVo cmUserVo)  {
		return sicims910UserMapper.insertCmUser(cmUserVo);
	}

	/**
	 * 사용자 정보를  수정한다.
	 * @param cmUserVo CmUserVo
	 * @return int
	 */
	@Override
	public int updateCmUser(CmUserVo cmUserVo) {
		return sicims910UserMapper.updateCmUser(cmUserVo);
	}

	/**
	 * 비밀번호를 초기화한다.
	 * @param param HashMap<String, Object>
	 * @return int
	 */
	@Override
	public int resetPassword(HashMap<String, Object> param){
		return sicims910UserMapper.resetPassword(param);
	}


	/**
	 * 사용자 정보를 삭제한다.
	 * @param param HashMap<String, Object>
	 * @return int
	 */
	@Override
	public int deleteCmUser(HashMap<String, Object> param) {
		return sicims910UserMapper.deleteCmUser(param);
	}


	/**
	 * 사용자 부여 권한이 있는경우에는 업데이트하고, 없는경우에는 신규로 등록한다.
	 * @param param HashMap<String, Object>
	 * @return int
	 */
	@Override
	public int saveCmScrt(HashMap<String, Object> param){
		return sicims910UserMapper.saveCmScrt(param);
	}


	/**
	 * 사용자 권한 정보를 삭제한다.
	 * @param param HashMap<String, Object>
	 * @return int
	 */
	@Override
	public int deleteCmScrt(HashMap<String, Object> param){
		return sicims910UserMapper.deleteCmScrt(param);
	}


	/**
	 * 사용자 승인 정보를  수정한다.
	 * @param cmUserVo CmUserVo
	 * @return int
	 */
	@Override
	public int updateCmUserApprove(CmUserVo cmUserVo) {
		return sicims910UserMapper.updateCmUserApprove(cmUserVo);
	}

}
