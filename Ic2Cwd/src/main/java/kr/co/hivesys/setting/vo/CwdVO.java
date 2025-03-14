package kr.co.hivesys.setting.vo;

import kr.co.hivesys.comm.BaseVO;

public class CwdVO extends BaseVO{
	
	private int boradStCnt;
	private int maxPsCnt;
	private int standardKg;
	private Double calcNum;
	private int multipNum;
	private int calcLocation;
	
	public int getBoradStCnt() {
		return boradStCnt;
	}
	public void setBoradStCnt(int boradStCnt) {
		this.boradStCnt = boradStCnt;
	}
	public int getMaxPsCnt() {
		return maxPsCnt;
	}
	public void setMaxPsCnt(int maxPsCnt) {
		this.maxPsCnt = maxPsCnt;
	}
	public int getStandardKg() {
		return standardKg;
	}
	public void setStandardKg(int standardKg) {
		this.standardKg = standardKg;
	}
	public int getMultipNum() {
		return multipNum;
	}
	public void setMultipNum(int multipNum) {
		this.multipNum = multipNum;
	}
	public int getCalcLocation() {
		return calcLocation;
	}
	public void setCalcLocation(int calcLocation) {
		this.calcLocation = calcLocation;
	}
	
	public Double getCalcNum() {
		return calcNum;
	}
	public void setCalcNum(Double calcNum) {
		this.calcNum = calcNum;
	}
	@Override
	public String toString() {
		return "CwdVO [boradStCnt=" + boradStCnt + ", maxPsCnt=" + maxPsCnt + ", standardKg=" + standardKg
				+ ", calcNum=" + calcNum + ", multipNum=" + multipNum + ", calcLocation=" + calcLocation + "]";
	}
	
}