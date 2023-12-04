package egovframework.com.sec.security.common;

import java.sql.ResultSet;
import java.sql.SQLException;

import egovframework.rte.fdl.security.userdetails.EgovUserDetails;
import egovframework.rte.fdl.security.userdetails.jdbc.EgovUsersByUsernameMapping;

import javax.sql.DataSource;

import com.g3way.sicims.services.sicims910User.vo.CmUserVo;
import com.g3way.sicims.util.common.DateUtil;

/**
 * mapRow 결과를 사용자 EgovUserDetails Object 에 정의한다.
 *
 * @author ByungHun Woo
 * @since 2009.06.01
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2009.03.10  ByungHun Woo    최초 생성
 *   2009.03.20  이문준          UPDATE
 *
 * </pre>
 */

public class EgovSessionMapping extends EgovUsersByUsernameMapping {

	/**
	 * 사용자정보를 테이블에서 조회하여 EgovUsersByUsernameMapping 에 매핑한다.
	 * @param ds DataSource
	 * @param usersByUsernameQuery String
	 */
	public EgovSessionMapping(DataSource ds, String usersByUsernameQuery) {
        super(ds, usersByUsernameQuery);
    }

	/**
	 * mapRow Override
	 * @param rs ResultSet 결과
	 * @param rownum row num
	 * @return Object EgovUserDetails
	 * @exception SQLException
	 */
	@Override
    protected EgovUserDetails mapRow(ResultSet rs, int rownum) throws SQLException {
    	logger.debug("## EgovUsersByUsernameMapping mapRow ##");
		String userId		= rs.getString("user_id");
		String userPswd		= rs.getString("user_pswd");
		String userNm		= rs.getString("user_nm");
		String inst1Cd		= rs.getString("inst1_cd");
		String inst2Cd		= rs.getString("inst2_cd");
		String inst1Nm		= rs.getString("inst1_nm");
		String inst2Nm		= rs.getString("inst2_nm");
		String aprvYn		= rs.getString("aprv_yn");
		boolean useYn		= (rs.getString("use_yn").equals("Y") ? true : false);
		String lastLgnYmd	= rs.getString("last_lgn_ymd");
		String authrtCd    	= rs.getString("authrt_cd");
		String authrtNm 	= rs.getString("authrt_nm");

		if(lastLgnYmd != null  && lastLgnYmd.length() == 8){
			lastLgnYmd = DateUtil.format(lastLgnYmd, "yyyymmdd", "yyyy-mm-dd");
		}

		// 세션 항목 설정
		CmUserVo cmUserVo = new CmUserVo();
		cmUserVo.setUserId(userId);
		cmUserVo.setUserPswd(userPswd);
		cmUserVo.setUserNm(userNm);
		cmUserVo.setInst1Cd(inst1Cd);
		cmUserVo.setInst2Cd(inst2Cd);
		cmUserVo.setInst1Nm(inst1Nm);
		cmUserVo.setInst2Nm(inst2Nm);
		cmUserVo.setAprvYn(aprvYn);
		cmUserVo.setLastLgnYmd(lastLgnYmd);	/* 마지막 로그인 일자 */
		cmUserVo.setAuthrtCd(authrtCd);		/* 헤딩 계정에 부여된 권한 코드 */
		cmUserVo.setAuthrtNm(authrtNm);		/* 헤딩 계정에 부여된 권한 명 */

		return new EgovUserDetails(userId, userPswd, useYn, cmUserVo);
    }
}
