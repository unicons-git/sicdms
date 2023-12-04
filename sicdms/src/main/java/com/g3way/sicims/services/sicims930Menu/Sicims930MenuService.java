package com.g3way.sicims.services.sicims930Menu;

import java.util.HashMap;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.g3way.sicims.services.sicims930Menu.vo.CmMenuVo;


public interface Sicims930MenuService {
	/********************************/
	/*		1. 메뉴접속로그 관리			*/
	/********************************/
	/**
	 * 메뉴접속로그 정보를  등록한다.
	 * @param String mmenuSe
	 * @param String smenuSe
	 * @return int
	 * @exception DataAccessException
	 */
	public int insertCmMenu(String mmenuSe, String smenuSe);


	/**
	 * 페이지 단위로 메뉴접속로그 목록을 조회한다.
	 * @param param HashMap<String, Object>
	 * @return List<CmMenuVo>
	 */
	public List<CmMenuVo> selectCmMenuList(HashMap<String, Object> param);


	/**
	 * 월별 메뉴접속 현황을 조회한다.
	 * @param param HashMap<String, Object>
	 * @return List<HashMap<String, Object>>
	 */
	public List<HashMap<String, Object>> getCmMenuStatList(HashMap<String, Object> param);

}