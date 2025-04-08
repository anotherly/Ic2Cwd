package kr.co.hivesys.stat.web;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFCreationHelper;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.co.hivesys.comm.SessionListener;
import kr.co.hivesys.stat.service.StatService;
import kr.co.hivesys.stat.vo.ChartVo;
import kr.co.hivesys.stat.vo.StatVO;
import kr.co.hivesys.terminal.service.TerminalService;
import kr.co.hivesys.terminal.vo.TerminalVo;
import kr.co.hivesys.user.vo.UserVO;
/**
 * statistic 컨트롤러 클래스
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

@Controller
public class StatController {
	
	public static final Logger logger = LoggerFactory.getLogger(StatController.class);
	
	@Resource(name="statService")
	private StatService statService;
	
	@Resource(name="terminalService")
	private TerminalService terminalService;
	
	public String url="";
	
	List<StatVO> svoList =null;
	
	//주소에 맞게 매핑
	@RequestMapping(value="/stat/**/*.do")
	public String statisticUrlMapping(HttpServletRequest request) throws Exception{
		url = request.getRequestURI().substring(request.getContextPath().length()).split(".do")[0];
		logger.debug("▶▶▶▶▶▶▶.보내려는 url : "+url);
		try {
			HttpSession chkSession = request.getSession(false);
			logger.debug("▶▶▶▶▶▶▶.체크세션 : "+chkSession);
			logger.debug("▶▶▶▶▶▶▶.체크세션 id: "+chkSession.getId());
			// 이미 접속한 아이디인지 체크
            // 현재 접속자들 보여주기
            SessionListener.getInstance().printloginUsers();
		} catch (Exception e) {
			System.out.println("에러메시지"+e.getMessage());
		}
		return url;
	}
	

	//메인화면 - 좌측 부
	@RequestMapping(value="/stat/mainChart.ajax")
	public @ResponseBody ModelAndView mainLeft( 
			HttpServletRequest request
			,@ModelAttribute("TerminalVo") TerminalVo inputVo
			,@RequestParam(required=false, value="startNum")String startNum
			,@RequestParam(required=false, value="endNum")String endNum
			,@RequestParam(required=false, value="teamName")String teamCode
			) throws Exception{
		url = request.getRequestURI().substring(request.getContextPath().length()).split(".do")[0];
		ModelAndView mav = new ModelAndView("jsonView");
		
		List<TerminalVo> tList = null;
		List<ChartVo> sList = null;
		List<ChartVo> xyList = null;
		List<TerminalVo> todayList = null;
		try {
			UserVO reqLoginVo = (UserVO) request.getSession().getAttribute("login");
			tList = terminalService.mainTerminalList(inputVo);
			sList = statService.mainGaugeChart(inputVo);
			xyList = statService.mainBarChart(inputVo); // 최대, 평균 막대 그래프
			todayList = statService.todayMaxList(inputVo); // 금일 최대 혼잡률 
			
			if(sList.get(0)!=null) {
				mav.addObject("gaugeCnt", sList.get(0).getGaugePointCnt());
			}else {
				mav.addObject("gaugeCnt", 0);
			}

			
			mav.addObject("todayList", todayList);
			mav.addObject("train", tList.get(0));
			mav.addObject("xyList", xyList);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("에러메시지 : "+e.toString());
		}
		return mav;
	}
	
	//메인화면 - 좌측 바차트
	@RequestMapping(value="/stat/mainBarChart.ajax")
	public @ResponseBody ModelAndView mainBarChart(
			HttpServletRequest request
			//@RequestParam(required=false, value="idArr[]")List<String> listArr
			,@ModelAttribute("TerminalVo") TerminalVo inputVo
			,@RequestParam(required=false, value="startNum")String startNum
			,@RequestParam(required=false, value="endNum")String endNum
			,@RequestParam(required=false, value="teamName")String teamCode
			) throws Exception{
		url = request.getRequestURI().substring(request.getContextPath().length()).split(".do")[0];
		ModelAndView mav = new ModelAndView("jsonView");
		List<ChartVo> sList = null;
		try {
			UserVO reqLoginVo = (UserVO) request.getSession().getAttribute("login");
			
			sList = statService.mainBarChart(inputVo);
			mav.addObject("data", sList);
			
			//현재시각 조회
			Calendar cal = Calendar.getInstance();
			String pattern = "yyyy-MM-dd HH:mm:ss"; 
			SimpleDateFormat formatter = new SimpleDateFormat(pattern);
			mav.addObject("nowDt", formatter.format(cal.getTime()));
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("에러메시지 : "+e.toString());
		}
		return mav;
	}
	
	@RequestMapping(value="/stat/list.ajax")
	public @ResponseBody ModelAndView statList( 
			HttpServletRequest request, HttpServletResponse response
			,@ModelAttribute("StatVO") StatVO inputVo
			) throws Exception{
		url = request.getRequestURI().substring(request.getContextPath().length()).split(".do")[0];
		
		ModelAndView mav = new ModelAndView("jsonView");
		try {
			//통계화면 하단 표
			svoList= statService.selectDayLilst(inputVo);
			mav.addObject("data", svoList);
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(""+e);
			mav.addObject("msg","에러가 발생했습니다.");
		}
		return mav;
	}
	
	@RequestMapping(value="/stat/statChart.ajax")
	public @ResponseBody ModelAndView statChart( 
			HttpServletRequest request, HttpServletResponse response
			,@ModelAttribute("StatVO") StatVO inputVo
			) throws Exception{
		url = request.getRequestURI().substring(request.getContextPath().length()).split(".do")[0];
		List<ChartVo> barList1=new ArrayList<>();
		List<ChartVo> barList2=new ArrayList<>();
		ModelAndView mav = new ModelAndView("jsonView");
		try {
			//통계화면 하단 표
			barList1= statService.statBarData(inputVo);
			barList2= statService.versusData(inputVo);
			mav.addObject("barData1", barList1);
			mav.addObject("barData2", barList2);
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(""+e);
			mav.addObject("msg","에러가 발생했습니다.");
		}
		return mav;
	}
	

	//그래프 캡쳐(div) 및 통계 데이터 다운로드
	@RequestMapping(value = "/capture/*.do", method = RequestMethod.POST)
	public void slip(Map<String, Object> model, XSSFWorkbook workbook, 
			HttpServletRequest req, HttpServletResponse res
			//,@RequestParam(required=false, value="imgVal")String imgVal
			)
            throws Exception {
	}

}
