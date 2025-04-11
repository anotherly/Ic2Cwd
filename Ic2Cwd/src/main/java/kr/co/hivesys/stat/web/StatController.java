package kr.co.hivesys.stat.web;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;


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
	
///////시작
	//그래프 캡쳐(div) 및 통계 데이터 다운로드
	@RequestMapping(value = "/capture/*.do", method = RequestMethod.POST)
	public void slip(Map<String, Object> model, XSSFWorkbook workbook, 
			HttpServletRequest req, HttpServletResponse res
			//,@RequestParam(required=false, value="imgVal")String imgVal
			) throws Exception {
		url = req.getRequestURI().substring(req.getContextPath().length()).split(".do")[0].split("/")[2];
		
		int x=1;//열 인덱스 -> 0부터 시작 1칸 띄우고 시작
		int y=1;//행 인덱스 -> 0부터 시작 1칸 띄우고 시작
		
		//일월연계 분기
		String title=url.split("_")[0];
		//받아온 일시
		String sndDt=url.split("_")[1];
		//다운로드 파일명
		String chartTitle="";
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String nowDt = sdf.format(date);
			
		res.setCharacterEncoding("UTF-8");
		
		
		// 이미지 시트 생성 
		XSSFSheet sheet1 = workbook.createSheet("image_sheet");
		
		logger.debug("▶▶▶▶▶▶▶.web으로부터 차트이미지 전송받음 : "+title);
		
        //엑셀 행렬 객체 생성
        XSSFRow objRow = null;
        XSSFRow objRow2 = null;
        XSSFCell objCell = null;
        
        // 제목 부분 스타일
        XSSFCellStyle ctStyle = workbook.createCellStyle();
        ctStyle.setAlignment(HorizontalAlignment.CENTER);
        ctStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        XSSFFont ctFont = workbook.createFont();
        ctFont.setBold(true);
        ctFont.setFontHeight((short)(18 * 20));
        ctStyle.setFont(ctFont);

        // 제목(일시) 스타일
        XSSFCellStyle dtStyle = workbook.createCellStyle();
        dtStyle.setAlignment(HorizontalAlignment.CENTER);
        dtStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        XSSFFont dtFont = workbook.createFont();
        dtFont.setBold(true);
        dtFont.setFontHeight((short)(14 * 20));
        dtStyle.setFont(dtFont);

        // 일일 통계 소제목 스타일
        XSSFCellStyle dayTt = workbook.createCellStyle();
        dayTt.setAlignment(HorizontalAlignment.CENTER);
        dayTt.setVerticalAlignment(VerticalAlignment.CENTER);
        XSSFFont daytFont = workbook.createFont();
        daytFont.setBold(true);
        dayTt.setFont(daytFont);

        // 컬럼 제목 스타일 (th)
        XSSFCellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        titleStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
        titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        titleStyle.setBorderRight(BorderStyle.THIN);
        titleStyle.setBorderLeft(BorderStyle.THIN);
        titleStyle.setBorderTop(BorderStyle.THIN);
        titleStyle.setBorderBottom(BorderStyle.THIN);

        // 데이터 셀 스타일 (td)
        XSSFCellStyle contentStyle = workbook.createCellStyle();
        contentStyle.setFillForegroundColor(IndexedColors.LEMON_CHIFFON.getIndex());
        contentStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        contentStyle.setBorderTop(BorderStyle.THIN);
        contentStyle.setBorderBottom(BorderStyle.THIN);
        contentStyle.setBorderLeft(BorderStyle.THIN);
        contentStyle.setBorderRight(BorderStyle.THIN);
        contentStyle.setAlignment(HorizontalAlignment.CENTER);

        
        //엑셀 제목
        if(title.equals("DAY")) {
			chartTitle="일일 통계";
		}else if(title.equals("MONTH")) {
			chartTitle="월별 통계";
			sndDt=sndDt.replace(")", "월)");
		}else if(title.equals("YEAR")) {
			chartTitle="연도별 통계";
			sndDt=sndDt.replace(")", "년)");
		}else if(title.equals("SEA")){
			chartTitle="계절별 통계";
			sndDt=sndDt.replace(")", "년)");
		}else {
			chartTitle="월간(일별) 통계";
			sndDt=sndDt.replace(")", "월)");
		}
        
        x=7;
        //셀 병합
        sheet1.addMergedRegion(new CellRangeAddress(y,y,x,x+3));//행시작, 행종료, 열시작, 열종료(4칸 셀병합)
        objRow = sheet1.createRow(y);
        objCell = objRow.createCell(x);
        objCell.setCellStyle(ctStyle);
        objCell.setCellValue(chartTitle);
        x=(x+3)+1;
        //연월일시
        sheet1.addMergedRegion(new CellRangeAddress(y,y,x,x+3));//행시작, 행종료, 열시작, 열종료
        objCell = objRow.createCell(x);
        objCell.setCellStyle(dtStyle);
        objCell.setCellValue(sndDt);
        //개행
		x=1;y++;
        
		//차트 이미지 세팅
	    try {
	    	//이미지 시작
	    	//데이터링크를 스트링으로
	    	//그림 3개 의 경우 (그림 갯수만금 반드시 for문 만들어야함)
	    	if (req.getParameter("img_val")==null) {
	    		//차트 제목 셋팅
		    	sheet1.addMergedRegion(new CellRangeAddress(y,y,x,x+3));
		    	objRow = sheet1.createRow(y);
	        	objCell = objRow.createCell(x);
	        	objCell.setCellStyle(dayTt);
	            objCell.setCellValue(req.getParameter("title_val0"));
	            x=(x+3)+2;
		    	
	        	sheet1.addMergedRegion(new CellRangeAddress(y,y,x,x+3));
	        	objCell = objRow.createCell(x);
	            objCell.setCellStyle(dayTt);
	            objCell.setCellValue(req.getParameter("title_val1"));
	        	x=(x+3)+2;
	        	
	        	sheet1.addMergedRegion(new CellRangeAddress(y,y,x,x+3));
	            objCell = objRow.createCell(x);
	            objCell.setCellStyle(dayTt);
	            objCell.setCellValue(req.getParameter("title_val2"));
	            //개행
	    		x=1;y++;
	            
				for (int i = 0; i < 3; i++) {
					String idx = Integer.toString(i);
					String paramName = "img_val"+idx;
					String data0 = req.getParameter(paramName);
					//String data = imgVal;
			        //데이터 헤더부분 자름
			        data0 = data0.replaceAll("data:image/png;base64,", "");
			        //바이트로 그림데이터 치환
			        byte[] bytes = Base64.decodeBase64(data0.getBytes());
			        /////////////////////
			        
			        int pictureIdx = workbook.addPicture(bytes, XSSFWorkbook.PICTURE_TYPE_JPEG);
			        XSSFCreationHelper helper = workbook.getCreationHelper();
		            XSSFDrawing drawing = sheet1.createDrawingPatriarch();
		            XSSFClientAnchor anchor = helper.createClientAnchor();
		            XSSFPicture pict = drawing.createPicture(anchor, pictureIdx);
		            
		            // 이미지를 출력할 CELL 위치 선정 & 크기 지정
		            // 화면 차트 배치에 따라 각각 핸들링 필요!!
		            //가로 4~7칸 세로 11칸의 이미지 기준
		            // 같은 pie(원형) 그래프라도 원이 찌그러져나오니 
		            // 가로길이를 다르게 함...
		            if (i==0) {
		            	//x=x+1;
		            	anchor.setCol1(x);//오른쪽으로 3칸 시작점
		                anchor.setRow1(y);//아래로 3칸 시작점
			            pict.resize(0.3,0.8); // 차트 이미지 크기 조절 함수 resize (double 인자 )
			            x=(x+5);
					} else if(i==1){
						anchor.setCol1(x);//오른쪽으로 10칸 시작점
		                anchor.setRow1(y);//아래로 3칸 시작점
		                pict.resize(0.4,0.7);
		                x=(x+4);
					} else{ //마지막
						anchor.setCol1(x);//오른쪽으로 15칸 시작점
		                anchor.setRow1(y);//아래로 3칸 시작점
		                pict.resize(0.4,0.7);//열 10칸,행 16칸
					}
				}
				y=y+11;
			} else { //단일 이미지의 경우
				
				String data = req.getParameter("img_val");
		    	//String data = imgVal;
		        //데이터 헤더부분 자름
		        data = data.replaceAll("data:image/png;base64,", "");
		        //바이트로 그림데이터 치환
		        byte[] bytes = Base64.decodeBase64(data.getBytes());
		        /////////////////////
		        
		        int pictureIdx = workbook.addPicture(bytes, XSSFWorkbook.PICTURE_TYPE_JPEG);
		        XSSFCreationHelper helper = workbook.getCreationHelper();
	            XSSFDrawing drawing = sheet1.createDrawingPatriarch();
	            XSSFClientAnchor anchor = helper.createClientAnchor();
	            
	            // 이미지를 출력할 CELL 위치 선정
	            if(title.equals("MONTH") || title.equals("DATE")) {//월일 경우
	            	anchor.setCol1(2);//오른쪽으로 3칸 시작점
	                anchor.setRow1(3);//아래로 4칸 시작점
	            }else {
	            	anchor.setCol1(3);//오른쪽으로 4칸 시작점
	                anchor.setRow1(2);//아래로 3칸 시작점
	            }
	            
	            // 이미지 그리기
	            XSSFPicture pict = drawing.createPicture(anchor, pictureIdx);
	            
	            // 이미지 사이즈 비율 설정
	            if(title.equals("MONTH") || title.equals("DATE")) {//월일 경우
	            	pict.resize(16,13);//행 13칸,열 15칸
	            }else {
	            	pict.resize(13,15);//행 13칸,열 15칸
	            }
			}
            //이미지 끝
	    	//개행
    		x=1;y++;y++;
            /*표 부분 생성*/
            /*th (컬럼제목)*/
    		/*sheet1.addMergedRegion(new CellRangeAddress(y,y,x,x+2));*/
            objRow = sheet1.createRow(y);
            objRow2 = sheet1.createRow(y+1);
            
            sheet1.addMergedRegion(new CellRangeAddress(y,y+1,1,1));
            
            // 주중 / 주말 헤더
            XSSFCell headCell = objRow.createCell(1);
            headCell.setCellValue("주중/주말");
            headCell.setCellStyle(titleStyle);
            objRow2.createCell(1).setCellStyle(titleStyle);
            
            sheet1.addMergedRegion(new CellRangeAddress(y,y+1,2,2));
            
            // 구분 헤더
            XSSFCell headCell1 = objRow.createCell(2);
            headCell1.setCellValue("구분");
            headCell1.setCellStyle(titleStyle);
            objRow2.createCell(2).setCellStyle(titleStyle);
            
            sheet1.addMergedRegion(new CellRangeAddress(y,y,3,6));
            sheet1.addMergedRegion(new CellRangeAddress(y,y,7,10));
            
            // 오전 RH 헤더
            for(int i=0; i<4; i++) {
            	XSSFCell headCell3 = objRow.createCell(i+3);
            	headCell3.setCellValue("오전 RH");
            	headCell3.setCellStyle(titleStyle);
            }
            
            // 오후 RH 헤더
            for(int i=0; i<4; i++) {
            	XSSFCell headCell3 = objRow.createCell(i+7);
            	headCell3.setCellValue("오후 RH");
            	headCell3.setCellStyle(titleStyle);
            }
            
            XSSFCell headCell4 = objRow2.createCell(3);
            XSSFCell headCell5 = objRow2.createCell(4);
            XSSFCell headCell6 = objRow2.createCell(5);
            XSSFCell headCell7 = objRow2.createCell(6);
            XSSFCell headCell8 = objRow2.createCell(7);
            XSSFCell headCell9 = objRow2.createCell(8);
            XSSFCell headCell10 = objRow2.createCell(9);
            XSSFCell headCell11 = objRow2.createCell(10);
            
            headCell4.setCellValue("최대 혼잡도");
            headCell5.setCellValue("시간대");
            headCell6.setCellValue("구간");
            headCell7.setCellValue("평균 혼잡도");
            headCell8.setCellValue("최대 혼잡도");
            headCell9.setCellValue("시간대");
            headCell10.setCellValue("구간");
            headCell11.setCellValue("평균 혼잡도");
            
            headCell4.setCellStyle(titleStyle);
            headCell5.setCellStyle(titleStyle);
            headCell6.setCellStyle(titleStyle);
            headCell7.setCellStyle(titleStyle);
            headCell8.setCellStyle(titleStyle);
            headCell9.setCellStyle(titleStyle);
            headCell10.setCellStyle(titleStyle);
            headCell11.setCellStyle(titleStyle);
            
            // 셀 넓이 조절
            sheet1.setColumnWidth(1, 4500);
            sheet1.setColumnWidth(2, 4500);
            sheet1.setColumnWidth(3, 4500);
            sheet1.setColumnWidth(4, 6000);
            sheet1.setColumnWidth(5, 4500);
            sheet1.setColumnWidth(6, 4500);
            sheet1.setColumnWidth(7, 4500);
            sheet1.setColumnWidth(8, 6000);
            sheet1.setColumnWidth(9, 4500);
            sheet1.setColumnWidth(10, 4500);
            
    		x=1;
    		y++;y++;
    		
            // 데이터 표에 삽입
    		for(int i = 0; i < svoList.size(); i++) {
        		objRow = sheet1.createRow(y); // 행 생성
        		
    			XSSFCell dataCell1 = objRow.createCell(1);
    			dataCell1.setCellValue(svoList.get(i).getWeekFlag()); // 주중/주말
    			dataCell1.setCellStyle(contentStyle);
    			
    			XSSFCell dataCell2 = objRow.createCell(2); 
    			dataCell2.setCellValue(svoList.get(i).getUpdownFlag());// 구분
    			dataCell2.setCellStyle(contentStyle);
    			
    			
    			XSSFCell dataCell3 = objRow.createCell(3); 
    			dataCell3.setCellValue(svoList.get(i).getAmMaxRate()); // 오전RH 최대 혼잡도
    			dataCell3.setCellStyle(contentStyle);
    			
    			XSSFCell dataCell4 = objRow.createCell(4); 
    			dataCell4.setCellValue(svoList.get(i).getAmRcvDt()); // 오전RH 시간대
    			dataCell4.setCellStyle(contentStyle);
    			
    			XSSFCell dataCell5 = objRow.createCell(5); 
    			dataCell5.setCellValue(svoList.get(i).getAmStationId()); // 오전RH 구간
    			dataCell5.setCellStyle(contentStyle);
    			
    			XSSFCell dataCell6 = objRow.createCell(6); 
    			dataCell6.setCellValue(svoList.get(i).getAmAvgRate()); // 오전RH 평균 혼잡도
    			dataCell6.setCellStyle(contentStyle);
    			
    			XSSFCell dataCell7 = objRow.createCell(7); 
    			dataCell7.setCellValue(svoList.get(i).getPmMaxRate()); // 오후RH 최대 혼잡도
    			dataCell7.setCellStyle(contentStyle);
    			
    			XSSFCell dataCell8 = objRow.createCell(8); 
    			dataCell8.setCellValue(svoList.get(i).getPmRcvDt()); // 오후RH 시간대
    			dataCell8.setCellStyle(contentStyle);
    			
    			XSSFCell dataCell9 = objRow.createCell(9); 
    			dataCell9.setCellValue(svoList.get(i).getPmStationId()); // 오후RH 구간
    			dataCell9.setCellStyle(contentStyle);
    			
    			XSSFCell dataCell10 = objRow.createCell(10); 
    			dataCell10.setCellValue(svoList.get(i).getPmAvgRate()); // 오후RH 평균 혼잡도
    			dataCell10.setCellStyle(contentStyle);
    			
    			y++; // 행 카운트 증가
    		}
    		
    		
    		
           /* for (int i = 0; i < svoList.size(); i++) {
            	sheet1.addMergedRegion(new CellRangeAddress(y,y,x,x+2));
                objRow = sheet1.createRow(y);
                for (int j = 0; j < 3; j++) {
                	objCell = objRow.createCell(x);
                    objCell.setCellStyle(contentStyle);
                    objCell.setCellValue(svoList.get(i).getLteRUsed());
                    x++;
    			}
                sheet1.addMergedRegion(new CellRangeAddress(y,y,x,x+2));
                for (int j = 0; j < 3; j++) {
                	objCell = objRow.createCell(x);
                	objCell.setCellStyle(contentStyle);
                	objCell.setCellValue(svoList.get(i).getLteRIp());
                	x++;
                }
                objCell = objRow.createCell(x);
            	objCell.setCellStyle(contentStyle);
            	objCell.setCellValue(svoList.get(i).getRSSI());
            	x++;
            	objCell = objRow.createCell(x);
            	objCell.setCellStyle(contentStyle);
            	objCell.setCellValue(svoList.get(i).getRSRP());
            	x++;
            	objCell = objRow.createCell(x);
            	objCell.setCellStyle(contentStyle);
            	objCell.setCellValue(svoList.get(i).getRSRQ());
            	x++;
            	objCell = objRow.createCell(x);
            	objCell.setCellStyle(contentStyle);
            	objCell.setCellValue(svoList.get(i).getLteRComUpVal());
            	x++;
            	sheet1.addMergedRegion(new CellRangeAddress(y,y,x,x+1));
                for (int j = 0; j < 2; j++) {
                	objCell = objRow.createCell(x);
                	objCell.setCellStyle(contentStyle);
                	objCell.setCellValue(svoList.get(i).getLteRComDnVal());
                	x++;
                }
                sheet1.addMergedRegion(new CellRangeAddress(y,y,x,x+1));
                for (int j = 0; j < 2; j++) {
                	objCell = objRow.createCell(x);
                	objCell.setCellStyle(contentStyle);
                	objCell.setCellValue(svoList.get(i).getFailureRegYdt());
                	x++;
                }
                sheet1.addMergedRegion(new CellRangeAddress(y,y,x,x+1));
                for (int j = 0; j < 2; j++) {
                	objCell = objRow.createCell(x);
                	objCell.setCellStyle(contentStyle);
                	objCell.setCellValue(svoList.get(i).getFailbackYdt());
                	x++;
                }
            	//개행
            	x=1;y++;
			}*/
            
	    }catch(Exception e) { 
	    	logger.debug(e.toString());
	        e.printStackTrace();
	    }
	    
	 // 파일명 설정 ------------------------------------------///
        String fileName = "download";
        if (model.get("fileName") != null && !((String)model.get("fileName")).equals("")) {
            fileName = (String)model.get("fileName");
        }
        
        
        // 브라우저에 따른 파일명 인코딩
        String userAgent = req.getHeader("User-Agent");
        if (userAgent.indexOf("MSIE") > -1) {
            fileName = URLEncoder.encode(fileName, "UTF-8");
        } else {
            fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
        }
        
        //들어온 request에 따른(일 월 연 계) 파일명 분기처리
        res.setHeader("Content-Disposition", "attachment; filename="+title+"_"+nowDt+".xlsx"); 
        res.setHeader("Content-Description", "JSP Generated Data"); 
        res.setContentType("application/vnd.ms-excel"); 
        
        ServletOutputStream out= res.getOutputStream();
        workbook.write(out);
        out.flush();
        out.close(); 
	}
		
}
