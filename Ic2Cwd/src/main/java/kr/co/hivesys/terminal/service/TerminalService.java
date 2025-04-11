package kr.co.hivesys.terminal.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import kr.co.hivesys.terminal.vo.TerminalVo;

public interface TerminalService {
	
	//메인화면 대시보드에서의 단말기목록 
	List<TerminalVo> mainTerminalList(TerminalVo cmsVo);
	
	List<TerminalVo> selectTerminalList(TerminalVo cmsVo);
	
	List<TerminalVo> selectTerminal(TerminalVo cmsVo);

	void insertTerminal(TerminalVo cmsVo);
	
	void updateTerminal(TerminalVo cmsVo);
	
	public void deleteTerminal(List<String> listArr);

	public void deleteTerminalD(TerminalVo cmsVo);

	
	
}
