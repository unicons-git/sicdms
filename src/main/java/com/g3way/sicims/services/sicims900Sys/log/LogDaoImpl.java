package com.g3way.sicims.services.sicims900Sys.log;


import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.g3way.sicims.mybatis.mappers.LogMapper;
import com.g3way.sicims.services.sicims900Sys.log.vo.CmLogVo;
import com.g3way.sicims.services.sicims910User.vo.CmUserVo;

@Repository("logDao")
public class LogDaoImpl implements LogDao {

	@Resource(name = "logMapper")
	private LogMapper logMapper;


	/**
	 * 사용자접속로그 정보를  등록한다.
	 * @param param CmUserVo
	 * @return int
	 * @exception DataAccessException
	 */
	@Override
	public int insertCmAlog(CmUserVo param){
    	return logMapper.insertCmAlog(param);
    }


	/**
	 * 웹접속로그 정보를  등록한다.
	 * @param param CmLogVo
	 * @return int
	 * @exception DataAccessException
	 */
	@Override
	public int insertCmWlog(CmLogVo param){
    	return logMapper.insertCmWlog(param);
    }


	/**
	 * 데이터수정 메인로그를 등록한다.
	 * @param param CmLogVo
	 * @return int
	 */
	@Override
	public int insertCmMlog(CmLogVo param){
    	return logMapper.insertCmMlog(param);
    }



	/**
	 * 데이터수정 서브 로그를 등록한다.
	 * @param param CmLogVo
	 * @return int
	 */
	@Override
	public int insertCmSlog(CmLogVo param){
		//해당 데이터가 1024가 넘는경우 잘라서 1024까지만 입력한다.
		if(param.getUpdtBfrCn() != null && param.getUpdtBfrCn().getClass() == String.class && param.getUpdtBfrCn().toString().length() > 1024) {
			param.setUpdtBfrCn(param.getUpdtBfrCn().toString().substring(0, 1024));
		}
		//해당 데이터가 1024가 넘는경우 잘라서 1024까지만 입력한다.
		if(param.getUpdtAftrCn() != null && param.getUpdtAftrCn().getClass() == String.class && param.getUpdtAftrCn().toString().length() > 1024) {
			param.setUpdtAftrCn(param.getUpdtAftrCn().toString().substring(0, 1024));
		}

    	return logMapper.insertCmSlog(param);
    }



	/**
	 * 테이블의  수정 전후 정보를 조회한다.
	 * @param param HashMap<String, Object>
	 * @return List<HashMap<String, Object>>
	 */
	@Override
	public List<HashMap<String, Object>> getTblDataList(HashMap<String, Object> param) {
		return logMapper.getTblDataList(param);
	}

}
