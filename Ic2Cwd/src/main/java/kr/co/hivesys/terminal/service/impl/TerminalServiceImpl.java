package kr.co.hivesys.terminal.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kr.co.hivesys.comm.excel.ExcelParserUtil;
import kr.co.hivesys.terminal.mapper.TerminalMapper;
import kr.co.hivesys.terminal.service.TerminalService;
import kr.co.hivesys.terminal.vo.AfcDataVO;
import kr.co.hivesys.terminal.vo.TerminalVo;

@Service("terminalService")
public class TerminalServiceImpl implements TerminalService {

    @Resource(name = "sqlSessionTemplate")
    private SqlSessionTemplate sqlSessionTemplate;

    @Resource(name = "terminalMapper")
    private TerminalMapper terminalMapper;

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

    @Override
    public void deleteTerminal(List<String> listArr) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("chkList", listArr);
        terminalMapper.deleteTerminal(map);
    }

    @Override
    public void deleteTerminalD(TerminalVo cmsVo) {
        terminalMapper.deleteTerminalD(cmsVo);
    }

    @Override
    public List<TerminalVo> selectLog(TerminalVo cmsVo) {
        return terminalMapper.selectLog(cmsVo);
    }

    @Override
    public List<TerminalVo> selectLogToday(TerminalVo cmsVo) {
        return terminalMapper.selectLogToday(cmsVo);
    }

    @Override
    public List<TerminalVo> selectAFC(TerminalVo cmsVo) {
        return terminalMapper.selectAFC(cmsVo);
    }

    @Override
    @Transactional
    public void uploadExcelData(MultipartFile file, int activeCap) throws Exception {
        List<AfcDataVO> dataList = ExcelParserUtil.parse(file, activeCap);

        SqlSession batchSession = sqlSessionTemplate.getSqlSessionFactory()
                .openSession(ExecutorType.BATCH, false);

        try {
            TerminalMapper mapper = batchSession.getMapper(TerminalMapper.class);
            int batchSize = 500;

            for (int i = 0; i < dataList.size(); i++) {
                mapper.insertAfcData(dataList.get(i));

                if (i % batchSize == 0) {
                    batchSession.flushStatements();
                }
            }

            batchSession.flushStatements();
            batchSession.commit();
        } catch (Exception e) {
            batchSession.rollback();
            throw e;
        } finally {
            batchSession.close();
        }
    }

    @Override
    public List<TerminalVo> updownOrder() {
        return terminalMapper.updownOrder();
    }
}