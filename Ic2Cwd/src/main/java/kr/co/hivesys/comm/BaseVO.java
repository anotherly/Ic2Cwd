package kr.co.hivesys.comm;
//해당 vo는 타 vo 들이 상속받아 사용할 수 있는 클래스로서
//검색 관련 항목(기간,검색유형,검색단어 등) 등을 변수로 생성함
public class BaseVO {
	private String sDate;
	private String eDate;
	private String schType;
	private String schWord;
	@Override
	public String toString() {
		return "BaseVO [sDate=" + sDate + ", eDate=" + eDate + ", schType=" + schType + ", schWord=" + schWord + "]";
	}
	public String getsDate() {
		return sDate;
	}
	public void setsDate(String sDate) {
		this.sDate = sDate;
	}
	public String geteDate() {
		return eDate;
	}
	public void seteDate(String eDate) {
		this.eDate = eDate;
	}
	public String getSchType() {
		return schType;
	}
	public void setSchType(String schType) {
		this.schType = schType;
	}
	public String getSchWord() {
		return schWord;
	}
	public void setSchWord(String schWord) {
		this.schWord = schWord;
	}
	
}
