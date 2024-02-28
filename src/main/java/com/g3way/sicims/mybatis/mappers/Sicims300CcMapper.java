package com.g3way.sicims.mybatis.mappers;

import java.util.HashMap;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.g3way.sicims.services.sicims300Cc.vo.CcCsexVo;
import com.g3way.sicims.services.sicims300Cc.vo.CcCsmaVo;
import com.g3way.sicims.services.sicims300Cc.vo.CcCsrsVo;
import com.g3way.sicims.services.sicims300Cc.vo.CcIsrdVo;
import com.g3way.sicims.services.sicims300Cc.vo.CcIsrrVo;
import com.g3way.sicims.services.sicims300Cc.vo.CcMailVo;
import com.g3way.sicims.services.sicims300Cc.vo.CcVcadVo;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
/*
 * 민원관리
 */

@Mapper("sicims300CcMapper")
public interface Sicims300CcMapper {

	/********************************/
	/*	1. 민원관리-건설업등록기준사전조사 자료관리	*/
	/*	1.1 건설업등록기준사전조사 정보			*/
	/********************************/
	/**
	 * 페이지 단위로 건설업등록기준사전조사 목록을 조회한다.
	 * @param param HashMap<String, Object>
	 * @return List<CcCsmaVo>
	 */
	public List<CcCsmaVo> selectCcCsmaList(HashMap<String, Object> param) throws DataAccessException;


	/**
	 * 건설업등록기준사전조사 정보를  조회한다.
	 * @param param HashMap<String, Object>
	 * @return CcCsmaVo
	 */
	public CcCsmaVo getCcCsmaInfo(HashMap<String, Object> param) throws DataAccessException;


	/**
	 * 건설업등록기준사전조사 정보를  등록한다.
	 * @param ccCsmaVo CcCsmaVo
	 * @return int
	 */
	public int insertCcCsma(CcCsmaVo ccCsmaVo) throws DataAccessException;


	/**
	 * 건설업등록기준사전조사 정보를  수정한다.
	 * @param ccCsmaVo CcCsmaVo
	 * @return int
	 */
	public int updateCcCsma(CcCsmaVo ccCsmaVo) throws DataAccessException;


	/**
	 * 건설업등록기준사전조사 제출상태 정보를  수정한다.
	 * @param param HashMap<String, Object>
	 * @return int
	 */
	public int updateCcCsmaSbmsn(HashMap<String, Object> param) throws DataAccessException;


	/**
	 * 업체담당자비밀번호를 변경한다.
	 * @param ccCsmaVo CcCsmaVo
	 * @return int
	 */
	public int updateCcCsmaPswd(CcCsmaVo ccCsmaVo) throws DataAccessException;


	/**
	 * 건설업등록기준사전조사 정보를 삭제한다.
	 * @param param HashMap<String, Object>
	 * @return int
	 */
	public int deleteCcCsma(HashMap<String, Object> param) throws DataAccessException;



	/********************************/
	/*	1.2 민원관리-건설업등록기준사전조사결과 정보	*/
	/********************************/
	/**
	 * 건설업등록기준사전조사결과 정보를  조회한다.
	 * @param param HashMap<String, Object>
	 * @return CcCsrsVo
	 */
	public CcCsrsVo getCcCsrsInfo(HashMap<String, Object> param) throws DataAccessException;


	/**
	 * 건설업등록기준사전조사결과 정보를  저장한다.
	 * @param ccCsrsVo CcCsrsVo
	 * @return int
	 */
	public int saveOrUpdateCcCsrs(CcCsrsVo ccCsrsVo) throws DataAccessException;


	/**
	 * 건설업등록기준사전조사결과의 파일 정보를 삭제한다.
	 * @param param HashMap<String, Object>
	 * @return int
	 */
	public int deleteCcCsrsFile(HashMap<String, Object> param) throws DataAccessException;




	/********************************/
	/*	1.3 건설업등록기준사전자료제출			*/
	/********************************/
	/**
	 *건설업등록기준사전조사 제출자료 목록을 조회한다.
	 * @param param HashMap<String, Object>
	 * @return List<CcCsexVo>
	 */
	public List<CcCsexVo> getCcCsexList(HashMap<String, Object> param) throws DataAccessException;


	/**
	 * 건설업등록기준사전조사 제출자료 정보를  조회한다.
	 * @param param HashMap<String, Object>
	 * @return CcCsexVo
	 */
	public CcCsexVo getCcCsexInfo(HashMap<String, Object> param) throws DataAccessException;


	/**
	 * 건설업등록기준사전조사자료 제출 정보를  등록한다.
	 * @param ccCsexVo CcCsexVo
	 * @return int
	 */
	public int insertCcCsex(CcCsexVo ccCsexVo) throws DataAccessException;





	/********************************/
	/*		2. 민원관리-위반업체행정처분 정보	*/
	/********************************/
	/**
	 * 페이지 단위로 위반업체행정처분 목록을 조회한다.
	 * @param param HashMap<String, Object>
	 * @return List<CcVcadVo>
	 */
	public List<CcVcadVo> selectCcVcadList(HashMap<String, Object> param) throws DataAccessException;


	/**
	 * 위반업체행정처분 정보를  조회한다.
	 * @param param HashMap<String, Object>
	 * @return CcVcadVo
	 */
	public CcVcadVo getCcVcadInfo(HashMap<String, Object> param) throws DataAccessException;


	/**
	 * 위반업체행정처분 정보를  등록한다.
	 * @param ccVcadVo CcVcadVo
	 * @return int
	 */
	public int insertCcVcad(CcVcadVo ccVcadVo) throws DataAccessException;


	/**
	 * 위반업체행정처분 정보를  수정한다.
	 * @param ccVcadVo CcVcadVo
	 * @return int
	 */
	public int updateCcVcad(CcVcadVo ccVcadVo) throws DataAccessException;


	/**
	 * 위반업체행정처분 정보를 삭제한다.
	 * @param param HashMap<String, Object>
	 * @return int
	 */
	public int deleteCcVcad(HashMap<String, Object> param) throws DataAccessException;




	/********************************/
	/*		3. 불법하도급신고 관리			*/
	/********************************/
	/**
	 * 페이지 단위로 불법하도급신고 목록을 조회한다.
	 * @param param HashMap<String, Object>
	 * @return List<CcIsrdVo>
	 */
	public List<CcIsrdVo> selectCcIsrdList(HashMap<String, Object> param) throws DataAccessException;


	/**
	 * KISCON 관리번호로 불법하도급신고 목록을 조회한다.
	 * @param param HashMap<String, Object>
	 * @return List<CcIsrdVo>
	 */
	public List<CcIsrdVo> getCcIsrdList(HashMap<String, Object> param) throws DataAccessException;


	/**
	 * 불법하도급신고 정보를  조회한다.
	 * @param param HashMap<String, Object>
	 * @return CcIsrdVo
	 */
	public CcIsrdVo getCcIsrdInfo(HashMap<String, Object> param) throws DataAccessException;


	/**
	 * 불법하도급신고 정보를  등록한다.
	 * @param ccIsrd CcIsrdVo
	 * @return int
	 */
	public int insertCcIsrd(CcIsrdVo ccIsrd) throws DataAccessException;


	/**
	 * 불법하도급신고 정보를  수정한다.
	 * @param ccIsrd CcIsrdVo
	 * @return int
	 */
	public int updateCcIsrd(CcIsrdVo ccIsrd) throws DataAccessException;


	/**
	 * 불법하도급신고 정보를 삭제한다.
	 * @param param HashMap<String, Object>
	 * @return int
	 */
	public int deleteCcIsrd(HashMap<String, Object> param) throws DataAccessException;


	/**
	 * 불법하도급신고 파일 정보를 삭제한다.
	 * @param param HashMap<String, Object>
	 * @return int
	 */
	public int deleteCcIsrdFile(HashMap<String, Object> param) throws DataAccessException;


	/**
	 * 불법하도급신고결과 정보를  조회한다.
	 * @param param HashMap<String, Object>
	 * @return CcIsrrVo
	 */
	public CcIsrrVo getCcIsrrInfo(HashMap<String, Object> param) throws DataAccessException;


	/**
	 * 불법하도급신고결과 정보를  저장한다.
	 * @param ccIsrr CcIsrrVo
	 * @return int
	 */
	public int saveOrUpdateCcIsrr(CcIsrrVo ccIsrr) throws DataAccessException;


	/**
	 * 불법하도급신고결과 정보를 삭제한다.
	 * @param param HashMap<String, Object>
	 * @return int
	 */
	public int deleteCcIsrr(HashMap<String, Object> param) throws DataAccessException;


	/**
	 * 불법하도급신고결과 파일 정보를 삭제한다.
	 * @param param HashMap<String, Object>
	 * @return int
	 */
	public int deleteCcIsrrFile(HashMap<String, Object> param) throws DataAccessException;

	/**
	 * 메일발송할 정보를  조회한다.
	 * @param param HashMap<String, Object>
	 * @return CcMailVo
	 */
	public CcMailVo getMainInfo(HashMap<String, Object> param) throws DataAccessException;

	

}
