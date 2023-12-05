package com.g3way.sicims.mybatis.mappers;


import java.util.HashMap;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.g3way.sicims.services.sicims930Menu.vo.CmMenuVo;

import egovframework.rte.psl.dataaccess.mapper.Mapper;


/***
 * 메뉴접속로그 관리 매퍼(Mapper)
 * @author dykim
 * @since 2023.07
 * @version 1.0.0
 */
@Mapper("sicims930MenuMapper")
public interface Sicims930MenuMapper {
	/********************************/
	/*		1. 메뉴접속로그 관리			*/
	/********************************/
	/**
	 * 메뉴접속로그 정보를  등록한다.
	 * @param param CmMenuVo
	 * @return int
	 * @exception DataAccessException
	 */
	public int insertCmMenu(CmMenuVo param) throws DataAccessException;


	/**
	 * 페이지 단위로 메뉴접속로그 목록을 조회한다.
	 * @param param HashMap<String, Object>
	 * @return List<CmMenuVo>
	 */
	public List<CmMenuVo> selectCmMenuList(HashMap<String, Object> param) throws DataAccessException;


	/**
	 * 월별 메뉴접속 현황을 조회한다.
	 * @param param HashMap<String, Object>
	 * @return List<HashMap<String, Object>>
	 */
	public List<HashMap<String, Object>> getCmMenuStatList(HashMap<String, Object> param) throws DataAccessException;

}
