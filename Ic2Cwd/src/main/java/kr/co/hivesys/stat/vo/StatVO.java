package kr.co.hivesys.stat.vo;

/**
 * @author jdb
 *
 */
public class StatVO {

	private String keyDate;
	//일월년 구분 (화면으로부터)
	private String keyType;
	
	private String weekFlag;
	private String weekName;
	
	private String updownFlag;
	private String updownName;
	
	private int amMaxRate;
	private String amRcvDt;
	
	private int amStationId;
	private String amStationName;
	
	private String amAvgRate;
	private String pmMaxRate;
	
	private String pmRcvDt;
	
	private int pmStationId;
	private String pmStationName;
	
	private int pmAvgRate;

	public String getKeyDate() {
		return keyDate;
	}

	public void setKeyDate(String keyDate) {
		this.keyDate = keyDate;
	}

	public String getKeyType() {
		return keyType;
	}

	public void setKeyType(String keyType) {
		this.keyType = keyType;
	}

	public String getWeekFlag() {
		return weekFlag;
	}

	public void setWeekFlag(String weekFlag) {
		this.weekFlag = weekFlag;
	}

	public String getWeekName() {
		return weekName;
	}

	public void setWeekName(String weekName) {
		this.weekName = weekName;
	}

	public String getUpdownFlag() {
		return updownFlag;
	}

	public void setUpdownFlag(String updownFlag) {
		this.updownFlag = updownFlag;
	}

	public String getUpdownName() {
		return updownName;
	}

	public void setUpdownName(String updownName) {
		this.updownName = updownName;
	}

	public int getAmMaxRate() {
		return amMaxRate;
	}

	public void setAmMaxRate(int amMaxRate) {
		this.amMaxRate = amMaxRate;
	}

	public String getAmRcvDt() {
		return amRcvDt;
	}

	public void setAmRcvDt(String amRcvDt) {
		this.amRcvDt = amRcvDt;
	}

	public int getAmStationId() {
		return amStationId;
	}

	public void setAmStationId(int amStationId) {
		this.amStationId = amStationId;
	}

	public String getAmStationName() {
		return amStationName;
	}

	public void setAmStationName(String amStationName) {
		this.amStationName = amStationName;
	}

	public String getAmAvgRate() {
		return amAvgRate;
	}

	public void setAmAvgRate(String amAvgRate) {
		this.amAvgRate = amAvgRate;
	}

	public String getPmMaxRate() {
		return pmMaxRate;
	}

	public void setPmMaxRate(String pmMaxRate) {
		this.pmMaxRate = pmMaxRate;
	}

	public String getPmRcvDt() {
		return pmRcvDt;
	}

	public void setPmRcvDt(String pmRcvDt) {
		this.pmRcvDt = pmRcvDt;
	}

	public int getPmStationId() {
		return pmStationId;
	}

	public void setPmStationId(int pmStationId) {
		this.pmStationId = pmStationId;
	}

	public String getPmStationName() {
		return pmStationName;
	}

	public void setPmStationName(String pmStationName) {
		this.pmStationName = pmStationName;
	}

	public int getPmAvgRate() {
		return pmAvgRate;
	}

	public void setPmAvgRate(int pmAvgRate) {
		this.pmAvgRate = pmAvgRate;
	}

	@Override
	public String toString() {
		return "StatVO [keyDate=" + keyDate + ", keyType=" + keyType + ", weekFlag=" + weekFlag + ", weekName="
				+ weekName + ", updownFlag=" + updownFlag + ", updownName=" + updownName + ", amMaxRate=" + amMaxRate
				+ ", amRcvDt=" + amRcvDt + ", amStationId=" + amStationId + ", amStationName=" + amStationName
				+ ", amAvgRate=" + amAvgRate + ", pmMaxRate=" + pmMaxRate + ", pmRcvDt=" + pmRcvDt + ", pmStationId="
				+ pmStationId + ", pmStationName=" + pmStationName + ", pmAvgRate=" + pmAvgRate + "]";
	}
	
	

}
