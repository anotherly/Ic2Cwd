package kr.co.hivesys.terminal.vo;

import kr.co.hivesys.comm.BaseVO;
import kr.co.hivesys.setting.vo.AspVO;

public class TerminalVo  extends AspVO{
	
	//차상단말기 정보
	private String trainNo;
	private String formationNo;
	private String deviceIp;
	private String deviceMac;
	private String insLocTxt;
	private String deviceUsed;
	private String gatewayIp;
	private String deviceUser;
	private String etc;
	private String regDt;
	
	//차상단말기 로그
	private String rcvDt;
	
	private String tno2;
	private String fno2;
	
	private String activeCap;
	private String trainCnt;
	private String trainAddCnt;
	
	private int kpa1;
	private int kpa2;
	private int kpa3;
	private int kpa4;
	private int kpa5;
	private int kpa6;
	private int kpa7;
	private int kpa8;
	
	private int sumKpa;
	
	private int rate;
	private int rate1;
	private int rate2;
	private int rate3;
	private int rate4;
	private int rate5;
	private int rate6;
	private int rate7;
	private int rate8;
	
	//보통,주의,혼잡,심각의 4단계
	private int cwd1;
	private int cwd2;
	private int cwd3;
	private int cwd4;
	private int cwd5;
	private int cwd6;
	private int cwd7;
	private int cwd8;
	
	private int stationId;
	private String stationName;

	private double avgCwd;
	
	private String errorRange;
	//
	private String dufCnt;
	
	private double AVG1;
	private double AVG2;
	
	private double cal1;
	private double cal2;
	private double range1;
	private double range2;
	
	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public int getSumKpa() {
		return sumKpa;
	}

	public void setSumKpa(int sumKpa) {
		this.sumKpa = sumKpa;
	}

	public double getAVG1() {
		return AVG1;
	}

	public void setAVG1(double aVG1) {
		AVG1 = aVG1;
	}

	public double getAVG2() {
		return AVG2;
	}

	public void setAVG2(double aVG2) {
		AVG2 = aVG2;
	}

	public double getCal1() {
		return cal1;
	}

	public void setCal1(double cal1) {
		this.cal1 = cal1;
	}

	public double getCal2() {
		return cal2;
	}

	public void setCal2(double cal2) {
		this.cal2 = cal2;
	}

	public double getRange1() {
		return range1;
	}

	public void setRange1(double range1) {
		this.range1 = range1;
	}

	public double getRange2() {
		return range2;
	}

	public void setRange2(double range2) {
		this.range2 = range2;
	}

	public String getDufCnt() {
		return dufCnt;
	}

	public void setDufCnt(String dufCnt) {
		this.dufCnt = dufCnt;
	}

	public String getTrainNo() {
		return trainNo;
	}

	public void setTrainNo(String trainNo) {
		this.trainNo = trainNo;
	}

	public String getFormationNo() {
		return formationNo;
	}

	public void setFormationNo(String formationNo) {
		this.formationNo = formationNo;
	}

	public String getDeviceIp() {
		return deviceIp;
	}

	public void setDeviceIp(String deviceIp) {
		this.deviceIp = deviceIp;
	}

	public String getDeviceMac() {
		return deviceMac;
	}

	public void setDeviceMac(String deviceMac) {
		this.deviceMac = deviceMac;
	}

	public String getInsLocTxt() {
		return insLocTxt;
	}

	public void setInsLocTxt(String insLocTxt) {
		this.insLocTxt = insLocTxt;
	}

	public String getDeviceUsed() {
		return deviceUsed;
	}

	public void setDeviceUsed(String deviceUsed) {
		this.deviceUsed = deviceUsed;
	}

	public String getGatewayIp() {
		return gatewayIp;
	}

	public void setGatewayIp(String gatewayIp) {
		this.gatewayIp = gatewayIp;
	}

	public String getDeviceUser() {
		return deviceUser;
	}

	public void setDeviceUser(String deviceUser) {
		this.deviceUser = deviceUser;
	}

	public String getEtc() {
		return etc;
	}

	public void setEtc(String etc) {
		this.etc = etc;
	}

	public String getRegDt() {
		return regDt;
	}

	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}

	public String getRcvDt() {
		return rcvDt;
	}

	public void setRcvDt(String rcvDt) {
		this.rcvDt = rcvDt;
	}

	public String getTno2() {
		return tno2;
	}

	public void setTno2(String tno2) {
		this.tno2 = tno2;
	}

	public String getFno2() {
		return fno2;
	}

	public void setFno2(String fno2) {
		this.fno2 = fno2;
	}

	public String getActiveCap() {
		return activeCap;
	}

	public void setActiveCap(String activeCap) {
		this.activeCap = activeCap;
	}

	public String getTrainCnt() {
		return trainCnt;
	}

	public void setTrainCnt(String trainCnt) {
		this.trainCnt = trainCnt;
	}

	public String getTrainAddCnt() {
		return trainAddCnt;
	}

	public void setTrainAddCnt(String trainAddCnt) {
		this.trainAddCnt = trainAddCnt;
	}

	public int getKpa1() {
		return kpa1;
	}

	public void setKpa1(int kpa1) {
		this.kpa1 = kpa1;
	}

	public int getKpa2() {
		return kpa2;
	}

	public void setKpa2(int kpa2) {
		this.kpa2 = kpa2;
	}

	public int getKpa3() {
		return kpa3;
	}

	public void setKpa3(int kpa3) {
		this.kpa3 = kpa3;
	}

	public int getKpa4() {
		return kpa4;
	}

	public void setKpa4(int kpa4) {
		this.kpa4 = kpa4;
	}

	public int getKpa5() {
		return kpa5;
	}

	public void setKpa5(int kpa5) {
		this.kpa5 = kpa5;
	}

	public int getKpa6() {
		return kpa6;
	}

	public void setKpa6(int kpa6) {
		this.kpa6 = kpa6;
	}

	public int getKpa7() {
		return kpa7;
	}

	public void setKpa7(int kpa7) {
		this.kpa7 = kpa7;
	}

	public int getKpa8() {
		return kpa8;
	}

	public void setKpa8(int kpa8) {
		this.kpa8 = kpa8;
	}

	public int getRate1() {
		return rate1;
	}

	public void setRate1(int rate1) {
		this.rate1 = rate1;
	}

	public int getRate2() {
		return rate2;
	}

	public void setRate2(int rate2) {
		this.rate2 = rate2;
	}

	public int getRate3() {
		return rate3;
	}

	public void setRate3(int rate3) {
		this.rate3 = rate3;
	}

	public int getRate4() {
		return rate4;
	}

	public void setRate4(int rate4) {
		this.rate4 = rate4;
	}

	public int getRate5() {
		return rate5;
	}

	public void setRate5(int rate5) {
		this.rate5 = rate5;
	}

	public int getRate6() {
		return rate6;
	}

	public void setRate6(int rate6) {
		this.rate6 = rate6;
	}

	public int getRate7() {
		return rate7;
	}

	public void setRate7(int rate7) {
		this.rate7 = rate7;
	}

	public int getRate8() {
		return rate8;
	}

	public void setRate8(int rate8) {
		this.rate8 = rate8;
	}

	public int getCwd1() {
		return cwd1;
	}

	public void setCwd1(int cwd1) {
		this.cwd1 = cwd1;
	}

	public int getCwd2() {
		return cwd2;
	}

	public void setCwd2(int cwd2) {
		this.cwd2 = cwd2;
	}

	public int getCwd3() {
		return cwd3;
	}

	public void setCwd3(int cwd3) {
		this.cwd3 = cwd3;
	}

	public int getCwd4() {
		return cwd4;
	}

	public void setCwd4(int cwd4) {
		this.cwd4 = cwd4;
	}

	public int getCwd5() {
		return cwd5;
	}

	public void setCwd5(int cwd5) {
		this.cwd5 = cwd5;
	}

	public int getCwd6() {
		return cwd6;
	}

	public void setCwd6(int cwd6) {
		this.cwd6 = cwd6;
	}

	public int getCwd7() {
		return cwd7;
	}

	public void setCwd7(int cwd7) {
		this.cwd7 = cwd7;
	}

	public int getCwd8() {
		return cwd8;
	}

	public void setCwd8(int cwd8) {
		this.cwd8 = cwd8;
	}

	public int getStationId() {
		return stationId;
	}

	public void setStationId(int stationId) {
		this.stationId = stationId;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public double getAvgCwd() {
		return avgCwd;
	}

	public void setAvgCwd(double avgCwd) {
		this.avgCwd = avgCwd;
	}

	public String getErrorRange() {
		return errorRange;
	}

	public void setErrorRange(String errorRange) {
		this.errorRange = errorRange;
	}

	@Override
	public String toString() {
		return "TerminalVo [trainNo=" + trainNo + ", formationNo=" + formationNo + ", deviceIp=" + deviceIp
				+ ", deviceMac=" + deviceMac + ", insLocTxt=" + insLocTxt + ", deviceUsed=" + deviceUsed
				+ ", gatewayIp=" + gatewayIp + ", deviceUser=" + deviceUser + ", etc=" + etc + ", regDt=" + regDt
				+ ", rcvDt=" + rcvDt + ", tno2=" + tno2 + ", fno2=" + fno2 + ", activeCap=" + activeCap + ", trainCnt="
				+ trainCnt + ", trainAddCnt=" + trainAddCnt + ", kpa1=" + kpa1 + ", kpa2=" + kpa2 + ", kpa3=" + kpa3
				+ ", kpa4=" + kpa4 + ", kpa5=" + kpa5 + ", kpa6=" + kpa6 + ", kpa7=" + kpa7 + ", kpa8=" + kpa8
				+ ", rate1=" + rate1 + ", rate2=" + rate2 + ", rate3=" + rate3 + ", rate4=" + rate4 + ", rate5=" + rate5
				+ ", rate6=" + rate6 + ", rate7=" + rate7 + ", rate8=" + rate8 + ", cwd1=" + cwd1 + ", cwd2=" + cwd2
				+ ", cwd3=" + cwd3 + ", cwd4=" + cwd4 + ", cwd5=" + cwd5 + ", cwd6=" + cwd6 + ", cwd7=" + cwd7
				+ ", cwd8=" + cwd8 + ", stationId=" + stationId + ", stationName=" + stationName + ", avgCwd=" + avgCwd
				+ ", errorRange=" + errorRange + "]";
	}
	
}