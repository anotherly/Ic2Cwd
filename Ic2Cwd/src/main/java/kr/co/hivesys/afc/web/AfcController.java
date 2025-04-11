package kr.co.hivesys.afc.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import kr.co.hivesys.comm.excel.ExcelComport;
import kr.co.hivesys.comm.file.FileUploadSave;
import kr.co.hivesys.comm.file.vo.FileVo;
import kr.co.hivesys.terminal.service.TerminalService;
import kr.co.hivesys.terminal.vo.TerminalVo;
import kr.co.hivesys.afc.vo.AfcDataVO;
import kr.co.hivesys.afc.service.AfcService;

@Controller
public class AfcController {

	public static final Logger logger = LoggerFactory.getLogger(AfcController.class);
	public String url="";
	
	@Resource(name="terminalService")
	private TerminalService terminalService;
	
	@Resource(name="afcService")
	private AfcService afcService;
	
	
	//로그조회  전체 데이터 조회
	@RequestMapping(value= "/afc/selectDownLogList.ajax")
	public @ResponseBody ModelAndView downLogList( 
	HttpServletRequest request, HttpServletResponse response
	,@ModelAttribute("TerminalVo") TerminalVo inputVo
	) throws Exception{
		url = request.getRequestURI().substring(request.getContextPath().length()).split(".do")[0];
		
		ModelAndView mav = new ModelAndView("jsonView");
		List<TerminalVo> sList= null;
		try {
			sList = afcService.selectLog(inputVo);
			mav.addObject("data", sList);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(""+e);
			mav.addObject("msg","에러가 발생했습니다.");
		}
		return mav;
	}
	
	//로그조회 금일 데이터 조회
		@RequestMapping(value= "/afc/selectDownLogListToday.ajax")
		public @ResponseBody ModelAndView downLogListToday( 
		HttpServletRequest request, HttpServletResponse response
		,@ModelAttribute("TerminalVo") TerminalVo inputVo
		) throws Exception{
			url = request.getRequestURI().substring(request.getContextPath().length()).split(".do")[0];
			
			ModelAndView mav = new ModelAndView("jsonView");
			List<TerminalVo> sList= null;
			try {
				sList = afcService.selectLogToday(inputVo);
				mav.addObject("data", sList);
				
			} catch (Exception e) {
				e.printStackTrace();
				logger.debug(""+e);
				mav.addObject("msg","에러가 발생했습니다.");
			}
			return mav;
		}
	

		//AFC download
		@RequestMapping(value= "/afc/afcDown.ajax")
		public void afcDown(
		HttpServletRequest req, HttpServletResponse res
		,@ModelAttribute("TerminalVo") TerminalVo inputVo
		) throws Exception{
			List<TerminalVo> sList= null;
			sList = afcService.selectAFC(inputVo);
			List<TerminalVo> sList2= null;
			sList2 = afcService.updownOrder();
			
			//별도의 엑셀 표 생성 함수 
			ExcelComport ex =new ExcelComport();
			XSSFWorkbook workbook = ex.exportExcel7(sList,sList2);
					
			//다운로드를 위한 헤더 핸들링
			String forNum ="";
			if(hasText(inputVo.getFormationNo())) {
				forNum=inputVo.getFormationNo();
			}else {
				forNum="전체";
			}
			String heading = "";
			if(hasText(inputVo.getActiveCap())) {
				if(inputVo.getActiveCap().equals("1")) {
					heading="상행";
				}else {
					heading="하행";
				}
			}else {
				heading="전체";
			}		
			
			String fileName="AFC 편성 : "+forNum+" 방향 : "+heading+" 시간대 : "+inputVo.getsDate()+" ~ "+inputVo.geteDate();
			ex.excelDownload(req,res,fileName,workbook);
			
		}
		
		
	//엑셀다운
	@RequestMapping(value= {"/afc/excelDown.ajax","/afc/excelDownToday.ajax"})
	public void excelDownload(
		HttpServletRequest req, HttpServletResponse res
		,@ModelAttribute("TerminalVo") TerminalVo inputVo
		) throws Exception{
	
		Map<String, Object> model = new HashMap<>();
		
		HashMap<Integer, String> thMap = new HashMap<Integer, String>();
		HashMap<Integer, Map> tbMap = new HashMap<Integer,Map>();
		HashMap<Integer, String> tbSubMap;
		
		url = req.getRequestURI().substring(req.getContextPath().length()).split(".ajax")[0].split("/")[2];
		
		List<TerminalVo> sList= null;
		if(url.equals("excelDownToday")) {
			sList = afcService.selectLogToday(inputVo);
		}else {
			sList = afcService.selectLog(inputVo);
		}
		
		//표제 부분
		thMap.put(0,"시각");
		thMap.put(1,"편성번호");
		thMap.put(2,"상/하행");
		thMap.put(3,"역사명");
		
		thMap.put(4,"kpa1");
		thMap.put(5,"kpa2");
		thMap.put(6,"kpa3");
		thMap.put(7,"kpa4");
		
		thMap.put(8,"1량 평균");
		thMap.put(9,"2량 평균");
		
		thMap.put(10,"1량 계산식");
		thMap.put(11,"2량 계산식");
		thMap.put(12,"1량 보정률");
		thMap.put(13,"2량 보정률");
		
		thMap.put(14,"1량 혼잡률");
		thMap.put(15,"2량 혼잡률");
		
		//표 내용 부분
		for (int i = 0; i < sList.size(); i++) {
			tbSubMap = new HashMap<Integer, String>();
			tbSubMap.put(0,sList.get(i).getRcvDt());
			tbSubMap.put(1,sList.get(i).getFormationNo());
			tbSubMap.put(2,sList.get(i).getActiveCap());
			tbSubMap.put(3,sList.get(i).getStationName());
			
			tbSubMap.put(4,Integer.toString(sList.get(i).getKpa1()));
			tbSubMap.put(5,Integer.toString(sList.get(i).getKpa2()));
			tbSubMap.put(6,Integer.toString(sList.get(i).getKpa3()));
			tbSubMap.put(7,Integer.toString(sList.get(i).getKpa4()));
			
			tbSubMap.put(8,Double.toString(sList.get(i).getAVG1()));
			tbSubMap.put(9,Double.toString(sList.get(i).getAVG2()));
			
			tbSubMap.put(10,Double.toString(sList.get(i).getCal1()));
			tbSubMap.put(11,Double.toString(sList.get(i).getCal2()));
			
			tbSubMap.put(12,Double.toString(sList.get(i).getRange1()));
			tbSubMap.put(13,Double.toString(sList.get(i).getRange2()));
			
			tbSubMap.put(14,Integer.toString(sList.get(i).getRate1()));
			tbSubMap.put(15,Integer.toString(sList.get(i).getRate2()));
			
			tbMap.put(i,tbSubMap);
			
		}
		
		ExcelComport ex =new ExcelComport();
		//별도의 엑셀 표 생성 함수 
		XSSFWorkbook workbook = ex.createDfExcelContent(thMap,tbMap);
		
		//다운로드를 위한 헤더 핸들링
		String forNum ="";
		if(hasText(inputVo.getFormationNo())) {
			forNum=inputVo.getFormationNo();
		}else {
			forNum="전체";
		}
		String heading = "";
		if(hasText(inputVo.getActiveCap())) {
			if(inputVo.getActiveCap().equals("1")) {
				heading="상행";
			}else {
				heading="하행";
			}
		}else {
			heading="전체";
		}		
		String fileName="편성 : "+forNum+" 방향 : "+heading+" 시간대 : "+inputVo.getsDate()+" ~ "+inputVo.geteDate();
		ex.excelDownload(req,res,fileName,workbook);
	}

	//String null,빈공간 체크 
	public static boolean hasText(String str) {
	    return str != null && !str.isEmpty() && containsText(str);
	}
	private static boolean containsText(CharSequence str) {
	    int strLen = str.length();
	    
	    for(int i = 0; i < strLen; ++i) {
	      if (!Character.isWhitespace(str.charAt(i))) {
	        return true;
	      }
	    }
	    
	    return false;
	}
	
	@PostMapping("/afc/uploadExcel")
	@ResponseBody
	public String uploadExcel(@RequestParam("file") MultipartFile file,
	                          @RequestParam("activeCap") int activeCap) {
	    try {
	    	afcService.uploadExcelData(file, activeCap);
	        return "success";
	    } catch (Exception e) {
	        e.printStackTrace();
	        return "fail";
	    }
	}
	
	//응하중 afc 비교목록
	@RequestMapping(value= "/afc/versusList.ajax")
	public @ResponseBody ModelAndView versusList( 
	HttpServletRequest request, HttpServletResponse response
	,@ModelAttribute("AfcDataVO") AfcDataVO inputVo
	) throws Exception{
		url = request.getRequestURI().substring(request.getContextPath().length()).split(".do")[0];
		
		ModelAndView mav = new ModelAndView("jsonView");
		List<AfcDataVO> sList= null;
		try {
			sList = afcService.versusList(inputVo);
			mav.addObject("data", sList);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(""+e);
			mav.addObject("msg","에러가 발생했습니다.");
		}
		return mav;
	}
	
	//엑셀다운
	@RequestMapping(value= "/afc/versusDownload.ajax")
	public void versusDownload(
		HttpServletRequest req, HttpServletResponse res
		,@ModelAttribute("AfcDataVO") AfcDataVO inputVo
		) throws Exception{
	
		Map<String, Object> model = new HashMap<>();
		
		HashMap<Integer, String> thMap = new HashMap<Integer, String>();
		HashMap<Integer, Map> tbMap = new HashMap<Integer,Map>();
		HashMap<Integer, String> tbSubMap;
		
		url = req.getRequestURI().substring(req.getContextPath().length()).split(".ajax")[0].split("/")[2];
		
		List<AfcDataVO> sList= null;
		sList = afcService.versusList(inputVo);
		
		//표제 부분
		thMap.put(0,"시각");
		thMap.put(1,"역명");
		thMap.put(2,"호차");
		thMap.put(3,"재차인원");
		thMap.put(4,"혼잡도");
		
		thMap.put(5,"시각");
		thMap.put(6,"편성번호");
		thMap.put(7,"역명");
		thMap.put(8,"무게총합");
		thMap.put(9,"혼잡도");
		
		thMap.put(10,"시간차(초)");
		
		//표 내용 부분
		for (int i = 0; i < sList.size(); i++) {
			tbSubMap = new HashMap<Integer, String>();
			tbSubMap.put(0,sList.get(i).getRcvDt());
			tbSubMap.put(1,sList.get(i).getStationName());
			tbSubMap.put(2,sList.get(i).getTrainNo());
			tbSubMap.put(3,Integer.toString(sList.get(i).getPeopleCnt()));
			tbSubMap.put(4,Double.toString(sList.get(i).getRate()));
			
			tbSubMap.put(5,sList.get(i).getKpaRcvDt());
			tbSubMap.put(6,sList.get(i).getKpaFormationNo());
			tbSubMap.put(7,sList.get(i).getKpaStationName());
			tbSubMap.put(8,Double.toString(sList.get(i).getSumKpa()));
			tbSubMap.put(9,Double.toString(sList.get(i).getAvgRate()));
			
			tbSubMap.put(10,Integer.toString(sList.get(i).getTimeDiffSec()));
			
			tbMap.put(i,tbSubMap);
			
		}
		
		ExcelComport ex =new ExcelComport();
		//별도의 엑셀 표 생성 함수 
		XSSFWorkbook workbook = ex.createDfExcelContent(thMap,tbMap);
		
		//다운로드를 위한 헤더 핸들링
		String heading = "";
		if(hasText(inputVo.getActiveCap())) {
			if(inputVo.getActiveCap().equals("1")) {
				heading="상행";
			}else {
				heading="하행";
			}
		} else {
			heading="전체";
		}		
		String fileName=inputVo.getsDate()+heading;
		ex.excelDownload(req,res,fileName,workbook);
	}
	
}


