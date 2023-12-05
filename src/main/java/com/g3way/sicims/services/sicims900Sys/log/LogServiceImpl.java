package com.g3way.sicims.services.sicims900Sys.log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.g3way.sicims.exception.SicimsException;
import com.g3way.sicims.services.sicims900Sys.log.vo.CmLogVo;
import com.g3way.sicims.services.sicims910User.vo.CmUserVo;
import com.g3way.sicims.util.common.SessionUtil;

import egovframework.com.cmm.service.EgovProperties;

/**
 * 사용자 로그 입력 및 조회하는  구현 클래스
 * @author 조준희
 * @since 2021.03.25
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2021.03.25  조준희          최초 생성
 *  </pre>
 */
@Service("logService")
public class LogServiceImpl implements LogService {

    @Resource(name="logDao")
    private LogDao logDao;

    private static final Logger LOG = LoggerFactory.getLogger(LogServiceImpl.class);


	/**
	 * 테이블의  수정 전후 정보를 조회한다.
	 * @param param HashMap<String, Object>
	 * @return List<HashMap<String, Object>>
	 */
    @Override
	public List<HashMap<String, Object>> getTblDataList(HashMap<String, Object> param){
		try {
			return logDao.getTblDataList(param);
		} catch (DataAccessException e) {
			return new ArrayList<HashMap<String, Object>>();
		}
	}


	/**
	 * 사용자접속로그 정보를  등록한다.
	 * @param param CmUserVo
	 * @return int
	 * @exception DataAccessException
	 */
    @Override
	public int insertCmAlog(CmUserVo param){
		try {
			return logDao.insertCmAlog(param);
		} catch (DataAccessException e) {
			return -1;
		}
    }



	/**
	 * 웹접속로그 정보를  등록한다.
	 * @param param CmLogVo
	 * @return int
	 * @exception DataAccessException
	 */
    @Override
	public int insertCmWlog(CmLogVo param) {
		try {
			return logDao.insertCmWlog(param);
		} catch (DataAccessException e) {
			return -1;
		}
    }


	/**
	 * 데이터수정 메인로그를 등록한다.
	 * @param param HashMap<String, Object>
	 * @param updtBeforeDataList List<HashMap<String, Object>>
	 * @param updtAfterDataList  List<HashMap<String, Object>>
	 * @return int
	 */
    @Override
	@SuppressWarnings("unchecked")
	public int insertCmMlog(HashMap<String, Object> param, List<HashMap<String, Object>> updtBeforeDataList, List<HashMap<String, Object>>updtAfterDataList) {
		int result = 0;
		//기존 데이터가 있는경우 수정 및 삭제
		CmLogVo cmLogVo = new CmLogVo();
		cmLogVo.setMlogScrnNm((param.get("mlogScrnNm") != null ) ? param.get("mlogScrnNm").toString() : "");
		cmLogVo.setTblNm((param.get("tblNm") != null ) ? param.get("tblNm").toString() : "");
		cmLogVo.setSqlWhrCn((param.get("sqlWhrCn") != null ) ? param.get("sqlWhrCn").toString() : "");
		cmLogVo.setUpdusrId(SessionUtil.getLoginUserId());
		cmLogVo.setUpdusrIp(SessionUtil.getLoginUserIp());
		String exclude =  EgovProperties.getProperty("Globals.updtLog.exclude");

		HashMap<String, Object> fixUpdtAfterDataColum = (param.get("fixUpdtAfterDataColum") != null ) ? (HashMap<String,Object>)param.get("fixUpdtAfterDataColum") : null;

		try {
			if(updtBeforeDataList != null ) {
				//기존데이터 수정인경우
				if(updtBeforeDataList.size() > 0 && updtAfterDataList != null && updtAfterDataList.size() > 0 && updtBeforeDataList.size() == updtAfterDataList.size() ) {

					for(int idx = 0; idx < updtBeforeDataList.size(); idx++) {
						logDao.insertCmMlog(cmLogVo);
						HashMap<String, Object> modifyBeforeDataMap = updtBeforeDataList.get(idx);
						HashMap<String, Object> modifyAfterDataMap 	= updtAfterDataList.get(idx);

						for (Entry<String, Object> key : modifyBeforeDataMap.entrySet()) {
							//로그입력 제외하는 컬럼(globals.properties 설정)
							if(exclude.indexOf(key.getKey()) != -1) {
								continue;
							}
							if(modifyBeforeDataMap.get(key.getKey()) == null && modifyAfterDataMap.get(key.getKey()) != null ) {
								cmLogVo.setUpdtBfrCn(modifyBeforeDataMap.get(key.getKey()));
								cmLogVo.setUpdtAftrCn(modifyAfterDataMap.get(key.getKey()));
								cmLogVo.setColNm(key.getKey());
								logDao.insertCmSlog(cmLogVo);
							}
							else if(modifyBeforeDataMap.get(key.getKey()) != null && !modifyBeforeDataMap.get(key.getKey()).equals(modifyAfterDataMap.get(key.getKey()))) {
								cmLogVo.setUpdtBfrCn(modifyBeforeDataMap.get(key.getKey()));
								cmLogVo.setUpdtAftrCn(modifyAfterDataMap.get(key.getKey()));
								cmLogVo.setColNm(key.getKey());
								logDao.insertCmSlog(cmLogVo);
							}
						}
					}
					result = 1;
				} else if(updtBeforeDataList.size() == 0 && updtAfterDataList != null && updtAfterDataList.size() > 0 ) {
					//데이터 신규인경우
					for(int idx = 0; idx < updtAfterDataList.size(); idx++) {
						logDao.insertCmMlog(cmLogVo);
						HashMap<String, Object> modifyAfterDataMap = updtAfterDataList.get(idx);

						for (String key : modifyAfterDataMap.keySet()) {
							//로그입력 제외하는 컬럼(globals.properties 설정)
							if(exclude.indexOf(key) != -1) {
								continue;
							}

							if(modifyAfterDataMap.get(key) != null ) {
								cmLogVo.setUpdtBfrCn(null);
								cmLogVo.setUpdtAftrCn(modifyAfterDataMap.get(key));
								cmLogVo.setColNm(key);
								logDao.insertCmSlog(cmLogVo);
							}
						}
					}
					result = 1;
				} else if(updtBeforeDataList.size() > 0 && updtAfterDataList != null && updtAfterDataList.size() == 0 ) {
					//데이터 삭제인경우
					for(int idx = 0; idx < updtBeforeDataList.size(); idx++) {
						logDao.insertCmMlog(cmLogVo);
						HashMap<String, Object> modifyBeforeDataMap = updtBeforeDataList.get(idx);

						for (String key : modifyBeforeDataMap.keySet()) {
							//로그입력 제외하는 컬럼(globals.properties 설정)
							if(exclude.indexOf(key) != -1) {
								continue;
							}
							if(fixUpdtAfterDataColum == null && modifyBeforeDataMap.get(key) != null ) {
								cmLogVo.setUpdtBfrCn(modifyBeforeDataMap.get(key));
								cmLogVo.setUpdtAftrCn(null);
								cmLogVo.setColNm(key);
								logDao.insertCmSlog(cmLogVo);
							}
							else if(fixUpdtAfterDataColum != null && fixUpdtAfterDataColum.containsKey(key) && modifyBeforeDataMap.get(key) != null && !modifyBeforeDataMap.get(key).equals(fixUpdtAfterDataColum.get(key))) {
								cmLogVo.setUpdtBfrCn(modifyBeforeDataMap.get(key));
								cmLogVo.setUpdtAftrCn(fixUpdtAfterDataColum.get(key));
								cmLogVo.setColNm(key);
								logDao.insertCmSlog(cmLogVo);
							}
						}
					}
					result = 1;
				}
				else {
					result = 1;
				}
			}
			//기존데이터가 없으면서 세로운 데이터가 있는경우(입력)
			else if(( updtBeforeDataList == null || updtBeforeDataList.size() == 0 )  && updtAfterDataList != null && updtAfterDataList.size() > 0) {
				result = 1;
			}
		}catch (NullPointerException e) {
			LOG.error("수정 로그 입력중 오류가 발생했습니다[NullPointerException].");
			throw new SicimsException("수정 로그 입력중 오류가 발생했습니다.");
		}catch (Exception e) {
			LOG.error("수정 로그 입력중 오류가 발생했습니다[Exception].");
			throw new SicimsException("수정 로그 입력중 오류가 발생했습니다.");
		}
		return result;
	}
}
