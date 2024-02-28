package com.g3way.sicims.services.sicims300Cc;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.g3way.sicims.mybatis.mappers.Sicims300CcMapper;
import com.g3way.sicims.services.sicims300Cc.vo.CcCsexVo;
import com.g3way.sicims.services.sicims300Cc.vo.CcCsmaVo;
import com.g3way.sicims.services.sicims300Cc.vo.CcCsrsVo;
import com.g3way.sicims.services.sicims300Cc.vo.CcIsrdVo;
import com.g3way.sicims.services.sicims300Cc.vo.CcIsrrVo;
import com.g3way.sicims.services.sicims300Cc.vo.CcMailVo;
import com.g3way.sicims.services.sicims300Cc.vo.CcVcadVo;


@Repository
public class  Sicims300CcDaoImpl implements Sicims300CcDao {

	@Resource(name = "sicims300CcMapper")
	private Sicims300CcMapper sicims300CcMapper;

	/********************************/
	/*	1. 민원관리-건설업등록기준사전조사 자료관리	*/
	/*	1.1 건설업등록기준사전조사 정보			*/
	/********************************/
	/**
	 * 페이지 단위로 건설업등록기준사전조사 목록을 조회한다.
	 * @param param HashMap<String, Object>
	 * @return List<CcCsmaVo>
	 */
	@Override
	public List<CcCsmaVo> selectCcCsmaList(HashMap<String, Object> param){
		return sicims300CcMapper.selectCcCsmaList(param);
	}


	/**
	 * 건설업등록기준사전조사 정보를  조회한다.
	 * @param param HashMap<String, Object>
	 * @return CcCsmaVo
	 */
	@Override
	public CcCsmaVo getCcCsmaInfo(HashMap<String, Object> param){
		return sicims300CcMapper.getCcCsmaInfo(param);
	}


	/**
	 * 건설업등록기준사전조사 정보를  등록한다.
	 * @param ccCsmaVo CcCsmaVo
	 * @return int
	 */
	@Override
	public int insertCcCsma(CcCsmaVo ccCsmaVo){
		return sicims300CcMapper.insertCcCsma(ccCsmaVo);
	}


	/**
	 * 건설업등록기준사전조사 정보를  수정한다.
	 * @param ccCsmaVo CcCsmaVo
	 * @return int
	 */
	@Override
	public int updateCcCsma(CcCsmaVo ccCsmaVo){
		return sicims300CcMapper.updateCcCsma(ccCsmaVo);
	}

	/**
	 * 건설업등록기준사전조사 제출상태 정보를  수정한다.
	 * @param param HashMap<String, Object>
	 * @return int
	 */
	public int updateCcCsmaSbmsn(HashMap<String, Object> param){
		return sicims300CcMapper.updateCcCsmaSbmsn(param);
	}


	/**
	 * 업체담당자비밀번호를 변경한다.
	 * @param ccCsmaVo CcCsmaVo
	 * @return int
	 */
	@Override
	public int updateCcCsmaPswd(CcCsmaVo ccCsmaVo){
		return sicims300CcMapper.updateCcCsmaPswd(ccCsmaVo);
	}


	/**
	 * 건설업등록기준사전조사 정보를 삭제한다.
	 * @param param HashMap<String, Object>
	 * @return int
	 */
	@Override
	public int deleteCcCsma(HashMap<String, Object> param){
		return sicims300CcMapper.deleteCcCsma(param);
	}




	/********************************/
	/*	1.2 민원관리-건설업등록기준사전조사결과 정보	*/
	/********************************/
	/**
	 * 건설업등록기준사전조사결과 정보를  조회한다.
	 * @param param HashMap<String, Object>
	 * @return CcCsrsVo
	 */
	@Override
	public CcCsrsVo getCcCsrsInfo(HashMap<String, Object> param){
		return sicims300CcMapper.getCcCsrsInfo(param);
	}


	/**
	 * 건설업등록기준사전조사결과 정보를  저장한다.
	 * @param ccCsrsVo CcCsrsVo
	 * @return int
	 */
	@Override
	public int saveOrUpdateCcCsrs(CcCsrsVo ccCsrsVo){
		return sicims300CcMapper.saveOrUpdateCcCsrs(ccCsrsVo);
	}


	/**
	 * 건설업등록기준사전조사결과의 파일 정보를 삭제한다.
	 * @param param HashMap<String, Object>
	 * @return int
	 */
	@Override
	public int deleteCcCsrsFile(HashMap<String, Object> param){
		return sicims300CcMapper.deleteCcCsrsFile(param);
	}






	/********************************/
	/*	1.3 건설업등록기준사전자료제출			*/
	/********************************/
	/**
	 *건설업등록기준사전조사 제출자료 목록을 조회한다.
	 * @param param HashMap<String, Object>
	 * @return List<CcCsexVo>
	 */
	@Override
	public List<CcCsexVo> getCcCsexList(HashMap<String, Object> param){
		return sicims300CcMapper.getCcCsexList(param);
	}


	/**
	 * 건설업등록기준사전조사 제출자료 정보를  조회한다.
	 * @param param HashMap<String, Object>
	 * @return CcCsexVo
	 */
	@Override
	public CcCsexVo getCcCsexInfo(HashMap<String, Object> param){
		return sicims300CcMapper.getCcCsexInfo(param);
	}


	/**
	 * 건설업등록기준사전조사자료 제출 정보를  등록한다.
	 * @param ccCsexVo CcCsexVo
	 * @return int
	 */
	public int insertCcCsex(CcCsexVo ccCsexVo){
		return sicims300CcMapper.insertCcCsex(ccCsexVo);
	}





	/********************************/
	/*		2. 민원관리-위반업체행정처분 정보	*/
	/********************************/
	/**
	 * 페이지 단위로 위반업체행정처분 목록을 조회한다.
	 * @param param HashMap<String, Object>
	 * @return List<CcVcadVo>
	 */
	@Override
	public List<CcVcadVo> selectCcVcadList(HashMap<String, Object> param) {
		return sicims300CcMapper.selectCcVcadList(param);
	}


	/**
	 * 위반업체행정처분 정보를  조회한다.
	 * @param param HashMap<String, Object>
	 * @return CcVcadVo
	 */
	@Override
	public CcVcadVo getCcVcadInfo(HashMap<String, Object> param) {
		return sicims300CcMapper.getCcVcadInfo(param);
	}


	/**
	 * 위반업체행정처분 정보를  등록한다.
	 * @param ccVcadVo CcVcadVo
	 * @return int
	 */
	@Override
	public int insertCcVcad(CcVcadVo ccVcadVo){
		return sicims300CcMapper.insertCcVcad(ccVcadVo);
	}


	/**
	 * 위반업체행정처분 정보를  수정한다.
	 * @param ccVcadVo CcVcadVo
	 * @return int
	 */
	@Override
	public int updateCcVcad(CcVcadVo ccVcadVo){
		return sicims300CcMapper.updateCcVcad(ccVcadVo);
	}


	/**
	 * 위반업체행정처분 정보를 삭제한다.
	 * @param param HashMap<String, Object>
	 * @return int
	 */
	@Override
	public int deleteCcVcad(HashMap<String, Object> param){
		return sicims300CcMapper.deleteCcVcad(param);
	}




	/********************************/
	/*		3. 불법하도급신고 관리			*/
	/********************************/
	/**
	 * 페이지 단위로 불법하도급신고 목록을 조회한다.
	 * @param param HashMap<String, Object>
	 * @return List<CcIsrdVo>
	 */
	@Override
	public List<CcIsrdVo> selectCcIsrdList(HashMap<String, Object> param){
		return sicims300CcMapper.selectCcIsrdList(param);
	}


	/**
	 * KISCON 관리번호로 불법하도급신고 목록을 조회한다.
	 * @param param HashMap<String, Object>
	 * @return List<CcIsrdVo>
	 */
	public List<CcIsrdVo> getCcIsrdList(HashMap<String, Object> param){
		return sicims300CcMapper.getCcIsrdList(param);
	}


	/**
	 * 불법하도급신고 정보를  조회한다.
	 * @param param HashMap<String, Object>
	 * @return CcIsrdVo
	 */
	@Override
	public CcIsrdVo getCcIsrdInfo(HashMap<String, Object> param){
		return sicims300CcMapper.getCcIsrdInfo(param);
	}


	/**
	 * 불법하도급신고 정보를  등록한다.
	 * @param ccIsrd CcIsrdVo
	 * @return int
	 */
	@Override
	public int insertCcIsrd(CcIsrdVo ccIsrd){
		return sicims300CcMapper.insertCcIsrd(ccIsrd);
	}


	/**
	 * 불법하도급신고 정보를  수정한다.
	 * @param ccIsrd CcIsrdVo
	 * @return int
	 */
	@Override
	public int updateCcIsrd(CcIsrdVo ccIsrd){
		return sicims300CcMapper.updateCcIsrd(ccIsrd);
	}


	/**
	 * 불법하도급신고 정보를 삭제한다.
	 * @param param HashMap<String, Object>
	 * @return int
	 */
	@Override
	public int deleteCcIsrd(HashMap<String, Object> param){
		return sicims300CcMapper.deleteCcIsrd(param);
	}


	/**
	 * 불법하도급신고 파일 정보를 삭제한다.
	 * @param param HashMap<String, Object>
	 * @return int
	 */
	@Override
	public int deleteCcIsrdFile(HashMap<String, Object> param){
		return sicims300CcMapper.deleteCcIsrdFile(param);
	}


	/**
	 * 불법하도급신고결과 정보를  조회한다.
	 * @param param HashMap<String, Object>
	 * @return CcIsrrVo
	 */
	@Override
	public CcIsrrVo getCcIsrrInfo(HashMap<String, Object> param){
		return sicims300CcMapper.getCcIsrrInfo(param);
	}


	/**
	 * 불법하도급신고결과 정보를  저장한다.
	 * @param ccIsrr CcIsrrVo
	 * @return int
	 */
	@Override
	public int saveOrUpdateCcIsrr(CcIsrrVo ccIsrr){
		return sicims300CcMapper.saveOrUpdateCcIsrr(ccIsrr);
	}


	/**
	 * 불법하도급신고결과 정보를 삭제한다.
	 * @param param HashMap<String, Object>
	 * @return int
	 */
	@Override
	public int deleteCcIsrr(HashMap<String, Object> param){
		return sicims300CcMapper.deleteCcIsrr(param);
	}


	/**
	 * 불법하도급신고결과 파일 정보를 삭제한다.
	 * @param param HashMap<String, Object>
	 * @return int
	 */
	@Override
	public int deleteCcIsrrFile(HashMap<String, Object> param){
		return sicims300CcMapper.deleteCcIsrrFile(param);
	}


	@Override
	public CcMailVo getMainInfo(HashMap<String, Object> param) throws DataAccessException {
		// TODO Auto-generated method stub
		return sicims300CcMapper.getMainInfo(param);
	}



}