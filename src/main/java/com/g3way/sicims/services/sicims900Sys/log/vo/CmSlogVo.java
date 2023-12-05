package com.g3way.sicims.services.sicims900Sys.log.vo;

import java.math.BigDecimal;

import com.g3way.sicims.services.sicims000Cm.vo.CmmnVo;


/*
 * 서브로그정보
 */
public class CmSlogVo extends CmmnVo{
	private BigDecimal	mlogSn;			/* 메인로그순번 */
	private BigDecimal	slogSn;			/* 서브로그순번 */
	private String		colNm;			/* 컬럼명 */
	private String 		updtBfrCn;		/* 갱신전내용 */
	private String		updtAftrCn;		/* 갱신후내용 */
	public BigDecimal getMlogSn() {
		return mlogSn;
	}
	public void setMlogSn(BigDecimal mlogSn) {
		this.mlogSn = mlogSn;
	}
	public BigDecimal getSlogSn() {
		return slogSn;
	}
	public void setSlogSn(BigDecimal slogSn) {
		this.slogSn = slogSn;
	}
	public String getColNm() {
		return colNm;
	}
	public void setColNm(String colNm) {
		this.colNm = colNm;
	}
	public String getUpdtBfrCn() {
		return updtBfrCn;
	}
	public void setUpdtBfrCn(String updtBfrCn) {
		this.updtBfrCn = updtBfrCn;
	}
	public String getUpdtAftrCn() {
		return updtAftrCn;
	}
	public void setUpdtAftrCn(String updtAftrCn) {
		this.updtAftrCn = updtAftrCn;
	}
	@Override
	public String toString() {
		return "CmSlogVo [mlogSn=" + mlogSn + ", slogSn=" + slogSn + ", colNm=" + colNm + ", updtBfrCn=" + updtBfrCn
				+ ", updtAftrCn=" + updtAftrCn + ", toString()=" + super.toString() + "]";
	}



}