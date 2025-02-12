package kr.co.hivesys.statistic.vo;

import kr.co.hivesys.terminal.vo.TerminalVo;

/**
 * @author jdb
 * 성질이 다른 프로젝트별로
 * 해당 chartvo에 필요한 vo를 
 * extend 해서 사용할 것 (필요없다면 굳이)
 */
public class ChartVo extends TerminalVo{
	
	//막대,선,산포도 등
	//x축 값
	private String xVal;
	//y축 값
	private String yVal;
	
	//원형
	//범례 값
	private String pieLegend;
	//범례에 해당하는 % 값
	private String pieVal;

	//게이지
	//표출하고자 하는 수치
	private String gaugePointCnt;

	public String getxVal() {
		return xVal;
	}

	public void setxVal(String xVal) {
		this.xVal = xVal;
	}

	public String getyVal() {
		return yVal;
	}

	public void setyVal(String yVal) {
		this.yVal = yVal;
	}

	public String getPieLegend() {
		return pieLegend;
	}

	public void setPieLegend(String pieLegend) {
		this.pieLegend = pieLegend;
	}

	public String getPieVal() {
		return pieVal;
	}

	public void setPieVal(String pieVal) {
		this.pieVal = pieVal;
	}

	public String getGaugePointCnt() {
		return gaugePointCnt;
	}

	public void setGaugePointCnt(String gaugePointCnt) {
		this.gaugePointCnt = gaugePointCnt;
	}

	@Override
	public String toString() {
		return "ChartVo [xVal=" + xVal + ", yVal=" + yVal + ", pieLegend=" + pieLegend + ", pieVal=" + pieVal
				+ ", gaugePointCnt=" + gaugePointCnt + "]";
	}

		
}
