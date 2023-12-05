package com.g3way.sicims.services.sicims000Cm.vo;

public class CmCodeVo extends CmmnVo{

	private String cmcdTy;			//공통코드유형
	private String cmcdSe;			//공통코드구분
	private String cmcdCd;			//공통코드코드
	private String cmcdNm;			//공통코드이름
	private String cmcdExpln;		//공통코드설명
	private String cmcdUnit;		//공통코드단위
	public String getCmcdTy() {
		return cmcdTy;
	}
	public void setCmcdTy(String cmcdTy) {
		this.cmcdTy = cmcdTy;
	}
	public String getCmcdSe() {
		return cmcdSe;
	}
	public void setCmcdSe(String cmcdSe) {
		this.cmcdSe = cmcdSe;
	}
	public String getCmcdCd() {
		return cmcdCd;
	}
	public void setCmcdCd(String cmcdCd) {
		this.cmcdCd = cmcdCd;
	}
	public String getCmcdNm() {
		return cmcdNm;
	}
	public void setCmcdNm(String cmcdNm) {
		this.cmcdNm = cmcdNm;
	}
	public String getCmcdExpln() {
		return cmcdExpln;
	}
	public void setCmcdExpln(String cmcdExpln) {
		this.cmcdExpln = cmcdExpln;
	}
	public String getCmcdUnit() {
		return cmcdUnit;
	}
	public void setCmcdUnit(String cmcdUnit) {
		this.cmcdUnit = cmcdUnit;
	}
	@Override
	public String toString() {
		return "CmCodeVo [cmcdTy=" + cmcdTy + ", cmcdSe=" + cmcdSe + ", cmcdCd=" + cmcdCd + ", cmcdNm=" + cmcdNm
				+ ", cmcdExpln=" + cmcdExpln + ", cmcdUnit=" + cmcdUnit + ", toString()=" + super.toString() + "]";
	}




}
