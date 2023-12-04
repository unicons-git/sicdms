package com.g3way.sicims.services.sicims300Cc.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.g3way.sicims.services.sicims000Cm.vo.CmmnVo;

/*
 * 건설업등록기준사전조사결과 정보
 *
*/
public class CcCsrsVo extends CmmnVo implements Serializable{
	private static final long serialVersionUID = -742879800276346787L;

	private BigDecimal	cirsSn;					// 건설업등록기준일련번호
	private String		vstYmd;					// 방문일자
	private String		lastRslt;				// 최종결과
	private String		ofcStbltYn;				// 사무실적합여부
	private String		tcnbltStbltYn;			// 기술능력적합여부
	private String		cptlStbltYn;			// 자본금적합여부
	private String		admdspVltnCn;			// 행정처분위반내용
	private String		admdspLcns;				// 행정처분면허
	private String		admdspVltn;				// 행정처분위반
	private String		admdspInstNm;			// 행정처분기관명
	private String		admdspRqstYmd;			// 행정처분의뢰일자
	private String		admdspCn;				// 행정처분내용
	private String		admdspBgngYmd;			// 행정처분시작일자
	private String		admdspEndYmd;			// 행정처분종료일자
	private String		ctrtExcl;				// 계약제외
	private String		ontcYmd;				// 공문알림일자
	private BigDecimal	ontcAtflId;				// 공문알림첨부파일ID
	private String		rsltRptYmd;				// 결과보고일자
	private BigDecimal	rsltRptAtflId;			// 결과보고첨부파일ID
	private String		acseNtfctnYmd;			// 재무과통보일자
	private BigDecimal	acseNtfctnAtflId;		// 재무과통보첨부파일ID

	private String		ontcAtflNm;				// 공문알림첨부파일명
	private String		rsltRptAtflNm;			// 결과보고첨부파일명
	private String		acseNtfctnAtflNm;		// 재무과통보첨부파일명
	public BigDecimal getCirsSn() {
		return cirsSn;
	}
	public void setCirsSn(BigDecimal cirsSn) {
		this.cirsSn = cirsSn;
	}
	public String getVstYmd() {
		return vstYmd;
	}
	public void setVstYmd(String vstYmd) {
		this.vstYmd = vstYmd;
	}
	public String getLastRslt() {
		return lastRslt;
	}
	public void setLastRslt(String lastRslt) {
		this.lastRslt = lastRslt;
	}
	public String getOfcStbltYn() {
		return ofcStbltYn;
	}
	public void setOfcStbltYn(String ofcStbltYn) {
		this.ofcStbltYn = ofcStbltYn;
	}
	public String getTcnbltStbltYn() {
		return tcnbltStbltYn;
	}
	public void setTcnbltStbltYn(String tcnbltStbltYn) {
		this.tcnbltStbltYn = tcnbltStbltYn;
	}
	public String getCptlStbltYn() {
		return cptlStbltYn;
	}
	public void setCptlStbltYn(String cptlStbltYn) {
		this.cptlStbltYn = cptlStbltYn;
	}
	public String getAdmdspVltnCn() {
		return admdspVltnCn;
	}
	public void setAdmdspVltnCn(String admdspVltnCn) {
		this.admdspVltnCn = admdspVltnCn;
	}
	public String getAdmdspLcns() {
		return admdspLcns;
	}
	public void setAdmdspLcns(String admdspLcns) {
		this.admdspLcns = admdspLcns;
	}
	public String getAdmdspVltn() {
		return admdspVltn;
	}
	public void setAdmdspVltn(String admdspVltn) {
		this.admdspVltn = admdspVltn;
	}
	public String getAdmdspInstNm() {
		return admdspInstNm;
	}
	public void setAdmdspInstNm(String admdspInstNm) {
		this.admdspInstNm = admdspInstNm;
	}
	public String getAdmdspRqstYmd() {
		return admdspRqstYmd;
	}
	public void setAdmdspRqstYmd(String admdspRqstYmd) {
		this.admdspRqstYmd = admdspRqstYmd;
	}
	public String getAdmdspCn() {
		return admdspCn;
	}
	public void setAdmdspCn(String admdspCn) {
		this.admdspCn = admdspCn;
	}
	public String getAdmdspBgngYmd() {
		return admdspBgngYmd;
	}
	public void setAdmdspBgngYmd(String admdspBgngYmd) {
		this.admdspBgngYmd = admdspBgngYmd;
	}
	public String getAdmdspEndYmd() {
		return admdspEndYmd;
	}
	public void setAdmdspEndYmd(String admdspEndYmd) {
		this.admdspEndYmd = admdspEndYmd;
	}
	public String getCtrtExcl() {
		return ctrtExcl;
	}
	public void setCtrtExcl(String ctrtExcl) {
		this.ctrtExcl = ctrtExcl;
	}
	public String getOntcYmd() {
		return ontcYmd;
	}
	public void setOntcYmd(String ontcYmd) {
		this.ontcYmd = ontcYmd;
	}
	public BigDecimal getOntcAtflId() {
		return ontcAtflId;
	}
	public void setOntcAtflId(BigDecimal ontcAtflId) {
		this.ontcAtflId = ontcAtflId;
	}
	public String getRsltRptYmd() {
		return rsltRptYmd;
	}
	public void setRsltRptYmd(String rsltRptYmd) {
		this.rsltRptYmd = rsltRptYmd;
	}
	public BigDecimal getRsltRptAtflId() {
		return rsltRptAtflId;
	}
	public void setRsltRptAtflId(BigDecimal rsltRptAtflId) {
		this.rsltRptAtflId = rsltRptAtflId;
	}
	public String getAcseNtfctnYmd() {
		return acseNtfctnYmd;
	}
	public void setAcseNtfctnYmd(String acseNtfctnYmd) {
		this.acseNtfctnYmd = acseNtfctnYmd;
	}
	public BigDecimal getAcseNtfctnAtflId() {
		return acseNtfctnAtflId;
	}
	public void setAcseNtfctnAtflId(BigDecimal acseNtfctnAtflId) {
		this.acseNtfctnAtflId = acseNtfctnAtflId;
	}
	public String getOntcAtflNm() {
		return ontcAtflNm;
	}
	public void setOntcAtflNm(String ontcAtflNm) {
		this.ontcAtflNm = ontcAtflNm;
	}
	public String getRsltRptAtflNm() {
		return rsltRptAtflNm;
	}
	public void setRsltRptAtflNm(String rsltRptAtflNm) {
		this.rsltRptAtflNm = rsltRptAtflNm;
	}
	public String getAcseNtfctnAtflNm() {
		return acseNtfctnAtflNm;
	}
	public void setAcseNtfctnAtflNm(String acseNtfctnAtflNm) {
		this.acseNtfctnAtflNm = acseNtfctnAtflNm;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "CcCsrsVo [cirsSn=" + cirsSn + ", vstYmd=" + vstYmd + ", lastRslt=" + lastRslt + ", ofcStbltYn="
				+ ofcStbltYn + ", tcnbltStbltYn=" + tcnbltStbltYn + ", cptlStbltYn=" + cptlStbltYn + ", admdspVltnCn="
				+ admdspVltnCn + ", admdspLcns=" + admdspLcns + ", admdspVltn=" + admdspVltn + ", admdspInstNm="
				+ admdspInstNm + ", admdspRqstYmd=" + admdspRqstYmd + ", admdspCn=" + admdspCn + ", admdspBgngYmd="
				+ admdspBgngYmd + ", admdspEndYmd=" + admdspEndYmd + ", ctrtExcl=" + ctrtExcl + ", ontcYmd=" + ontcYmd
				+ ", ontcAtflId=" + ontcAtflId + ", rsltRptYmd=" + rsltRptYmd + ", rsltRptAtflId=" + rsltRptAtflId
				+ ", acseNtfctnYmd=" + acseNtfctnYmd + ", acseNtfctnAtflId=" + acseNtfctnAtflId + ", ontcAtflNm="
				+ ontcAtflNm + ", rsltRptAtflNm=" + rsltRptAtflNm + ", acseNtfctnAtflNm=" + acseNtfctnAtflNm
				+ ", toString()=" + super.toString() + "]";
	}





}