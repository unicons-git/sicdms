package com.g3way.sicims.services.sicims300Cc.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.g3way.sicims.services.sicims000Cm.vo.CmFileVo;

/*
 * 건설업등록기준사전자료제출 정보
 *
*/
public class CcCsexVo extends CmFileVo implements Serializable{
	private static final long serialVersionUID = -77894072975501075L;

	private BigDecimal	cirsSn;					// 건설업등록기준일련번호
	private String		exmnDataSn;				// 조사자료일련번호
	private String		exmnDataSbmsnYmd;		// 조사자료제출일자
	private String		exmnDataCn;				// 조사자료내용
	private BigDecimal	exmnDataAtflId;			// 조사자료첨부파일ID
	private String		exmnDataAtflNm;			// 조사자료첨부파일명

	public BigDecimal getCirsSn() {
		return cirsSn;
	}
	public void setCirsSn(BigDecimal cirsSn) {
		this.cirsSn = cirsSn;
	}
	public String getExmnDataSn() {
		return exmnDataSn;
	}
	public void setExmnDataSn(String exmnDataSn) {
		this.exmnDataSn = exmnDataSn;
	}
	public String getExmnDataSbmsnYmd() {
		return exmnDataSbmsnYmd;
	}
	public void setExmnDataSbmsnYmd(String exmnDataSbmsnYmd) {
		this.exmnDataSbmsnYmd = exmnDataSbmsnYmd;
	}
	public String getExmnDataCn() {
		return exmnDataCn;
	}
	public void setExmnDataCn(String exmnDataCn) {
		this.exmnDataCn = exmnDataCn;
	}
	public BigDecimal getExmnDataAtflId() {
		return exmnDataAtflId;
	}
	public void setExmnDataAtflId(BigDecimal exmnDataAtflId) {
		this.exmnDataAtflId = exmnDataAtflId;
	}
	public String getExmnDataAtflNm() {
		return exmnDataAtflNm;
	}
	public void setExmnDataAtflNm(String exmnDataAtflNm) {
		this.exmnDataAtflNm = exmnDataAtflNm;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "CcCsexVo [cirsSn=" + cirsSn + ", exmnDataSn=" + exmnDataSn + ", exmnDataSbmsnYmd=" + exmnDataSbmsnYmd
				+ ", exmnDataCn=" + exmnDataCn + ", exmnDataAtflId=" + exmnDataAtflId + ", exmnDataAtflNm="
				+ exmnDataAtflNm + ", toString()=" + super.toString() + "]";
	}


}