package com.g3way.sicims.services.sicims000Cm.vo;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/*
 * 기본 VO
 */
public class CmmnVo {
	private long 		seqNo;				/* 행번호 */
	private long 		page;				/* 페이지 */
	private long 		totalCount;			/* 전체검색건수 */

	private String		useYn;				/* 사용여부 */
	private String		updtYmd;			/* 갱신일자 */
	private String		updusrId;			/* 갱신자ID */
	private String		updusrIp;			/* 갱신자IP */
	private String		rmrk;				/* 비고 */

	/*로그에서 사용하는 변수*/
	private String 		logPage;			/* 접속 페이지 */
	private String 		logMode;			/* 로그모드 */
	private BigDecimal 	mlogSn;				/* 메인로그순번 */
	private BigDecimal 	slogSn;				/* 서브로그순번 */
	private String 		tblNm;				/* 테이블명 */
	private String 		mlogScrnNm;			/* 메인로그화면명 */
	private String 		sqlWhrCn;			/* SQL조건절내용 */
	private Object[] 	whrParam;			/* where 조건절 param */
	private Object 		colNm;				/* 컬럼명 */
	private Object 		updtBfrCn;			/* 갱신전내용 */
	private Object 		updtAftrCn;			/* 갱신후내용 */

	private List<HashMap<String, Object>> 	updtBeforeDataList;
	private List<HashMap<String, Object>> 	updtAfterDataList;
	private HashMap<String, Object> 		fixUpdtAfterDataColum;
	public long getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(long seqNo) {
		this.seqNo = seqNo;
	}
	public long getPage() {
		return page;
	}
	public void setPage(long page) {
		this.page = page;
	}
	public long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
	public String getUseYn() {
		return useYn;
	}
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
	public String getUpdtYmd() {
		return updtYmd;
	}
	public void setUpdtYmd(String updtYmd) {
		this.updtYmd = updtYmd;
	}
	public String getUpdusrId() {
		return updusrId;
	}
	public void setUpdusrId(String updusrId) {
		this.updusrId = updusrId;
	}
	public String getUpdusrIp() {
		return updusrIp;
	}
	public void setUpdusrIp(String updusrIp) {
		this.updusrIp = updusrIp;
	}
	public String getRmrk() {
		return rmrk;
	}
	public void setRmrk(String rmrk) {
		this.rmrk = rmrk;
	}
	public String getLogPage() {
		return logPage;
	}
	public void setLogPage(String logPage) {
		this.logPage = logPage;
	}
	public String getLogMode() {
		return logMode;
	}
	public void setLogMode(String logMode) {
		this.logMode = logMode;
	}
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
	public String getTblNm() {
		return tblNm;
	}
	public void setTblNm(String tblNm) {
		this.tblNm = tblNm;
	}
	public String getMlogScrnNm() {
		return mlogScrnNm;
	}
	public void setMlogScrnNm(String mlogScrnNm) {
		this.mlogScrnNm = mlogScrnNm;
	}
	public String getSqlWhrCn() {
		return sqlWhrCn;
	}
	public void setSqlWhrCn(String sqlWhrCn) {
		this.sqlWhrCn = sqlWhrCn;
	}
	public Object[] getWhrParam() {
		return whrParam;
	}
	public void setWhrParam(Object[] whrParam) {
		this.whrParam = whrParam;
	}
	public Object getColNm() {
		return colNm;
	}
	public void setColNm(Object colNm) {
		this.colNm = colNm;
	}
	public Object getUpdtBfrCn() {
		return updtBfrCn;
	}
	public void setUpdtBfrCn(Object updtBfrCn) {
		this.updtBfrCn = updtBfrCn;
	}
	public Object getUpdtAftrCn() {
		return updtAftrCn;
	}
	public void setUpdtAftrCn(Object updtAftrCn) {
		this.updtAftrCn = updtAftrCn;
	}
	public List<HashMap<String, Object>> getUpdtBeforeDataList() {
		return updtBeforeDataList;
	}
	public void setUpdtBeforeDataList(List<HashMap<String, Object>> updtBeforeDataList) {
		this.updtBeforeDataList = updtBeforeDataList;
	}
	public List<HashMap<String, Object>> getUpdtAfterDataList() {
		return updtAfterDataList;
	}
	public void setUpdtAfterDataList(List<HashMap<String, Object>> updtAfterDataList) {
		this.updtAfterDataList = updtAfterDataList;
	}
	public HashMap<String, Object> getFixUpdtAfterDataColum() {
		return fixUpdtAfterDataColum;
	}
	public void setFixUpdtAfterDataColum(HashMap<String, Object> fixUpdtAfterDataColum) {
		this.fixUpdtAfterDataColum = fixUpdtAfterDataColum;
	}
	@Override
	public String toString() {
		return "CmmnVo [seqNo=" + seqNo + ", page=" + page + ", totalCount=" + totalCount + ", useYn=" + useYn
				+ ", updtYmd=" + updtYmd + ", updusrId=" + updusrId + ", updusrIp=" + updusrIp + ", rmrk=" + rmrk
				+ ", logPage=" + logPage + ", logMode=" + logMode + ", mlogSn=" + mlogSn + ", slogSn=" + slogSn
				+ ", tblNm=" + tblNm + ", mlogScrnNm=" + mlogScrnNm + ", sqlWhrCn=" + sqlWhrCn + ", whrParam="
				+ Arrays.toString(whrParam) + ", colNm=" + colNm + ", updtBfrCn=" + updtBfrCn + ", updtAftrCn="
				+ updtAftrCn + ", updtBeforeDataList=" + updtBeforeDataList + ", updtAfterDataList=" + updtAfterDataList
				+ ", fixUpdtAfterDataColum=" + fixUpdtAfterDataColum + "]";
	}


}
