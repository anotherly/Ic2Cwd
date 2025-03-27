package kr.co.hivesys.terminal.mapper;

import java.util.HashMap;
import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import kr.co.hivesys.terminal.vo.TerminalVo;

@Mapper("terminalMapper")
public interface TerminalMapper {
	//메인화면 대시보드에서의 단말기목록 
	List<TerminalVo> mainTerminalList(TerminalVo cmsVo);
	List<TerminalVo> selectLog(TerminalVo cmsVo);
	// 메인화면 게이지 차트
	List<TerminalVo> mainGaugeChart(TerminalVo cmsVo);
	// 메인화면 바 차트
	List<TerminalVo> mainBarChart(TerminalVo cmsVo);
	
	List<TerminalVo> selectTerminalList(TerminalVo cmsVo);
	
	List<TerminalVo> selectTerminal(TerminalVo cmsVo);
	
	void insertTerminal(TerminalVo cmsVo);

	void updateTerminal(TerminalVo cmsVo);

	void deleteTerminal(HashMap<String, Object> map);

	void deleteTerminalD(TerminalVo cmsVo);
	
}
