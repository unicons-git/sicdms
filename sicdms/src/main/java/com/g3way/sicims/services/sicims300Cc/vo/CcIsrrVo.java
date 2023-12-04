package com.g3way.sicims.services.sicims300Cc.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.g3way.sicims.services.sicims000Cm.vo.CmmnVo;

/*
 * 불법하도급신고결과 정보
 *
*/
public class CcIsrrVo extends CmmnVo implements Serializable{
	private static final long serialVersionUID = 2641330521878347086L;

	private String		rcptNo;				// 접수번호
	private String		ilgsctCn;			// 불법 하도급내용
	private String		ilgsctTrdr;			// 불법 하도급거래자
	private String		ilgsctStg;			// 불법 하도급단계
	private String		vltnBzentyNm;		// 위반업체명
	private String		podrInstNm;			// 발주기관명
	private String		dspsInstNm;			// 처분기관명
	private String		dclrCn;				// 신고내용
	private String		prcsRslt;			// 처리결과
	private String		rsltRmrk;			// 결과비고
	private BigDecimal	rsltFileId;			// 결과파일ID

	private String		rsltFileNm;			// 결과파일명

	public String getRcptNo() {
		return rcptNo;
	}

	public void setRcptNo(String rcptNo) {
		this.rcptNo = rcptNo;
	}

	public String getIlgsctCn() {
		return ilgsctCn;
	}

	public void setIlgsctCn(String ilgsctCn) {
		this.ilgsctCn = ilgsctCn;
	}

	public String getIlgsctTrdr() {
		return ilgsctTrdr;
	}

	public void setIlgsctTrdr(String ilgsctTrdr) {
		this.ilgsctTrdr = ilgsctTrdr;
	}

	public String getIlgsctStg() {
		return ilgsctStg;
	}

	public void setIlgsctStg(String ilgsctStg) {
		this.ilgsctStg = ilgsctStg;
	}

	public String getVltnBzentyNm() {
		return vltnBzentyNm;
	}

	public void setVltnBzentyNm(String vltnBzentyNm) {
		this.vltnBzentyNm = vltnBzentyNm;
	}

	public String getPodrInstNm() {
		return podrInstNm;
	}

	public void setPodrInstNm(String podrInstNm) {
		this.podrInstNm = podrInstNm;
	}

	public String getDspsInstNm() {
		return dspsInstNm;
	}

	public void setDspsInstNm(String dspsInstNm) {
		this.dspsInstNm = dspsInstNm;
	}

	public String getDclrCn() {
		return dclrCn;
	}

	public void setDclrCn(String dclrCn) {
		this.dclrCn = dclrCn;
	}

	public String getPrcsRslt() {
		return prcsRslt;
	}

	public void setPrcsRslt(String prcsRslt) {
		this.prcsRslt = prcsRslt;
	}

	public String getRsltRmrk() {
		return rsltRmrk;
	}

	public void setRsltRmrk(String rsltRmrk) {
		this.rsltRmrk = rsltRmrk;
	}

	public BigDecimal getRsltFileId() {
		return rsltFileId;
	}

	public void setRsltFileId(BigDecimal rsltFileId) {
		this.rsltFileId = rsltFileId;
	}

	public String getRsltFileNm() {
		return rsltFileNm;
	}

	public void setRsltFileNm(String rsltFileNm) {
		this.rsltFileNm = rsltFileNm;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "CcIsrrVo [rcptNo=" + rcptNo + ", ilgsctCn=" + ilgsctCn + ", ilgsctTrdr=" + ilgsctTrdr + ", ilgsctStg="
				+ ilgsctStg + ", vltnBzentyNm=" + vltnBzentyNm + ", podrInstNm=" + podrInstNm + ", dspsInstNm="
				+ dspsInstNm + ", dclrCn=" + dclrCn + ", prcsRslt=" + prcsRslt + ", rsltRmrk=" + rsltRmrk
				+ ", rsltFileId=" + rsltFileId + ", rsltFileNm=" + rsltFileNm + ", toString()=" + super.toString()
				+ "]";
	}




}