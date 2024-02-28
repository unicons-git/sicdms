package com.g3way.sicims.services.sicims300Cc.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/*
 * 건설업등록기준사전자료제출 정보
 *
*/
public class CcMailVo implements Serializable{

	/**  */
	private static final long serialVersionUID = 1L;
	private BigDecimal	cirsSn;					// 건설업등록기준일련번호
	private String		cstrnNm;				// 공사명
	private String		exmnBzentyPicNm;		// 조사업체담당자명
	private String 		exmnDataSbmsnYmd; // 제출일자
	private BigDecimal fileId;
	private String fileOrgnlNm;
	private String updtYmd;
	private String emlAddr;
	private String exmnBzentyPicTelno;
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public BigDecimal getCirsSn() {
		return cirsSn;
	}
	public String getCstrnNm() {
		return cstrnNm;
	}
	public String getExmnBzentyPicNm() {
		return exmnBzentyPicNm;
	}
	public String getExmnDataSbmsnYmd() {
		return exmnDataSbmsnYmd;
	}
	public BigDecimal getFileId() {
		return fileId;
	}
	public String getFileOrgnlNm() {
		return fileOrgnlNm;
	}
	public String getUpdtYmd() {
		return updtYmd;
	}
	public void setCirsSn(BigDecimal cirsSn) {
		this.cirsSn = cirsSn;
	}
	public void setCstrnNm(String cstrnNm) {
		this.cstrnNm = cstrnNm;
	}
	public void setExmnBzentyPicNm(String exmnBzentyPicNm) {
		this.exmnBzentyPicNm = exmnBzentyPicNm;
	}
	public void setExmnDataSbmsnYmd(String exmnDataSbmsnYmd) {
		this.exmnDataSbmsnYmd = exmnDataSbmsnYmd;
	}
	public void setFileId(BigDecimal fileId) {
		this.fileId = fileId;
	}
	public void setFileOrgnlNm(String fileOrgnlNm) {
		this.fileOrgnlNm = fileOrgnlNm;
	}
	public void setUpdtYmd(String updtYmd) {
		this.updtYmd = updtYmd;
	}
	public String getEmlAddr() {
		return emlAddr;
	}
	public void setEmlAddr(String emlAddr) {
		this.emlAddr = emlAddr;
	}
	public String getExmnBzentyPicTelno() {
		return exmnBzentyPicTelno;
	}
	public void setExmnBzentyPicTelno(String exmnBzentyPicTelno) {
		this.exmnBzentyPicTelno = exmnBzentyPicTelno;
	}
	
}