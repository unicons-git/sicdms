package com.g3way.sicims.mybatis.mappers;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.g3way.sicims.services.sicims000Cm.vo.CmCodeVo;
import com.g3way.sicims.services.sicims000Cm.vo.CmFileVo;

import egovframework.rte.psl.dataaccess.mapper.Mapper;



@Mapper("sicims000CmMapper")
public interface Sicims000CmMapper {
	/********************************/
	/*		0. 공통정보					*/
	/********************************/
	/**
	 * 시스템의 년도를 조회한다.
	 * @param param HashMap<String, Object>
	 * @return String
	 */
	public String getCmnnYear(HashMap<String, Object> param) throws DataAccessException;




	/********************************/
	/*		1. 공통코드 관리				*/
	/********************************/
	/**
	 * 공통코드 목록을 조회한다.
	 * @param  param HashMap<String, Object>
	 * @return List<CmCodeVo>
	 */
	public List<CmCodeVo> getCmCodeList(HashMap<String, Object> param) throws DataAccessException;




	/********************************/
	/*		2. 기관 목록 조회				*/
	/********************************/
	/**
	 * 기관코드 목록을 조회한다.
	 * @param  param HashMap<String, Object>
	 * @return List<String>
	 */
	public List<String> getInstCdList(HashMap<String, Object> param) throws DataAccessException;


	/**
	 * 기관1 목록을 조회한다.
	 * @param  param HashMap<String, Object>
	 * @return List<HashMap<String, Object>>
	 */
	public List<HashMap<String, Object>> getInst1CdList(HashMap<String, Object> param) throws DataAccessException;


	/**
	 * 기관2 목록을 조회한다.
	 * @param  param HashMap<String, Object>
	 * @return List<HashMap<String, Object>>
	 */
	public List<HashMap<String, Object>> getInst2CdList(HashMap<String, Object> param) throws DataAccessException;




	/********************************/
	/*		3. 공통 파일 관리				*/
	/********************************/
	/**
	 * 파일정보를 조회한다.
	 * @param fileId BigDecimal
	 * @return CmFileVo
	 */
	public CmFileVo getCmFileInfo(BigDecimal fileId)  throws DataAccessException;


	/**
	 * 파일 정보를 등록한다.
	 * @param param CmFileVo
	 * @return int
	 */
	public int insertCmFile(CmFileVo param)  throws DataAccessException;

	/**
	 * 파일 정보를 수정한다.
	 * @param param CmFileVo
	 * @return int
	 */
	public int updateCmFile(CmFileVo param)  throws DataAccessException;

	/**
	 * 파일 정보를 삭제한다.
	 * @param fileSeq String
	 * @return int
	 */
	public int deleteCmFile(BigDecimal fileSn)  throws DataAccessException;




	/********************************/
	/*		99. 메타파일 정보 조회			*/
	/********************************/
	/**
	 * 메타파일 정보를 조회한다.
	 * @param  gubun String
	 * @return List<HashMap<String, Object>>
	 */
	public List<HashMap<String, Object>> getColumnMetaList(HashMap<String, Object> params) throws DataAccessException;


}