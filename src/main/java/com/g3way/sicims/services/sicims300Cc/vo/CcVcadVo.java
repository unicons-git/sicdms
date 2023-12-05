package com.g3way.sicims.services.sicims300Cc.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.g3way.sicims.services.sicims000Cm.vo.CmmnVo;

/*
 * 위반업체행정처분 정보
 *
*/
public class CcVcadVo extends CmmnVo implements Serializable{
	private static final long serialVersionUID = -6752949026325045750L;

	private BigDecimal	admdspSn;			// 행정처분일련번호
	private String		regInstCd;			// 등록기관코드
	private String		ntfctnYmd;			// 통보일자
	private String		ntfctnInstNm;		// 통보기관명
	private String		rcptDocNo;			// 접수문서번호
	private String		bzentyNm;			// 업체명
	private String		crno;				// 법인등록번호
	private String		lctn;				// 소재지
	private String		vltnCn;				// 위반내용
	private String		vltnSpcnSe;			// 위반혐의구분
	private String		vltnArtcl;			// 위반조항
	private String		cmptnSe;			// 완료구분
	private String		docNo;				// 문서번호
	private String		picNm;				// 담당자명
	private String		prgrsSttsCd;		// 진행상태코드
	private String		dspsCn;				// 처분내용
	private String		dspsBssArtcl;		// 처분근거조항
	private String		dspsTrmnYmd;		// 처분종결일자
	private String		dtlCn;				// 상세내용
	private String		prcsPlan;			// 처리계획

	private String		regInstNm;			// 등록기관명
	private String		cmptnSeNm;			// 완료구분명
	private String		vltnSpcnSeNm;		// 위반혐의구분명
	private String		prgrsSttsNm;		// 진행상태명

	public BigDecimal getAdmdspSn() {
		return admdspSn;
	}
	public void setAdmdspSn(BigDecimal admdspSn) {
		this.admdspSn = admdspSn;
	}
	public String getRegInstCd() {
		return regInstCd;
	}
	public void setRegInstCd(String regInstCd) {
		this.regInstCd = regInstCd;
	}
	public String getNtfctnYmd() {
		return ntfctnYmd;
	}
	public void setNtfctnYmd(String ntfctnYmd) {
		this.ntfctnYmd = ntfctnYmd;
	}
	public String getNtfctnInstNm() {
		return ntfctnInstNm;
	}
	public void setNtfctnInstNm(String ntfctnInstNm) {
		this.ntfctnInstNm = ntfctnInstNm;
	}
	public String getRcptDocNo() {
		return rcptDocNo;
	}
	public void setRcptDocNo(String rcptDocNo) {
		this.rcptDocNo = rcptDocNo;
	}
	public String getBzentyNm() {
		return bzentyNm;
	}
	public void setBzentyNm(String bzentyNm) {
		this.bzentyNm = bzentyNm;
	}
	public String getCrno() {
		return crno;
	}
	public void setCrno(String crno) {
		this.crno = crno;
	}
	public String getLctn() {
		return lctn;
	}
	public void setLctn(String lctn) {
		this.lctn = lctn;
	}
	public String getVltnCn() {
		return vltnCn;
	}
	public void setVltnCn(String vltnCn) {
		this.vltnCn = vltnCn;
	}
	public String getVltnSpcnSe() {
		return vltnSpcnSe;
	}
	public void setVltnSpcnSe(String vltnSpcnSe) {
		this.vltnSpcnSe = vltnSpcnSe;
	}
	public String getVltnArtcl() {
		return vltnArtcl;
	}
	public void setVltnArtcl(String vltnArtcl) {
		this.vltnArtcl = vltnArtcl;
	}
	public String getCmptnSe() {
		return cmptnSe;
	}
	public void setCmptnSe(String cmptnSe) {
		this.cmptnSe = cmptnSe;
	}
	public String getDocNo() {
		return docNo;
	}
	public void setDocNo(String docNo) {
		this.docNo = docNo;
	}
	public String getPicNm() {
		return picNm;
	}
	public void setPicNm(String picNm) {
		this.picNm = picNm;
	}
	public String getPrgrsSttsCd() {
		return prgrsSttsCd;
	}
	public void setPrgrsSttsCd(String prgrsSttsCd) {
		this.prgrsSttsCd = prgrsSttsCd;
	}
	public String getDspsCn() {
		return dspsCn;
	}
	public void setDspsCn(String dspsCn) {
		this.dspsCn = dspsCn;
	}
	public String getDspsBssArtcl() {
		return dspsBssArtcl;
	}
	public void setDspsBssArtcl(String dspsBssArtcl) {
		this.dspsBssArtcl = dspsBssArtcl;
	}
	public String getDspsTrmnYmd() {
		return dspsTrmnYmd;
	}
	public void setDspsTrmnYmd(String dspsTrmnYmd) {
		this.dspsTrmnYmd = dspsTrmnYmd;
	}
	public String getDtlCn() {
		return dtlCn;
	}
	public void setDtlCn(String dtlCn) {
		this.dtlCn = dtlCn;
	}
	public String getPrcsPlan() {
		return prcsPlan;
	}
	public void setPrcsPlan(String prcsPlan) {
		this.prcsPlan = prcsPlan;
	}
	public String getRegInstNm() {
		return regInstNm;
	}
	public void setRegInstNm(String regInstNm) {
		this.regInstNm = regInstNm;
	}
	public String getCmptnSeNm() {
		return cmptnSeNm;
	}
	public void setCmptnSeNm(String cmptnSeNm) {
		this.cmptnSeNm = cmptnSeNm;
	}
	public String getVltnSpcnSeNm() {
		return vltnSpcnSeNm;
	}
	public void setVltnSpcnSeNm(String vltnSpcnSeNm) {
		this.vltnSpcnSeNm = vltnSpcnSeNm;
	}
	public String getPrgrsSttsNm() {
		return prgrsSttsNm;
	}
	public void setPrgrsSttsNm(String prgrsSttsNm) {
		this.prgrsSttsNm = prgrsSttsNm;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "CcVcadVo [admdspSn=" + admdspSn + ", regInstCd=" + regInstCd + ", ntfctnYmd=" + ntfctnYmd
				+ ", ntfctnInstNm=" + ntfctnInstNm + ", rcptDocNo=" + rcptDocNo + ", bzentyNm=" + bzentyNm + ", crno="
				+ crno + ", lctn=" + lctn + ", vltnCn=" + vltnCn + ", vltnSpcnSe=" + vltnSpcnSe + ", vltnArtcl="
				+ vltnArtcl + ", cmptnSe=" + cmptnSe + ", docNo=" + docNo + ", picNm=" + picNm + ", prgrsSttsCd="
				+ prgrsSttsCd + ", dspsCn=" + dspsCn + ", dspsBssArtcl=" + dspsBssArtcl + ", dspsTrmnYmd=" + dspsTrmnYmd
				+ ", dtlCn=" + dtlCn + ", prcsPlan=" + prcsPlan + ", regInstNm=" + regInstNm + ", cmptnSeNm="
				+ cmptnSeNm + ", vltnSpcnSeNm=" + vltnSpcnSeNm + ", prgrsSttsNm=" + prgrsSttsNm + ", toString()="
				+ super.toString() + "]";
	}


}