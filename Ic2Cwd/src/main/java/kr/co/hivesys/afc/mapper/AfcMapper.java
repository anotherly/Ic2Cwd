package kr.co.hivesys.afc.mapper;

import java.util.HashMap;
import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import kr.co.hivesys.afc.vo.AfcDataVO;
import kr.co.hivesys.terminal.vo.TerminalVo;

@Mapper("afcMapper")
public interface AfcMapper {
	
	List<TerminalVo> updownOrder();
	// 로그 전체 조회
	List<TerminalVo> selectLog(TerminalVo cmsVo);
	
	// 로그 금일 조회
	List<TerminalVo> selectLogToday(TerminalVo cmsVo);
	
	//AFC 양식 다운로드
	List<TerminalVo> selectAFC(TerminalVo cmsVo);
	
	int insertAfcData(AfcDataVO vo);
	int insertAfcDataList(List<AfcDataVO> dataList);
	
	//응하중 afc 비교목록
	List<AfcDataVO> versusList(AfcDataVO inputVo);
	
}
