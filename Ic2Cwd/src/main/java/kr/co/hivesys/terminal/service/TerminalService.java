package kr.co.hivesys.terminal.service;

import java.util.List;

import kr.co.hivesys.terminal.vo.TerminalVo;

public interface TerminalService {
	
	//메인화면 대시보드에서의 단말기목록 
	List<TerminalVo> mainTerminalList(TerminalVo cmsVo);

	List<TerminalVo> selectTerminalList(TerminalVo cmsVo);
	
	List<TerminalVo> selectTerminal(TerminalVo cmsVo);
	
	String creComId();

	void insertTerminal(TerminalVo cmsVo);
	
	void updateTerminal(TerminalVo cmsVo);
	
	public void deleteTerminal(List<String> listArr);

	public void deleteTerminalD(String lteIp);
	
	TerminalVo chartQ(TerminalVo inputVo);
	List<TerminalVo> chartD(TerminalVo inputVo);
	
	List<TerminalVo>  mainChart1(TerminalVo inputVo);
	List<TerminalVo>  mainChart2(TerminalVo inputVo);
	List<TerminalVo>  barChart(TerminalVo inputVo);
	
	List<TerminalVo>  userRsrp(TerminalVo inputVo);
	List<TerminalVo>  userRsrq(TerminalVo inputVo);

	List<TerminalVo> chartQFirst(TerminalVo inputVo);

	List<TerminalVo> routerTeamCnt(TerminalVo terminalVo);

	TerminalVo deviceReload(TerminalVo inputVo);
}
