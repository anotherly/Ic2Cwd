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
	private TerminalMapper TerminalMapper;
	
	//메인화면 대시보드에서의 단말기목록 
	@Override
	public List<TerminalVo> mainTerminalList(TerminalVo cmsVo) {
		return TerminalMapper.mainTerminalList(cmsVo);
	}
	
	@Override
	public List<TerminalVo> selectTerminalList(TerminalVo cmsVo) {
		return TerminalMapper.selectTerminalList(cmsVo);
	}
	
	@Override
	public List<TerminalVo> selectTerminal(TerminalVo cmsVo) {
		return TerminalMapper.selectTerminal(cmsVo);
	}

	
	@Override
	public void insertTerminal(TerminalVo cmsVo) {
		TerminalMapper.insertTerminal(cmsVo);
		
	}
	
	@Override
	public void updateTerminal(TerminalVo cmsVo) {
		TerminalMapper.updateTerminal(cmsVo);
		
	}
	//사용자 삭제
	public void deleteTerminal(List<String> listArr) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("chkList",listArr);
		TerminalMapper.deleteTerminal(map);		
	}
	
	//사용자 삭제 (상세에서)
	@Override
	public void deleteTerminalD(TerminalVo cmsVo) {
		TerminalMapper.deleteTerminalD(cmsVo);
	}

}
