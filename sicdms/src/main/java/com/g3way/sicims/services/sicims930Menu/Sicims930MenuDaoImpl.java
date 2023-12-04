package com.g3way.sicims.services.sicims930Menu;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.g3way.sicims.mybatis.mappers.Sicims930MenuMapper;
import com.g3way.sicims.services.sicims930Menu.vo.CmMenuVo;


@Repository
public class Sicims930MenuDaoImpl implements Sicims930MenuDao {
	@Resource(name = "sicims930MenuMapper")
	private Sicims930MenuMapper sicims930MenuMapper;

	/********************************/
	/*		1. 메뉴접속로그 관리			*/
	/********************************/
	/**
	 * 메뉴접속로그 정보를  등록한다.
	 * @param param CmMenuVo
	 * @return int
	 * @exception DataAccessException
	 */
	@Override
	public int insertCmMenu(CmMenuVo param) {
		return sicims930MenuMapper.insertCmMenu(param);
	}


	/**
	 * 페이지 단위로 메뉴접속로그 목록을 조회한다.
	 * @param param HashMap<String, Object>
	 * @return List<CmMenuVo>
	 */
	@Override
	public List<CmMenuVo> selectCmMenuList(HashMap<String, Object> param){
		return sicims930MenuMapper.selectCmMenuList(param);
	}


	/**
	 * 월별 메뉴접속 현황을 조회한다.
	 * @param param HashMap<String, Object>
	 * @return List<HashMap<String, Object>>
	 */
	@Override
	public List<HashMap<String, Object>> getCmMenuStatList(HashMap<String, Object> param){
		return sicims930MenuMapper.getCmMenuStatList(param);
	}


}
