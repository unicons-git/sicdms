package com.g3way.sicims.services.sicims900Sys.log.vo;

import java.math.BigDecimal;

import com.g3way.sicims.services.sicims000Cm.vo.CmmnVo;


/*
 * 로그정보
 */
public class CmLogVo extends CmmnVo{

	private BigDecimal	logSn;			// 웹로그순번
	private String		scrnNm;			// 웹화면명
	private String		paramCn;		// 패러미터내용
	private String		logYr;			// 접속년도
	private String		logYm;			// 접속연월
	private String		logYmd;			// 접속일자
	private String		logDt;			// 접속일시
	private String		userNm;			// 사용자이름
	public BigDecimal getLogSn() {
		return logSn;
	}
	public void setLogSn(BigDecimal logSn) {
		this.logSn = logSn;
	}
	public String getScrnNm() {
		return scrnNm;
	}
	public void setScrnNm(String scrnNm) {
		this.scrnNm = scrnNm;
	}
	public String getParamCn() {
		return paramCn;
	}
	public void setParamCn(String paramCn) {
		this.paramCn = paramCn;
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
	public String getUserNm() {
		return userNm;
	}
	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}
	@Override
	public String toString() {
		return "CmLogVo [logSn=" + logSn + ", scrnNm=" + scrnNm + ", paramCn=" + paramCn + ", logYr=" + logYr
				+ ", logYm=" + logYm + ", logYmd=" + logYmd + ", logDt=" + logDt + ", userNm=" + userNm
				+ ", toString()=" + super.toString() + "]";
	}


}