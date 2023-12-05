package com.g3way.sicims.services.sicims300Cc.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.g3way.sicims.services.sicims000Cm.vo.CmmnVo;

/*
 * 불법하도급신고 정보
 *
*/
public class CcIsrdVo extends CmmnVo implements Serializable{
	private static final long serialVersionUID = 7559574443366530031L;

	private String		rcptNo;						// 접수번호
	private String		regInstCd;					// 등록기관코드
	private String		rcptYmd;					// 접수일자
	private String		dclNm;						// 신고자명
	private String		dclBrdt;					// 신고자생년월일
	private String		dclAddr;					// 신고자주소
	private String		dclTelno;					// 신고자전화번호
	private String		dclEmlAddr;					// 신고자이메일주소
	private String		gnctrtBzentyKisconMngNo;	// 원도급업체KISCON관리번호
	private String		gnctrtBzentyNm;				// 원도급업체명
	private String		gnctrtBzentyPicTelno;		// 원도급업체담당자전화번호
	private String		gisbeKisconMngNo;			// 불법하도급지급업체KISCON관리번호
	private String		gisbeNm;					// 불법하도급지급업체명
	private String		gisbeTelno;					// 불법하도급지급업체전화번호
	private String		risbeKisconMngNo;			// 불법하도급수령업체KISCON관리번호
	private String		risbeNm;					// 불법하도급수령업체명
	private String		risbeTelno;					// 불법하도급수령업체전화번호
	private String		cstrnNm;					// 공사명
	private String		podrInstNm;					// 발주기관명
	private String		cstrnSpotAddr;				// 공사현장 주소
	private String		dclrCn;						// 신고내용
	private String		evdncDcmnt;					// 증거서류
	private BigDecimal	dclrFileId;					// 신고파일ID
	private String		dclrRmrk;					// 신고비고

	private String		regInstNm;					// 등록기관명
	private String		dclrFileNm;					// 신고파일명

	public String getRcptNo() {
		return rcptNo;
	}
	public void setRcptNo(String rcptNo) {
		this.rcptNo = rcptNo;
	}
	public String getRegInstCd() {
		return regInstCd;
	}
	public void setRegInstCd(String regInstCd) {
		this.regInstCd = regInstCd;
	}
	public String getRcptYmd() {
		return rcptYmd;
	}
	public void setRcptYmd(String rcptYmd) {
		this.rcptYmd = rcptYmd;
	}
	public String getDclNm() {
		return dclNm;
	}
	public void setDclNm(String dclNm) {
		this.dclNm = dclNm;
	}
	public String getDclBrdt() {
		return dclBrdt;
	}
	public void setDclBrdt(String dclBrdt) {
		this.dclBrdt = dclBrdt;
	}
	public String getDclAddr() {
		return dclAddr;
	}
	public void setDclAddr(String dclAddr) {
		this.dclAddr = dclAddr;
	}
	public String getDclTelno() {
		return dclTelno;
	}
	public void setDclTelno(String dclTelno) {
		this.dclTelno = dclTelno;
	}
	public String getDclEmlAddr() {
		return dclEmlAddr;
	}
	public void setDclEmlAddr(String dclEmlAddr) {
		this.dclEmlAddr = dclEmlAddr;
	}
	public String getGnctrtBzentyKisconMngNo() {
		return gnctrtBzentyKisconMngNo;
	}
	public void setGnctrtBzentyKisconMngNo(String gnctrtBzentyKisconMngNo) {
		this.gnctrtBzentyKisconMngNo = gnctrtBzentyKisconMngNo;
	}
	public String getGnctrtBzentyNm() {
		return gnctrtBzentyNm;
	}
	public void setGnctrtBzentyNm(String gnctrtBzentyNm) {
		this.gnctrtBzentyNm = gnctrtBzentyNm;
	}
	public String getGnctrtBzentyPicTelno() {
		return gnctrtBzentyPicTelno;
	}
	public void setGnctrtBzentyPicTelno(String gnctrtBzentyPicTelno) {
		this.gnctrtBzentyPicTelno = gnctrtBzentyPicTelno;
	}
	public String getGisbeKisconMngNo() {
		return gisbeKisconMngNo;
	}
	public void setGisbeKisconMngNo(String gisbeKisconMngNo) {
		this.gisbeKisconMngNo = gisbeKisconMngNo;
	}
	public String getGisbeNm() {
		return gisbeNm;
	}
	public void setGisbeNm(String gisbeNm) {
		this.gisbeNm = gisbeNm;
	}
	public String getGisbeTelno() {
		return gisbeTelno;
	}
	public void setGisbeTelno(String gisbeTelno) {
		this.gisbeTelno = gisbeTelno;
	}
	public String getRisbeKisconMngNo() {
		return risbeKisconMngNo;
	}
	public void setRisbeKisconMngNo(String risbeKisconMngNo) {
		this.risbeKisconMngNo = risbeKisconMngNo;
	}
	public String getRisbeNm() {
		return risbeNm;
	}
	public void setRisbeNm(String risbeNm) {
		this.risbeNm = risbeNm;
	}
	public String getRisbeTelno() {
		return risbeTelno;
	}
	public void setRisbeTelno(String risbeTelno) {
		this.risbeTelno = risbeTelno;
	}
	public String getCstrnNm() {
		return cstrnNm;
	}
	public void setCstrnNm(String cstrnNm) {
		this.cstrnNm = cstrnNm;
	}
	public String getPodrInstNm() {
		return podrInstNm;
	}
	public void setPodrInstNm(String podrInstNm) {
		this.podrInstNm = podrInstNm;
	}
	public String getCstrnSpotAddr() {
		return cstrnSpotAddr;
	}
	public void setCstrnSpotAddr(String cstrnSpotAddr) {
		this.cstrnSpotAddr = cstrnSpotAddr;
	}
	public String getDclrCn() {
		return dclrCn;
	}
	public void setDclrCn(String dclrCn) {
		this.dclrCn = dclrCn;
	}
	public String getEvdncDcmnt() {
		return evdncDcmnt;
	}
	public void setEvdncDcmnt(String evdncDcmnt) {
		this.evdncDcmnt = evdncDcmnt;
	}
	public BigDecimal getDclrFileId() {
		return dclrFileId;
	}
	public void setDclrFileId(BigDecimal dclrFileId) {
		this.dclrFileId = dclrFileId;
	}
	public String getDclrRmrk() {
		return dclrRmrk;
	}
	public void setDclrRmrk(String dclrRmrk) {
		this.dclrRmrk = dclrRmrk;
	}
	public String getRegInstNm() {
		return regInstNm;
	}
	public void setRegInstNm(String regInstNm) {
		this.regInstNm = regInstNm;
	}
	public String getDclrFileNm() {
		return dclrFileNm;
	}
	public void setDclrFileNm(String dclrFileNm) {
		this.dclrFileNm = dclrFileNm;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "CcIsrdVo [rcptNo=" + rcptNo + ", regInstCd=" + regInstCd + ", rcptYmd=" + rcptYmd + ", dclNm=" + dclNm
				+ ", dclBrdt=" + dclBrdt + ", dclAddr=" + dclAddr + ", dclTelno=" + dclTelno + ", dclEmlAddr="
				+ dclEmlAddr + ", gnctrtBzentyKisconMngNo=" + gnctrtBzentyKisconMngNo + ", gnctrtBzentyNm="
				+ gnctrtBzentyNm + ", gnctrtBzentyPicTelno=" + gnctrtBzentyPicTelno + ", gisbeKisconMngNo="
				+ gisbeKisconMngNo + ", gisbeNm=" + gisbeNm + ", gisbeTelno=" + gisbeTelno + ", risbeKisconMngNo="
				+ risbeKisconMngNo + ", risbeNm=" + risbeNm + ", risbeTelno=" + risbeTelno + ", cstrnNm=" + cstrnNm
				+ ", podrInstNm=" + podrInstNm + ", cstrnSpotAddr=" + cstrnSpotAddr + ", dclrCn=" + dclrCn
				+ ", evdncDcmnt=" + evdncDcmnt + ", dclrFileId=" + dclrFileId + ", dclrRmrk=" + dclrRmrk
				+ ", regInstNm=" + regInstNm + ", dclrFileNm=" + dclrFileNm + ", toString()=" + super.toString() + "]";
	}


}