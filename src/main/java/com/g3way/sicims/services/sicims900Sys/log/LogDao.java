package com.g3way.sicims.services.sicims900Sys.log;

import java.util.HashMap;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.g3way.sicims.services.sicims900Sys.log.vo.CmLogVo;
import com.g3way.sicims.services.sicims910User.vo.CmUserVo;

/**
 * 사용자 로그 입력 및 조회하는 DAO 클래스
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

public interface LogDao {

	/**
	 * 사용자접속로그 정보를  등록한다.
	 * @param param LoginVo
	 * @return int
	 * @exception DataAccessException
	 */
	public int insertCmAlog(CmUserVo param);


	/**
	 * 웹접속로그 정보를  등록한다.
	 * @param param CmMenuVo
	 * @return int
	 * @exception DataAccessException
	 */
	public int insertCmWlog(CmLogVo param);


	/**
	 * 데이터수정 메인로그를 등록한다.
	 * @param param CmLogVo
	 * @return int
	 */
	public int insertCmMlog(CmLogVo param);


	/**
	 * 데이터수정 서브 로그를 등록한다.
	 * @param param CmLogVo
	 * @return int
	 */
	public int insertCmSlog(CmLogVo param);


	/**
	 * 테이블의  수정 전후 정보를 조회한다.
	 * @param param HashMap<String, Object>
	 * @return List<HashMap<String, Object>>
	 */
	public List<HashMap<String, Object>> getTblDataList(HashMap<String, Object> param);
}
