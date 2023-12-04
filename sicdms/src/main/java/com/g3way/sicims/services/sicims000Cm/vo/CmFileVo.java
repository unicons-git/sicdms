package com.g3way.sicims.services.sicims000Cm.vo;

import java.math.BigDecimal;


/*
 * 공통코드
 */
public class CmFileVo extends CmmnVo {
    private BigDecimal	fileId;			/* 파일ID */
    private String		fileKd;			/* 파일종류 */
    private String		fileNm;			/* 파일명 */
    private String		fileOrgnlNm;	/* 원본파일명 */
    private String		fileExtn;		/* 파일확장자 */
    private String		filePath;		/* 파일경로 */
    private BigDecimal	fileSz;			/* 파일크기 */
    private String		rfrncId;		/* 참조Id */
	public BigDecimal getFileId() {
		return fileId;
	}
	public void setFileId(BigDecimal fileId) {
		this.fileId = fileId;
	}
	public String getFileKd() {
		return fileKd;
	}
	public void setFileKd(String fileKd) {
		this.fileKd = fileKd;
	}
	public String getFileNm() {
		return fileNm;
	}
	public void setFileNm(String fileNm) {
		this.fileNm = fileNm;
	}
	public String getFileOrgnlNm() {
		return fileOrgnlNm;
	}
	public void setFileOrgnlNm(String fileOrgnlNm) {
		this.fileOrgnlNm = fileOrgnlNm;
	}
	public String getFileExtn() {
		return fileExtn;
	}
	public void setFileExtn(String fileExtn) {
		this.fileExtn = fileExtn;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public BigDecimal getFileSz() {
		return fileSz;
	}
	public void setFileSz(BigDecimal fileSz) {
		this.fileSz = fileSz;
	}
	public String getRfrncId() {
		return rfrncId;
	}
	public void setRfrncId(String rfrncId) {
		this.rfrncId = rfrncId;
	}
	@Override
	public String toString() {
		return "CmFileVo [fileId=" + fileId + ", fileKd=" + fileKd + ", fileNm=" + fileNm + ", fileOrgnlNm="
				+ fileOrgnlNm + ", fileExtn=" + fileExtn + ", filePath=" + filePath + ", fileSz=" + fileSz
				+ ", rfrncId=" + rfrncId + ", toString()=" + super.toString() + "]";
	}



}
