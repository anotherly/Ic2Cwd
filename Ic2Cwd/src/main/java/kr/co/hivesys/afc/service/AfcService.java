package kr.co.hivesys.afc.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import kr.co.hivesys.afc.vo.AfcDataVO;
import kr.co.hivesys.terminal.vo.TerminalVo;

public interface AfcService {
	
	List<TerminalVo> updownOrder();
	// 로그 전체 조회
	List<TerminalVo> selectLog(TerminalVo cmsVo);
	
	// 로그 금일 조회
	List<TerminalVo> selectLogToday(TerminalVo cmsVo);
	
	// AFC 데이터 다운로드 
	List<TerminalVo> selectAFC(TerminalVo cmsVo);

	//AFC 업로드
	void uploadExcelData(MultipartFile file, int activeCap) throws Exception;
	
}
