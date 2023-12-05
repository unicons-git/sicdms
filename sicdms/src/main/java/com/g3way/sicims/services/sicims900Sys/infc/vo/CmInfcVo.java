package com.g3way.sicims.services.sicims900Sys.infc.vo;

/*
 * 자료수집로그정보
 *
*/
public class CmInfcVo {
	private String		clctSn;			// 수집순번
	private String		clctYmd;		// 수집일자
	private String		clctMthdCd;		// 수집방법코드
	private String		infSe;			// 인터페이스구분
	private String		infNm;			// 인터페이스명
	private int			pageNo;			// 페이지번호
	private int			rowsNum;		// 행개수
	private String		inqBgngDt;		// 조회시작일시
	private String		inqEndDt;		// 조회종료일시
	private String		param;			// 패러미터
	private int			clctTnocs;		// 수집총건수
	private int			scsNocs;		// 성공건수
	private int			failNocs;		// 실패건수
	private String		scsYn;			// 성공여부
	private String		failRsn;		// 실패사유
	private String		clctBgngDt;		// 수집시작일시
	private String		clctEndDt;		// 수집종료일시
	private String		updusrId;		// 갱신자ID
	private String		rmrk;			// 비고
	public String getClctSn() {
		return clctSn;
	}
	public void setClctSn(String clctSn) {
		this.clctSn = clctSn;
	}
	public String getClctYmd() {
		return clctYmd;
	}
	public void setClctYmd(String clctYmd) {
		this.clctYmd = clctYmd;
	}
	public String getClctMthdCd() {
		return clctMthdCd;
	}
	public void setClctMthdCd(String clctMthdCd) {
		this.clctMthdCd = clctMthdCd;
	}
	public String getInfSe() {
		return infSe;
	}
	public void setInfSe(String infSe) {
		this.infSe = infSe;
	}
	public String getInfNm() {
		return infNm;
	}
	public void setInfNm(String infNm) {
		this.infNm = infNm;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getRowsNum() {
		return rowsNum;
	}
	public void setRowsNum(int rowsNum) {
		this.rowsNum = rowsNum;
	}
	public String getInqBgngDt() {
		return inqBgngDt;
	}
	public void setInqBgngDt(String inqBgngDt) {
		this.inqBgngDt = inqBgngDt;
	}
	public String getInqEndDt() {
		return inqEndDt;
	}
	public void setInqEndDt(String inqEndDt) {
		this.inqEndDt = inqEndDt;
	}
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	public int getClctTnocs() {
		return clctTnocs;
	}
	public void setClctTnocs(int clctTnocs) {
		this.clctTnocs = clctTnocs;
	}
	public int getScsNocs() {
		return scsNocs;
	}
	public void setScsNocs(int scsNocs) {
		this.scsNocs = scsNocs;
	}
	public int getFailNocs() {
		return failNocs;
	}
	public void setFailNocs(int failNocs) {
		this.failNocs = failNocs;
	}
	public String getScsYn() {
		return scsYn;
	}
	public void setScsYn(String scsYn) {
		this.scsYn = scsYn;
	}
	public String getFailRsn() {
		return failRsn;
	}
	public void setFailRsn(String failRsn) {
		this.failRsn = failRsn;
	}
	public String getClctBgngDt() {
		return clctBgngDt;
	}
	public void setClctBgngDt(String clctBgngDt) {
		this.clctBgngDt = clctBgngDt;
	}
	public String getClctEndDt() {
		return clctEndDt;
	}
	public void setClctEndDt(String clctEndDt) {
		this.clctEndDt = clctEndDt;
	}
	public String getUpdusrId() {
		return updusrId;
	}
	public void setUpdusrId(String updusrId) {
		this.updusrId = updusrId;
	}
	public String getRmrk() {
		return rmrk;
	}
	public void setRmrk(String rmrk) {
		this.rmrk = rmrk;
	}
	@Override
	public String toString() {
		return "CmInfcVo [clctSn=" + clctSn + ", clctYmd=" + clctYmd + ", clctMthdCd=" + clctMthdCd + ", infSe=" + infSe
				+ ", infNm=" + infNm + ", pageNo=" + pageNo + ", rowsNum=" + rowsNum + ", inqBgngDt=" + inqBgngDt
				+ ", inqEndDt=" + inqEndDt + ", param=" + param + ", clctTnocs=" + clctTnocs + ", scsNocs=" + scsNocs
				+ ", failNocs=" + failNocs + ", scsYn=" + scsYn + ", failRsn=" + failRsn + ", clctBgngDt=" + clctBgngDt
				+ ", clctEndDt=" + clctEndDt + ", updusrId=" + updusrId + ", rmrk=" + rmrk + "]";
	}




}