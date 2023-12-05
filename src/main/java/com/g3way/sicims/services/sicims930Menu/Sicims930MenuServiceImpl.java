package com.g3way.sicims.services.sicims930Menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import com.g3way.sicims.services.sicims930Menu.vo.CmMenuVo;
import com.g3way.sicims.util.common.SessionUtil;



@Service
public class Sicims930MenuServiceImpl implements Sicims930MenuService {
	@SuppressWarnings("unused")
	private static final Logger LOG = LoggerFactory.getLogger(Sicims930MenuServiceImpl.class);

	@Autowired	private Sicims930MenuDao 	sicims930MenuDao;

	/********************************/
	/*		1. 메뉴접속로그 관리			*/
	/********************************/
	/**
	 * 메뉴접속로그 정보를  등록한다.
	 * @param String mmenuSe
	 * @param String smenuSe
	 * @return int
	 * @exception DataAccessException
	 */
	@Override
	public int insertCmMenu(String mmenuSe, String smenuSe) {
		CmMenuVo param = new CmMenuVo();
		param.setMmenuSe(mmenuSe);
		param.setSmenuSe(smenuSe);
		param.setUpdusrId(SessionUtil.getLoginUserId());
		param.setUpdusrIp(SessionUtil.getLoginUserIp());

		try {
			return sicims930MenuDao.insertCmMenu(param);
		} catch (DataAccessException e) {
			return -1;
		}
	}



	/**
	 * 페이지 단위로 메뉴접속로그 목록을 조회한다.
	 * @param param HashMap<String, Object>
	 * @return List<CmMenuVo>
	 */
	@Override
	public List<CmMenuVo> selectCmMenuList(HashMap<String, Object> param){
		try {
			// 로그시작일자/로그종료일자 '-'제거
			param.put("fromYmd", String.valueOf(param.get("fromYmd")).replace("-", ""));
			param.put("toYmd", 	 String.valueOf(param.get("toYmd")).replace("-", ""));

			return sicims930MenuDao.selectCmMenuList(param);
		} catch (DataAccessException e) {
			return new ArrayList<CmMenuVo>();
		}
	}


	/**
	 * 월별 메뉴접속 현황을 조회한다.
	 * @param param HashMap<String, Object>
	 * @return List<HashMap<String, Object>>
	 */
	@Override
	public List<HashMap<String, Object>> getCmMenuStatList(HashMap<String, Object> param){
		try {
			// 로그시작일자/로그종료일자 '-'제거
			param.put("fromYmd", String.valueOf(param.get("fromYmd")).replace("-", ""));
			param.put("toYmd", 	 String.valueOf(param.get("toYmd")).replace("-", ""));

			return sicims930MenuDao.getCmMenuStatList(param);
		} catch (DataAccessException e) {
			return new ArrayList<HashMap<String, Object>>();
		}
	}



}
