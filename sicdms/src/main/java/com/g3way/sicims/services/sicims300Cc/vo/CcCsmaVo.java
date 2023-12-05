package com.g3way.sicims.services.sicims300Cc.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.g3way.sicims.services.sicims000Cm.vo.CmmnVo;

/*
 * 건설업등록기준사전조사 정보
 *
*/
public class CcCsmaVo extends CmmnVo implements Serializable{
	private static final long serialVersionUID = 5298225020445120105L;

	private BigDecimal	cirsSn;					// 건설업등록기준일련번호
	private String		regInstCd;				// 등록기관코드
	private String		pbancNo;				// 공고번호
	private String		pbancYmd;				// 공고일자
	private String		opnbdYmd;				// 개찰일자
	private String		cstrnNm;				// 공사명
	private BigDecimal	cstrnAmt;				// 공사금액(원)
	private String		podrInstNm;				// 발주기관명
	private String		bidPrtcptCndtn;			// 입찰참가조건
	private BigDecimal	bidPrtcptBzentyCnt;		// 입찰참가업체수
	private String		qfexmn1rnkBzentyNm;		// 적격심사1순위업체명
	private String		exmnBzentySe;			// 조사업체구분
	private String		exmnBzentySeNm;			// 조사업체구분명
	private String		exmnBzentyNm;			// 조사업체명
	private String		exmnBzentyRprsvNm;		// 조사업체대표자명
	private String		spcoSe;					// 전문업체구분
	private String		exmnBzentyAddr;			// 조사업체주소
	private String		exmnBzentyPicNm;		// 조사업체담당자명
	private String		exmnBzentyPicTelno;		// 조사업체담당자전화번호
	private String		exmnBzentyPicEmlAddr;	// 조사업체담당자이메일주소
	private String		exmnBzentyPicPswd;		// 조사업체담당자비밀번호
	private String		exmnBzentyCrno;			// 조사업체법인등록번호
	private String		mfldCd;					// 주력분야코드
	private String		pssnLcns;				// 보유면허
	private String		mmadvnSe;				// 상호시장진출구분
	private String		taskTkcgAoNm;			// 업무담당주무관명
	private String		cptlTkcgAoNm;			// 자본금담당주무관명
	private String		acseCstrnPicNm;			// 재무과공사담당자명
	private String		sbmsnBgngYmd;			// 제출시작일자
	private String		sbmsnEndYmd;			// 제출종료일자
	private String		sbmsnSttsCd;			// 제출상태코드
	private String		sbmsnYmd;				// 제출일자

	private String		regInstNm;				// 등록기관명
	private String		mfldNm;					// 주력분야명
	private String		mmadvnSeNm;				// 상호시장진출구분명
	private String		sbmsnSttsNm;			// 제출상태명
	private String		picChgYn;				// 담당자변경여부
	public BigDecimal getCirsSn() {
		return cirsSn;
	}
	public void setCirsSn(BigDecimal cirsSn) {
		this.cirsSn = cirsSn;
	}
	public String getRegInstCd() {
		return regInstCd;
	}
	public void setRegInstCd(String regInstCd) {
		this.regInstCd = regInstCd;
	}
	public String getPbancNo() {
		return pbancNo;
	}
	public void setPbancNo(String pbancNo) {
		this.pbancNo = pbancNo;
	}
	public String getPbancYmd() {
		return pbancYmd;
	}
	public void setPbancYmd(String pbancYmd) {
		this.pbancYmd = pbancYmd;
	}
	public String getOpnbdYmd() {
		return opnbdYmd;
	}
	public void setOpnbdYmd(String opnbdYmd) {
		this.opnbdYmd = opnbdYmd;
	}
	public String getCstrnNm() {
		return cstrnNm;
	}
	public void setCstrnNm(String cstrnNm) {
		this.cstrnNm = cstrnNm;
	}
	public BigDecimal getCstrnAmt() {
		return cstrnAmt;
	}
	public void setCstrnAmt(BigDecimal cstrnAmt) {
		this.cstrnAmt = cstrnAmt;
	}
	public String getPodrInstNm() {
		return podrInstNm;
	}
	public void setPodrInstNm(String podrInstNm) {
		this.podrInstNm = podrInstNm;
	}
	public String getBidPrtcptCndtn() {
		return bidPrtcptCndtn;
	}
	public void setBidPrtcptCndtn(String bidPrtcptCndtn) {
		this.bidPrtcptCndtn = bidPrtcptCndtn;
	}
	public BigDecimal getBidPrtcptBzentyCnt() {
		return bidPrtcptBzentyCnt;
	}
	public void setBidPrtcptBzentyCnt(BigDecimal bidPrtcptBzentyCnt) {
		this.bidPrtcptBzentyCnt = bidPrtcptBzentyCnt;
	}
	public String getQfexmn1rnkBzentyNm() {
		return qfexmn1rnkBzentyNm;
	}
	public void setQfexmn1rnkBzentyNm(String qfexmn1rnkBzentyNm) {
		this.qfexmn1rnkBzentyNm = qfexmn1rnkBzentyNm;
	}
	public String getExmnBzentySe() {
		return exmnBzentySe;
	}
	public void setExmnBzentySe(String exmnBzentySe) {
		this.exmnBzentySe = exmnBzentySe;
	}
	public String getExmnBzentySeNm() {
		return exmnBzentySeNm;
	}
	public void setExmnBzentySeNm(String exmnBzentySeNm) {
		this.exmnBzentySeNm = exmnBzentySeNm;
	}
	public String getExmnBzentyNm() {
		return exmnBzentyNm;
	}
	public void setExmnBzentyNm(String exmnBzentyNm) {
		this.exmnBzentyNm = exmnBzentyNm;
	}
	public String getExmnBzentyRprsvNm() {
		return exmnBzentyRprsvNm;
	}
	public void setExmnBzentyRprsvNm(String exmnBzentyRprsvNm) {
		this.exmnBzentyRprsvNm = exmnBzentyRprsvNm;
	}
	public String getSpcoSe() {
		return spcoSe;
	}
	public void setSpcoSe(String spcoSe) {
		this.spcoSe = spcoSe;
	}
	public String getExmnBzentyAddr() {
		return exmnBzentyAddr;
	}
	public void setExmnBzentyAddr(String exmnBzentyAddr) {
		this.exmnBzentyAddr = exmnBzentyAddr;
	}
	public String getExmnBzentyPicNm() {
		return exmnBzentyPicNm;
	}
	public void setExmnBzentyPicNm(String exmnBzentyPicNm) {
		this.exmnBzentyPicNm = exmnBzentyPicNm;
	}
	public String getExmnBzentyPicTelno() {
		return exmnBzentyPicTelno;
	}
	public void setExmnBzentyPicTelno(String exmnBzentyPicTelno) {
		this.exmnBzentyPicTelno = exmnBzentyPicTelno;
	}
	public String getExmnBzentyPicEmlAddr() {
		return exmnBzentyPicEmlAddr;
	}
	public void setExmnBzentyPicEmlAddr(String exmnBzentyPicEmlAddr) {
		this.exmnBzentyPicEmlAddr = exmnBzentyPicEmlAddr;
	}
	public String getExmnBzentyPicPswd() {
		return exmnBzentyPicPswd;
	}
	public void setExmnBzentyPicPswd(String exmnBzentyPicPswd) {
		this.exmnBzentyPicPswd = exmnBzentyPicPswd;
	}
	public String getExmnBzentyCrno() {
		return exmnBzentyCrno;
	}
	public void setExmnBzentyCrno(String exmnBzentyCrno) {
		this.exmnBzentyCrno = exmnBzentyCrno;
	}
	public String getMfldCd() {
		return mfldCd;
	}
	public void setMfldCd(String mfldCd) {
		this.mfldCd = mfldCd;
	}
	public String getPssnLcns() {
		return pssnLcns;
	}
	public void setPssnLcns(String pssnLcns) {
		this.pssnLcns = pssnLcns;
	}
	public String getMmadvnSe() {
		return mmadvnSe;
	}
	public void setMmadvnSe(String mmadvnSe) {
		this.mmadvnSe = mmadvnSe;
	}
	public String getTaskTkcgAoNm() {
		return taskTkcgAoNm;
	}
	public void setTaskTkcgAoNm(String taskTkcgAoNm) {
		this.taskTkcgAoNm = taskTkcgAoNm;
	}
	public String getCptlTkcgAoNm() {
		return cptlTkcgAoNm;
	}
	public void setCptlTkcgAoNm(String cptlTkcgAoNm) {
		this.cptlTkcgAoNm = cptlTkcgAoNm;
	}
	public String getAcseCstrnPicNm() {
		return acseCstrnPicNm;
	}
	public void setAcseCstrnPicNm(String acseCstrnPicNm) {
		this.acseCstrnPicNm = acseCstrnPicNm;
	}
	public String getSbmsnBgngYmd() {
		return sbmsnBgngYmd;
	}
	public void setSbmsnBgngYmd(String sbmsnBgngYmd) {
		this.sbmsnBgngYmd = sbmsnBgngYmd;
	}
	public String getSbmsnEndYmd() {
		return sbmsnEndYmd;
	}
	public void setSbmsnEndYmd(String sbmsnEndYmd) {
		this.sbmsnEndYmd = sbmsnEndYmd;
	}
	public String getSbmsnSttsCd() {
		return sbmsnSttsCd;
	}
	public void setSbmsnSttsCd(String sbmsnSttsCd) {
		this.sbmsnSttsCd = sbmsnSttsCd;
	}
	public String getSbmsnYmd() {
		return sbmsnYmd;
	}
	public void setSbmsnYmd(String sbmsnYmd) {
		this.sbmsnYmd = sbmsnYmd;
	}
	public String getRegInstNm() {
		return regInstNm;
	}
	public void setRegInstNm(String regInstNm) {
		this.regInstNm = regInstNm;
	}
	public String getMfldNm() {
		return mfldNm;
	}
	public void setMfldNm(String mfldNm) {
		this.mfldNm = mfldNm;
	}
	public String getMmadvnSeNm() {
		return mmadvnSeNm;
	}
	public void setMmadvnSeNm(String mmadvnSeNm) {
		this.mmadvnSeNm = mmadvnSeNm;
	}
	public String getSbmsnSttsNm() {
		return sbmsnSttsNm;
	}
	public void setSbmsnSttsNm(String sbmsnSttsNm) {
		this.sbmsnSttsNm = sbmsnSttsNm;
	}
	public String getPicChgYn() {
		return picChgYn;
	}
	public void setPicChgYn(String picChgYn) {
		this.picChgYn = picChgYn;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "CcCsmaVo [cirsSn=" + cirsSn + ", regInstCd=" + regInstCd + ", pbancNo=" + pbancNo + ", pbancYmd="
				+ pbancYmd + ", opnbdYmd=" + opnbdYmd + ", cstrnNm=" + cstrnNm + ", cstrnAmt=" + cstrnAmt
				+ ", podrInstNm=" + podrInstNm + ", bidPrtcptCndtn=" + bidPrtcptCndtn + ", bidPrtcptBzentyCnt="
				+ bidPrtcptBzentyCnt + ", qfexmn1rnkBzentyNm=" + qfexmn1rnkBzentyNm + ", exmnBzentySe=" + exmnBzentySe
				+ ", exmnBzentySeNm=" + exmnBzentySeNm + ", exmnBzentyNm=" + exmnBzentyNm + ", exmnBzentyRprsvNm="
				+ exmnBzentyRprsvNm + ", spcoSe=" + spcoSe + ", exmnBzentyAddr=" + exmnBzentyAddr + ", exmnBzentyPicNm="
				+ exmnBzentyPicNm + ", exmnBzentyPicTelno=" + exmnBzentyPicTelno + ", exmnBzentyPicEmlAddr="
				+ exmnBzentyPicEmlAddr + ", exmnBzentyPicPswd=" + exmnBzentyPicPswd + ", exmnBzentyCrno="
				+ exmnBzentyCrno + ", mfldCd=" + mfldCd + ", pssnLcns=" + pssnLcns + ", mmadvnSe=" + mmadvnSe
				+ ", taskTkcgAoNm=" + taskTkcgAoNm + ", cptlTkcgAoNm=" + cptlTkcgAoNm + ", acseCstrnPicNm="
				+ acseCstrnPicNm + ", sbmsnBgngYmd=" + sbmsnBgngYmd + ", sbmsnEndYmd=" + sbmsnEndYmd + ", sbmsnSttsCd="
				+ sbmsnSttsCd + ", sbmsnYmd=" + sbmsnYmd + ", regInstNm=" + regInstNm + ", mfldNm=" + mfldNm
				+ ", mmadvnSeNm=" + mmadvnSeNm + ", sbmsnSttsNm=" + sbmsnSttsNm + ", picChgYn=" + picChgYn
				+ ", toString()=" + super.toString() + "]";
	}




}