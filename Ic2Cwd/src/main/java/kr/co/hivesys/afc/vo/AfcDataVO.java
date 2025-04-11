package kr.co.hivesys.afc.vo;

import java.util.Date;

import kr.co.hivesys.comm.BaseVO;

public class AfcDataVO extends BaseVO{
	//upload
	private Date uptDt;
	//afc
    private String rcvDt;       // 수집 시간 (예: 2025-04-03 05:42:00)
    private String stationId; // 역번호
    private String stationName; // 역명
    private int peopleCnt;    // 재차인원
    private double rate;      // 혼잡도(%)
    private String activeCap;    // 열차 구분 (상선=1, 하선=2)
    private String trainNo;
    //kpa
    private String kpaRcvDt;
    private String kpaFormationNo;
    private String kpaStationId; // 역번호
    private String kpaStationName; // 역명
    private Integer sumKpa; // 전체무게
    private double rate1;      // 혼잡도(%)
    private double rate2;      // 혼잡도(%)
    private double avgRate;      // 혼잡도(%)
    private Integer timeDiffSec; //시간차이
    
	public Date getUptDt() {
		return uptDt;
	}
	public void setUptDt(Date uptDt) {
		this.uptDt = uptDt;
	}
	public String getRcvDt() {
		return rcvDt;
	}
	public void setRcvDt(String rcvDt) {
		this.rcvDt = rcvDt;
	}
	public String getStationId() {
		return stationId;
	}
	public void setStationId(String stationId) {
		this.stationId = stationId;
	}
	public String getStationName() {
		return stationName;
	}
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	public int getPeopleCnt() {
		return peopleCnt;
	}
	public void setPeopleCnt(int peopleCnt) {
		this.peopleCnt = peopleCnt;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public String getActiveCap() {
		return activeCap;
	}
	public void setActiveCap(String activeCap) {
		this.activeCap = activeCap;
	}
	public String getTrainNo() {
		return trainNo;
	}
	public void setTrainNo(String trainNo) {
		this.trainNo = trainNo;
	}
	public String getKpaRcvDt() {
		return kpaRcvDt;
	}
	public void setKpaRcvDt(String kpaRcvDt) {
		this.kpaRcvDt = kpaRcvDt;
	}
	public String getKpaFormationNo() {
		return kpaFormationNo;
	}
	public void setKpaFormationNo(String kpaFormationNo) {
		this.kpaFormationNo = kpaFormationNo;
	}
	public String getKpaStationId() {
		return kpaStationId;
	}
	public void setKpaStationId(String kpaStationId) {
		this.kpaStationId = kpaStationId;
	}
	public String getKpaStationName() {
		return kpaStationName;
	}
	public void setKpaStationName(String kpaStationName) {
		this.kpaStationName = kpaStationName;
	}
	public Integer getSumKpa() {
		return sumKpa;
	}
	public void setSumKpa(Integer sumKpa) {
		this.sumKpa = sumKpa;
	}
	public double getRate1() {
		return rate1;
	}
	public void setRate1(double rate1) {
		this.rate1 = rate1;
	}
	public double getRate2() {
		return rate2;
	}
	public void setRate2(double rate2) {
		this.rate2 = rate2;
	}
	public double getAvgRate() {
		return avgRate;
	}
	public void setAvgRate(double avgRate) {
		this.avgRate = avgRate;
	}
	public Integer getTimeDiffSec() {
		return timeDiffSec;
	}
	public void setTimeDiffSec(Integer timeDiffSec) {
		this.timeDiffSec = timeDiffSec;
	}
    
}
