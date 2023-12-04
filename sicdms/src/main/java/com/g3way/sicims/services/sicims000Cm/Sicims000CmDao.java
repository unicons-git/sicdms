package com.g3way.sicims.services.sicims000Cm;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import com.g3way.sicims.services.sicims000Cm.vo.CmCodeVo;
import com.g3way.sicims.services.sicims000Cm.vo.CmFileVo;



public interface Sicims000CmDao {
	/********************************/
	/*		0. 공통정보					*/
	/********************************/
	/**
	 * 시스템의 년도를 조회한다.
	 * @param param HashMap<String, Object>
	 * @return String
	 */
	public String getCmnnYear(HashMap<String, Object> param);



	/********************************/
	/*		1. 공통코드 관리				*/
	/********************************/
	/**
	 * 공통코드 목록을 조회한다.
	 * @param  param HashMap<String, Object>
	 * @return List<CmCodeVo>
	 */
	public List<CmCodeVo> getCmCodeList(HashMap<String, Object> param);




	/********************************/
	/*		2. 기관 목록 조회				*/
	/********************************/
	/**
	 * 기관코드 목록을 조회한다.
	 * @param  param HashMap<String, Object>
	 * @return List<String>
	 */
	public List<String> getInstCdList(HashMap<String, Object> param);


	/**
	 * 기관1 목록을 조회한다.
	 * @param  param HashMap<String, Object>
	 * @return List<HashMap<String, Object>>
	 */
	public List<HashMap<String, Object>> getInst1CdList(HashMap<String, Object> param);


	/**
	 * 기관2 목록을 조회한다.
	 * @param  param HashMap<String, Object>
	 * @return List<HashMap<String, Object>>
	 */
	public List<HashMap<String, Object>> getInst2CdList(HashMap<String, Object> param);




	/********************************/
	/*		3. 공통 파일 관리				*/
	/********************************/

	/**
	 * 파일정보를 조회한다.
	 * @param fileId String
	 * @return CmFileVo
	 */
	public CmFileVo getCmFileInfo(BigDecimal fileId);

	/**
	 * 파일 정보를 등록한다.
	 * @param param CmFileVo
	 * @return int
	 */
	public int insertCmFile(CmFileVo param);

	/**
	 * 파일 정보를 수정한다.
	 * @param param CmFileVo
	 * @return int
	 */
	public int updateCmFile(CmFileVo param);

	/**
	 * 파일 정보를 삭제한다.
	 * @param fileId String
	 * @return int
	 */
	public int deleteCmFile(BigDecimal fileId);



	/********************************/
	/*		99. 메타파일 정보 조회			*/
	/********************************/
	/**
	 * 메타파일 정보를 조회한다.
	 * @param  gubun String
	 * @return List<HashMap<String, Object>>
	 */
	public List<HashMap<String, Object>> getColumnMetaList(HashMap<String, Object> params);

}
