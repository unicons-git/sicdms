package com.g3way.sicims.mybatis.mappers;


import java.util.HashMap;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.g3way.sicims.services.sicims900Sys.log.vo.CmLogVo;
import com.g3way.sicims.services.sicims910User.vo.CmUserVo;

import egovframework.rte.psl.dataaccess.mapper.Mapper;


@Mapper("logMapper")
public interface LogMapper {

	/**
	 * 접속로그 정보를  등록한다.
	 * @param param CmUserVo
	 * @return int
	 * @exception DataAccessException
	 */
	public int insertCmAlog(CmUserVo param) throws DataAccessException;


	/**
	 * 웹접속로그 정보를  등록한다.
	 * @param param CmLogVo
	 * @return int
	 * @exception DataAccessException
	 */
	public int insertCmWlog(CmLogVo param) throws DataAccessException;


	/**
	 * 데이터 수정 로그를 등록한다.
	 * @param param CmLogVo
	 * @return int
	 * @exception DataAccessException
	 */
	public int insertCmMlog(CmLogVo param) throws DataAccessException;


	/**
	 *데이터 수정 상세  로그를 등록한다.
	 * @param param CmLogVo
	 * @return int
	 * @exception DataAccessException
	 */
	public int insertCmSlog(CmLogVo param) throws DataAccessException;


	/**
	 * 테이블의  수정 전후 정보를 조회한다.
	 * @param param HashMap<String, Object>
	 * @return List<HashMap<String, Object>>
	 * @exception DataAccessException
	 */
	public List<HashMap<String, Object>> getTblDataList(HashMap<String, Object> param) throws DataAccessException;
}
