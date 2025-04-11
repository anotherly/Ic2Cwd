package kr.co.hivesys.afc.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kr.co.hivesys.comm.excel.ExcelParserUtil;
import kr.co.hivesys.terminal.vo.TerminalVo;
import kr.co.hivesys.afc.mapper.AfcMapper;
import kr.co.hivesys.afc.service.AfcService;
import kr.co.hivesys.afc.vo.AfcDataVO;
import kr.co.hivesys.afc.vo.AfcDataVO;

@Service("afcService")
public class AfcServiceImpl implements AfcService {
    @Resource(name = "afcMapper")
    private AfcMapper afcMapper;

    @Override
    public List<TerminalVo> updownOrder() {
        return afcMapper.updownOrder();
    }
    
	@Override
	public List<TerminalVo> selectLog(TerminalVo cmsVo) {
		return afcMapper.selectLog(cmsVo);
	}

	@Override
	public List<TerminalVo> selectLogToday(TerminalVo cmsVo) {
		return afcMapper.selectLogToday(cmsVo);
	}

	@Override
	public List<TerminalVo> selectAFC(TerminalVo cmsVo) {
		return afcMapper.selectAFC(cmsVo);
	}

	@Override
    @Transactional
    public void uploadExcelData(MultipartFile file, int activeCap) throws Exception {
        List<AfcDataVO> dataList = ExcelParserUtil.parse(file, activeCap);
        afcMapper.insertAfcDataList(dataList); // MyBatis foreach 방식으로 한번에 처리
    }
    
}