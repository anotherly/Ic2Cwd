package kr.co.hivesys.stat.service;

import java.util.HashMap;
import java.util.List;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import kr.co.hivesys.stat.vo.StkAreaVO;
import kr.co.hivesys.terminal.vo.TerminalVo;
import kr.co.hivesys.stat.vo.ChartVo;
import kr.co.hivesys.stat.vo.ChkDateVO;
import kr.co.hivesys.stat.vo.LogDataVO;
import kr.co.hivesys.stat.vo.MainStVo;
import kr.co.hivesys.stat.vo.MainYTVo;
import kr.co.hivesys.stat.vo.ScatterVO;
import kr.co.hivesys.stat.vo.StatVO;
import kr.co.hivesys.user.vo.UserVO;

/**
 * code 서비스 클래스
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

public interface StatService {
	
	// 메인화면 게이지 차트
	List<ChartVo> mainGaugeChart(TerminalVo cmsVo);
	// 메인화면 바 차트
	List<ChartVo> mainBarChart(TerminalVo cmsVo);
	
	List<TerminalVo> todayMaxList(TerminalVo cmsVo);
	
	List<ChartVo> statBarData(StatVO inputVo);
	List<ChartVo> versusData(StatVO inputVo);
	List<StatVO>  selectDayLilst(StatVO inputVo);

	
}
