package kr.co.hivesys.terminal.vo;

import java.util.Date;

public class AfcDataVO {
    private Date rcvDt;       // 수집 시간 (예: 2025-04-03 05:42:00)
    private String stationId; // 역명
    private int peopleCnt;    // 재차인원
    private double rate;      // 혼잡도(%)
    private int activeCap;    // 열차 구분 (상선=1, 하선=2)

    // Getter/Setter
    public Date getRcvDt() {
        return rcvDt;
    }
    public void setRcvDt(Date rcvDt) {
        this.rcvDt = rcvDt;
    }

    public String getStationId() {
        return stationId;
    }
    public void setStationId(String stationId) {
        this.stationId = stationId;
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
