package com.g3way.sicims.services.sicims910User.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.g3way.sicims.services.sicims000Cm.vo.CmmnVo;

/*
 * 사용자정보
 *
*/
public class CmUserVo extends CmmnVo implements Serializable{

	private static final long serialVersionUID = 3868210232929931052L;
	private String		userId;			// 사용자ID
	private String		userPswd;		// 사용자비밀번호
	private String		hrscRplcKey;	// 인사대체키
	private String		userNm;			// 사용자명
	private String		inst1Cd;		// 기관1코드
	private String		inst2Cd;		// 기관2코드
	private String		deptNm;			// 부서명
	private String		telno;			// 전화번호
	private String		emlAddr;		// 이메일주소
	private String		userSe;			// 사용자구분
	private String 		authrtCd;		// 권한코드
	private String		frstRegDt;		// 최초등록일시
	private String		lastLgnYmd;		// 최종로그인일자
	private String		pswdChgYmd;		// 비밀번호변경일자
	private BigDecimal	pswdErrCnt;		// 비밀번호오류수
	private String		autzrId;		// 승인자ID
	private String		aprvYn;			// 승인여부
	private String		aprvDt;			// 승인일시
	private String		authrtChgYmd;	// 권한변경일자
	private String		whdwlYmd;		// 탈퇴일자
	private String		whdwlRsn;		// 탈퇴사유

	private String      userPswd1;		// 사용자비밀번호1(회원가입 및 암호변경에 사용)
	private String      userPswd2;		// 사용자비밀번호2(회원가입 및 암호변경에 사용)

	private String      inst1Nm;		// 기관1명
	private String      inst2Nm;		// 기관2명
	private String 		userSeNm;		// 사용자구분이름
	private String 		aprvUpdtYn;		// 승인수정여부

	private String 		authrtNm;		// 권한코드명

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserPswd() {
		return userPswd;
	}

	public void setUserPswd(String userPswd) {
		this.userPswd = userPswd;
	}

	public String getHrscRplcKey() {
		return hrscRplcKey;
	}

	public void setHrscRplcKey(String hrscRplcKey) {
		this.hrscRplcKey = hrscRplcKey;
	}

	public String getUserNm() {
		return userNm;
	}

	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}

	public String getInst1Cd() {
		return inst1Cd;
	}

	public void setInst1Cd(String inst1Cd) {
		this.inst1Cd = inst1Cd;
	}

	public String getInst2Cd() {
		return inst2Cd;
	}

	public void setInst2Cd(String inst2Cd) {
		this.inst2Cd = inst2Cd;
	}

	public String getDeptNm() {
		return deptNm;
	}

	public void setDeptNm(String deptNm) {
		this.deptNm = deptNm;
	}

	public String getTelno() {
		return telno;
	}

	public void setTelno(String telno) {
		this.telno = telno;
	}

	public String getEmlAddr() {
		return emlAddr;
	}

	public void setEmlAddr(String emlAddr) {
		this.emlAddr = emlAddr;
	}

	public String getUserSe() {
		return userSe;
	}

	public void setUserSe(String userSe) {
		this.userSe = userSe;
	}

	public String getAuthrtCd() {
		return authrtCd;
	}

	public void setAuthrtCd(String authrtCd) {
		this.authrtCd = authrtCd;
	}

	public String getFrstRegDt() {
		return frstRegDt;
	}

	public void setFrstRegDt(String frstRegDt) {
		this.frstRegDt = frstRegDt;
	}

	public String getLastLgnYmd() {
		return lastLgnYmd;
	}

	public void setLastLgnYmd(String lastLgnYmd) {
		this.lastLgnYmd = lastLgnYmd;
	}

	public String getPswdChgYmd() {
		return pswdChgYmd;
	}

	public void setPswdChgYmd(String pswdChgYmd) {
		this.pswdChgYmd = pswdChgYmd;
	}

	public BigDecimal getPswdErrCnt() {
		return pswdErrCnt;
	}

	public void setPswdErrCnt(BigDecimal pswdErrCnt) {
		this.pswdErrCnt = pswdErrCnt;
	}

	public String getAutzrId() {
		return autzrId;
	}

	public void setAutzrId(String autzrId) {
		this.autzrId = autzrId;
	}

	public String getAprvYn() {
		return aprvYn;
	}

	public void setAprvYn(String aprvYn) {
		this.aprvYn = aprvYn;
	}

	public String getAprvDt() {
		return aprvDt;
	}

	public void setAprvDt(String aprvDt) {
		this.aprvDt = aprvDt;
	}

	public String getAuthrtChgYmd() {
		return authrtChgYmd;
	}

	public void setAuthrtChgYmd(String authrtChgYmd) {
		this.authrtChgYmd = authrtChgYmd;
	}

	public String getWhdwlYmd() {
		return whdwlYmd;
	}

	public void setWhdwlYmd(String whdwlYmd) {
		this.whdwlYmd = whdwlYmd;
	}

	public String getWhdwlRsn() {
		return whdwlRsn;
	}

	public void setWhdwlRsn(String whdwlRsn) {
		this.whdwlRsn = whdwlRsn;
	}

	public String getUserPswd1() {
		return userPswd1;
	}

	public void setUserPswd1(String userPswd1) {
		this.userPswd1 = userPswd1;
	}

	public String getUserPswd2() {
		return userPswd2;
	}

	public void setUserPswd2(String userPswd2) {
		this.userPswd2 = userPswd2;
	}

	public String getInst1Nm() {
		return inst1Nm;
	}

	public void setInst1Nm(String inst1Nm) {
		this.inst1Nm = inst1Nm;
	}

	public String getInst2Nm() {
		return inst2Nm;
	}

	public void setInst2Nm(String inst2Nm) {
		this.inst2Nm = inst2Nm;
	}

	public String getUserSeNm() {
		return userSeNm;
	}

	public void setUserSeNm(String userSeNm) {
		this.userSeNm = userSeNm;
	}

	public String getAprvUpdtYn() {
		return aprvUpdtYn;
	}

	public void setAprvUpdtYn(String aprvUpdtYn) {
		this.aprvUpdtYn = aprvUpdtYn;
	}

	public String getAuthrtNm() {
		return authrtNm;
	}

	public void setAuthrtNm(String authrtNm) {
		this.authrtNm = authrtNm;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "CmUserVo [userId=" + userId + ", userPswd=" + userPswd + ", hrscRplcKey=" + hrscRplcKey + ", userNm="
				+ userNm + ", inst1Cd=" + inst1Cd + ", inst2Cd=" + inst2Cd + ", deptNm=" + deptNm + ", telno=" + telno
				+ ", emlAddr=" + emlAddr + ", userSe=" + userSe + ", authrtCd=" + authrtCd + ", frstRegDt=" + frstRegDt
				+ ", lastLgnYmd=" + lastLgnYmd + ", pswdChgYmd=" + pswdChgYmd + ", pswdErrCnt=" + pswdErrCnt
				+ ", autzrId=" + autzrId + ", aprvYn=" + aprvYn + ", aprvDt=" + aprvDt + ", authrtChgYmd="
				+ authrtChgYmd + ", whdwlYmd=" + whdwlYmd + ", whdwlRsn=" + whdwlRsn + ", userPswd1=" + userPswd1
				+ ", userPswd2=" + userPswd2 + ", inst1Nm=" + inst1Nm + ", inst2Nm=" + inst2Nm + ", userSeNm="
				+ userSeNm + ", aprvUpdtYn=" + aprvUpdtYn + ", authrtNm=" + authrtNm + ", toString()="
				+ super.toString() + "]";
	}


}