package kr.co.hivesys.stat.service.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sun.media.jfxmedia.logging.Logger;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import kr.co.hivesys.comm.KeyMapVo;
import kr.co.hivesys.stat.mapper.StatMapper;
import kr.co.hivesys.stat.service.StatService;
import kr.co.hivesys.stat.vo.StkAreaVO;
import kr.co.hivesys.terminal.vo.TerminalVo;
import kr.co.hivesys.stat.vo.ChartVo;
import kr.co.hivesys.stat.vo.ChkDateVO;
import kr.co.hivesys.stat.vo.LogDataVO;
import kr.co.hivesys.stat.vo.MainStVo;
import kr.co.hivesys.stat.vo.MainYTVo;
import kr.co.hivesys.stat.vo.ScatterVO;
import kr.co.hivesys.stat.vo.StatVO;
import kr.co.hivesys.user.mapper.UserMapper;
import kr.co.hivesys.user.service.UserService;
import kr.co.hivesys.user.vo.UserVO;

/**
 * code 서비스 구현 클래스
 * @author 미래전략사업팀 정다빈
 * @since 2020.07.23
 * @version 1.0
 * @see
 *
 * << 개정이력(Modification Information) >>
 *
 *   수정일            수정자              수정내용
 *  -------    -------- ---------------------------
 *  2020.07.23  정다빈           최초 생성
 */

@Service("statService")
public class StatServiceImpl implements StatService{
	
	
	@Resource(name="statMapper")
	private StatMapper statMapper;
	
	
	//메인화면 게이지 차트
	@Override
	public List<ChartVo> mainGaugeChart(TerminalVo cmsVo) {
		return statMapper.mainGaugeChart(cmsVo);
	}
	
	//메인화면 바 차트
	@Override
	public List<ChartVo> mainBarChart(TerminalVo cmsVo) {
		return statMapper.mainBarChart(cmsVo);
	}

	@Override
	public List<ChartVo> statBarData(StatVO inputVo) {
		return statMapper.statBarData(inputVo);
	}

	@Override
	public List<ChartVo> versusData(StatVO inputVo) {
		return statMapper.versusData(inputVo);
	}
	
	@Override
	public List<StatVO>  selectDayLilst(StatVO inputVo) {
		return statMapper.selectDayLilst(inputVo);
	}
	
	
}
