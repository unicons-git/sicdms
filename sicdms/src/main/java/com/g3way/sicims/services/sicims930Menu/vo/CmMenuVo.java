package com.g3way.sicims.services.sicims930Menu.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.g3way.sicims.services.sicims000Cm.vo.CmmnVo;

/*
 * 메뉴접속정보
 *
*/
public class CmMenuVo extends CmmnVo implements Serializable{
	/**  */
	private static final long serialVersionUID = 7527249249467266938L;
	private BigDecimal	logSn;			// 웹로그순번
	private String		mmenuSe;		// 주메뉴구분
	private String		smenuSe;		// 서브메뉴구분
	private String		logYr;			// 접속년도
	private String		logYm;			// 접속연월
	private String		logYmd;			// 접속일자
	private String		logDt;			// 접속일시

	private String		mmenuSeNm;		// 주메뉴구분먕
	private String		smenuSeNm;		// 서브메뉴구분명
	private String		userNm;			// 사용자명
	private String		deptNm;			// 부서명
	public BigDecimal getLogSn() {
		return logSn;
	}
	public void setLogSn(BigDecimal logSn) {
		this.logSn = logSn;
	}
	public String getMmenuSe() {
		return mmenuSe;
	}
	public void setMmenuSe(String mmenuSe) {
		this.mmenuSe = mmenuSe;
	}
	public String getSmenuSe() {
		return smenuSe;
	}
	public void setSmenuSe(String smenuSe) {
		this.smenuSe = smenuSe;
	}
	public String getLogYr() {
		return logYr;
	}
	public void setLogYr(String logYr) {
		this.logYr = logYr;
	}
	public String getLogYm() {
		return logYm;
	}
	public void setLogYm(String logYm) {
		this.logYm = logYm;
	}
	public String getLogYmd() {
		return logYmd;
	}
	public void setLogYmd(String logYmd) {
		this.logYmd = logYmd;
	}
	public String getLogDt() {
		return logDt;
	}
	public void setLogDt(String logDt) {
		this.logDt = logDt;
	}
	public String getMmenuSeNm() {
		return mmenuSeNm;
	}
	public void setMmenuSeNm(String mmenuSeNm) {
		this.mmenuSeNm = mmenuSeNm;
	}
	public String getSmenuSeNm() {
		return smenuSeNm;
	}
	public void setSmenuSeNm(String smenuSeNm) {
		this.smenuSeNm = smenuSeNm;
	}
	public String getUserNm() {
		return userNm;
	}
	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}
	public String getDeptNm() {
		return deptNm;
	}
	public void setDeptNm(String deptNm) {
		this.deptNm = deptNm;
	}
	@Override
	public String toString() {
		return "CmMenuVo [logSn=" + logSn + ", mmenuSe=" + mmenuSe + ", smenuSe=" + smenuSe + ", logYr=" + logYr
				+ ", logYm=" + logYm + ", logYmd=" + logYmd + ", logDt=" + logDt + ", mmenuSeNm=" + mmenuSeNm
				+ ", smenuSeNm=" + smenuSeNm + ", userNm=" + userNm + ", deptNm=" + deptNm + ", toString()="
				+ super.toString() + "]";
	}




}