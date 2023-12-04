package com.g3way.sicims.services.sicims000Cm;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.g3way.sicims.mybatis.mappers.Sicims000CmMapper;
import com.g3way.sicims.services.sicims000Cm.vo.CmCodeVo;
import com.g3way.sicims.services.sicims000Cm.vo.CmFileVo;

@Repository
public class Sicims000CmDaoImpl implements Sicims000CmDao {

	@Resource(name = "sicims000CmMapper")
	private Sicims000CmMapper sicims000CmMapper;


	/********************************/
	/*		0. 공통정보					*/
	/********************************/
	/**
	 * 시스템의 년도를 조회한다.
	 * @param param HashMap<String, Object>
	 * @return String
	 */
	@Override
	public String getCmnnYear(HashMap<String, Object> param){
		return sicims000CmMapper.getCmnnYear(param);
	}



	/********************************/
	/*		1. 공통코드 관리				*/
	/********************************/
	/**
	 * 공통코드 목록을 조회한다.
	 * @param  param HashMap<String, Object>
	 * @return List<CmCodeVo>
	 */
	@Override
	public List<CmCodeVo> getCmCodeList(HashMap<String, Object> param) {
		return sicims000CmMapper.getCmCodeList(param);
	}




	/********************************/
	/*		2. 기관 목록 조회				*/
	/********************************/
	/**
	 * 기관코드 목록을 조회한다.
	 * @param  param HashMap<String, Object>
	 * @return List<String>
	 */
	public List<String> getInstCdList(HashMap<String, Object> param){
		return sicims000CmMapper.getInstCdList(param);
	}


	/**
	 * 기관1 목록을 조회한다.
	 * @param  gubun String
	 * @return List<CmMainVo>
	 */
	@Override
	public List<HashMap<String, Object>> getInst1CdList(HashMap<String, Object> param){
		return sicims000CmMapper.getInst1CdList(param);
	}

	/**
	 * 기관2 목록을 조회한다.
	 * @param  gubun String
	 * @return List<CmMainVo>
	 */
	@Override
	public List<HashMap<String, Object>> getInst2CdList(HashMap<String, Object> param){
		return sicims000CmMapper.getInst2CdList(param);
	}





	/********************************/
	/*		3. 공통 파일 관리				*/
	/********************************/
	/**
	 * 파일정보를 조회한다.
	 * @param fileSeq String
	 * @return CmFileVo
	 */
	@Override
	public CmFileVo getCmFileInfo(BigDecimal fileId) {
		return sicims000CmMapper.getCmFileInfo(fileId);
	}


	/**
	 * 파일 정보를 등록한다.
	 * @param param CmFileVo
	 * @return int
	 */
	@Override
	public int insertCmFile(CmFileVo param){
		return sicims000CmMapper.insertCmFile(param);
	}

	/**
	 * 파일 정보를 수정한다.
	 * @param param CmFileVo
	 * @return int
	 */
	@Override
	public int updateCmFile(CmFileVo param){
		return sicims000CmMapper.updateCmFile(param);
	}

	/**
	 * 파일 정보를 삭제한다.
	 * @param fileSeq String
	 * @return int
	 */
	@Override
	public int deleteCmFile(BigDecimal fileId){
		return sicims000CmMapper.deleteCmFile(fileId);
	}



	/********************************/
	/*		99. 메타파일 정보 조회			*/
	/********************************/
	/**
	 * 메타파일 정보를 조회한다.
	 * @param  gubun String
	 * @return List<HashMap<String, Object>>
	 */
	@Override
	public List<HashMap<String, Object>> getColumnMetaList(HashMap<String, Object> params){
		return sicims000CmMapper.getColumnMetaList(params);
	}
}
