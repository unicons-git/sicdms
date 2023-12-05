package com.g3way.sicims.services.sicims910User;

import java.util.HashMap;
import java.util.List;

import com.g3way.sicims.services.sicims910User.vo.CmUserVo;


public interface Sicims910UserDao {

	/********************************/
	/*		1. 사용자 관리				*/
	/********************************/
	/**
	 * 사용자 목록을 조회한다.
	 * @param param HashMap<String, Object>
	 * @return List<CmUserVo>
	 */
	public List<CmUserVo> getCmUserList(HashMap<String, Object> param);

	/**
	 * 페이지 단위로 사용자 목록을 조회한다.
	 * @param param HashMap<String, Object>
	 * @return List<CmUserVo>
	 */
	public List<CmUserVo> selectCmUserList(HashMap<String, Object> param);


	/**
	 * 사용자 정보를 조회한다.
	 * @param param HashMap<String, Object>
	 * @return CmUserVo
	 */
	public CmUserVo getCmUserInfo(HashMap<String, Object> param);


	/**
	 * 사용자 정보를  등록한다.
	 * @param cmUserVo CmUserVo
	 * @return int
	 */
	public int insertCmUser(CmUserVo cmUserVo);

	/**
	 * 사용자 정보를  수정한다.
	 * @param cmUserVo CmUserVo
	 * @return int
	 */
	public int updateCmUser(CmUserVo cmUserVo);


	/**
	 * 비밀번호를 초기화한다.
	 * @param param HashMap<String, Object>
	 * @return int
	 */
	public int resetPassword(HashMap<String, Object> param);


	/**
	 * 사용자 정보를 삭제한다.
	 * @param param HashMap<String, Object>
	 * @return int
	 */
	public int deleteCmUser(HashMap<String, Object> param);


	/**
	 * 사용자 부여 권한이 있는경우에는 업데이트하고, 없는경우에는 신규로 등록한다.
	 * @param param HashMap<String, Object>
	 * @return int
	 */
	public int saveCmScrt(HashMap<String, Object> param);


	/**
	 * 사용자 권한 정보를 삭제한다.
	 * @param param HashMap<String, Object>
	 * @return int
	 */
	public int deleteCmScrt(HashMap<String, Object> param);


	/**
	 * 사용자 승인 정보를  수정한다.
	 * @param cmUserVo CmUserVo
	 * @return int
	 */
	public int updateCmUserApprove(CmUserVo cmUserVo);
}
