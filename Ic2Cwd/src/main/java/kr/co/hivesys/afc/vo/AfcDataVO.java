package kr.co.hivesys.afc.vo;

import java.util.Date;

public class AfcDataVO {
    private Date rcvDt;       // 수집 시간 (예: 2025-04-03 05:42:00)
    private String stationId; // 역번호
    private String stationName; // 역명
    private int peopleCnt;    // 재차인원
    private double rate;      // 혼잡도(%)
    private int activeCap;    // 열차 구분 (상선=1, 하선=2)
    private String trainNo;

    // Getter/Setter
    public String getStationId() {
		return stationId;
	}
	public void setStationId(String stationId) {
		this.stationId = stationId;
	}
    
    public String getTrainNo() {
        return trainNo;
    }
	public void setTrainNo(String trainNo) {
        this.trainNo = trainNo;
    }
	
    public Date getRcvDt() {
        return rcvDt;
    }
    public void setRcvDt(Date rcvDt) {
        this.rcvDt = rcvDt;
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

    public int getActiveCap() {
        return activeCap;
    }
    public void setActiveCap(int activeCap) {
        this.activeCap = activeCap;
    }
}
