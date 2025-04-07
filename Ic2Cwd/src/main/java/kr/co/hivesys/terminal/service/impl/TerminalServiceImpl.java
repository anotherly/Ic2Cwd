package kr.co.hivesys.terminal.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.co.hivesys.terminal.mapper.TerminalMapper;
import kr.co.hivesys.terminal.service.TerminalService;
import kr.co.hivesys.terminal.vo.TerminalVo;

@Service("terminalService")
public class TerminalServiceImpl implements TerminalService{
	@Resource(name="terminalMapper")
	private TerminalMapper terminalMapper;
	
	//메인화면 대시보드에서의 단말기목록 
	@Override
	public List<TerminalVo> mainTerminalList(TerminalVo cmsVo) {
		return terminalMapper.mainTerminalList(cmsVo);
	}
	
	@Override
	public List<TerminalVo> selectTerminalList(TerminalVo cmsVo) {
		return terminalMapper.selectTerminalList(cmsVo);
	}
	
	@Override
	public List<TerminalVo> selectTerminal(TerminalVo cmsVo) {
		return terminalMapper.selectTerminal(cmsVo);
	}

	
	@Override
	public void insertTerminal(TerminalVo cmsVo) {
		terminalMapper.insertTerminal(cmsVo);
		
	}
	
	@Override
	public void updateTerminal(TerminalVo cmsVo) {
		terminalMapper.updateTerminal(cmsVo);
		
	}
	//사용자 삭제
	public void deleteTerminal(List<String> listArr) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("chkList",listArr);
		terminalMapper.deleteTerminal(map);		
	}
	
	//사용자 삭제 (상세에서)
	@Override
	public void deleteTerminalD(TerminalVo cmsVo) {
		terminalMapper.deleteTerminalD(cmsVo);
	}

	
	// 로그 전체 조회
	@Override
	public List<TerminalVo> selectLog(TerminalVo cmsVo) {
		return terminalMapper.selectLog(cmsVo);
	}
	
	// 로그 금일 조회
	@Override
	public List<TerminalVo> selectLogToday(TerminalVo cmsVo) {
		return terminalMapper.selectLogToday(cmsVo);
	}
	// AFC 데이터 다운로드 
	@Override
	public List<TerminalVo> selectAFC(TerminalVo cmsVo) {
		return terminalMapper.selectAFC(cmsVo);
	}

	@Override
	public List<TerminalVo> updownOrder() {
		return terminalMapper.updownOrder();
	}

}
