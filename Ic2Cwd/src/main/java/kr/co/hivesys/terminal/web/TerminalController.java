package kr.co.hivesys.terminal.web;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import kr.co.hivesys.comm.excel.ExcelComport;
import kr.co.hivesys.comm.file.FileUploadSave;
import kr.co.hivesys.comm.file.vo.FileVo;
import kr.co.hivesys.depart.service.DepartService;
import kr.co.hivesys.depart.vo.DepartVo;
import kr.co.hivesys.setting.service.AspService;
import kr.co.hivesys.setting.vo.AspVO;
import kr.co.hivesys.terminal.vo.TerminalVo;
import kr.co.hivesys.user.vo.UserVO;
import kr.co.hivesys.terminal.service.TerminalService;

@Controller
public class TerminalController {

	public static final Logger logger = LoggerFactory.getLogger(TerminalController.class);
	public String url="";
	
	@Resource(name="terminalService")
	private TerminalService terminalService;
	
	@Resource(name="aspService")
	private AspService aspService;
	
	//주소에 맞게 매핑
	@RequestMapping(value= {"/chart/*.do","/terminal/*.do","/stat/*.do"})
	public String urlMapping(HttpSession httpSession, HttpServletRequest request,Model model
			) throws Exception{
		/*logger.debug("▶▶▶▶▶▶▶.단말기 최초 컨트롤러");
		url = request.getRequestURI().substring(request.getContextPath().length()).split(".do")[0];
		logger.debug("▶▶▶▶▶▶▶.보내려는 url : "+url);
		ModelAndView mav = new ModelAndView(url);
		return mav;*/
		logger.debug("▶▶▶▶▶▶▶.단말기 최초 컨트롤러");
		url = request.getRequestURI().substring(request.getContextPath().length()).split(".do")[0];
		logger.debug("▶▶▶▶▶▶▶.보내려는 url : "+url);
		return url;
	}
	
	//메인화면 - 목록 조회
	@RequestMapping(value="/terminal/mainTerminalList.ajax")
	public @ResponseBody ModelAndView reqList( 
			HttpServletRequest request
			//@RequestParam(required=false, value="idArr[]")List<String> listArr
			,@ModelAttribute("TerminalVo") TerminalVo inputVo
			,@RequestParam(required=false, value="startNum")String startNum
			,@RequestParam(required=false, value="endNum")String endNum
			,@RequestParam(required=false, value="teamName")String teamCode
			) throws Exception{
		url = request.getRequestURI().substring(request.getContextPath().length()).split(".do")[0];
		ModelAndView mav = new ModelAndView("jsonView");
		List<TerminalVo> sList = null;
		List<AspVO> aspList = null;
		try {
			UserVO reqLoginVo = (UserVO) request.getSession().getAttribute("login");
			
			sList = terminalService.mainTerminalList(inputVo);
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
	
	//목록 데이터 전송
	@RequestMapping(value= "/terminal/terminalList.ajax")
	public @ResponseBody ModelAndView terminalList( 
	HttpServletRequest request, HttpServletResponse response
	,@ModelAttribute("TerminalVo") TerminalVo inputVo
	) throws Exception{
		url = request.getRequestURI().substring(request.getContextPath().length()).split(".do")[0];
		
		ModelAndView mav = new ModelAndView("jsonView");
		List<TerminalVo> sList= null;
		try {
			sList = terminalService.selectTerminal(inputVo);
			mav.addObject("data", sList);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(""+e);
			mav.addObject("msg","에러가 발생했습니다.");
		}
		return mav;
	}
	
	//목록 데이터 전송
	@RequestMapping(value= "/terminal/terminalInsert.do")
	public @ResponseBody ModelAndView terminalInsert( 
			HttpServletRequest request, HttpServletResponse response
			,@ModelAttribute("TerminalVo") TerminalVo inputVo
			) throws Exception{
		url = request.getRequestURI().substring(request.getContextPath().length()).split(".do")[0];
		
		ModelAndView mav = new ModelAndView(url);
		List<AspVO> aspList= null;
		try {
			aspList = aspService.selectAspType(new AspVO());
			mav.addObject("carTypeList", aspList);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(""+e);
			mav.addObject("msg","에러가 발생했습니다.");
		}
		return mav;
	}
	
	//등록 저장
	@RequestMapping(value="/terminal/terminalInsert.ajax")
	public ModelAndView insertAjax(HttpSession httpSession, 
			HttpServletRequest request,Model model
			,@ModelAttribute("TerminalVo") TerminalVo inputVo
			) throws Exception{
		ModelAndView mav = new ModelAndView("jsonView");
		try {
			terminalService.insertTerminal(inputVo);
			aspService.insertAsp(inputVo);
		} catch (Exception e) {
			logger.debug("에러메시지 : "+e.toString());
			e.printStackTrace();
			mav.addObject("msg","저장에 실패하였습니다");
		}
		return mav;
	}
	

	//(상세)
	@RequestMapping(value="/terminal/terminalDetail.do")
	public @ResponseBody ModelAndView terminalDetail( 
	HttpServletRequest request, HttpServletResponse response
	,@ModelAttribute("TerminalVo") TerminalVo inputVo
	) throws Exception{
		url = request.getRequestURI().substring(request.getContextPath().length()).split(".do")[0];
		
		ModelAndView mav = new ModelAndView("jsonView");
		TerminalVo data= null;
		try {
			data = terminalService.selectTerminal(inputVo).get(0);
			mav.addObject("data", data);
			mav.setViewName(url);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(""+e);
			mav.addObject("msg","에러가 발생했습니다.");
		}
		return mav;
	}
	
	//사용자 수정 페이지 진입
	@RequestMapping(value="/terminal/terminalUpdate.do")
	public @ResponseBody ModelAndView userUpdate( 
			HttpServletRequest request, HttpServletResponse response
			,@ModelAttribute("TerminalVo") TerminalVo inputVo
	) throws Exception{
		logger.debug("▶▶▶▶▶▶▶.회원정보 조회 목록!!!!!!!!!!!!!!!!");
		
		url = request.getRequestURI().substring(request.getContextPath().length()).split(".do")[0];
		
		ModelAndView mav = new ModelAndView("jsonView");
		TerminalVo data= null;
		List<AspVO> aspList= null;
		try {
			aspList = aspService.selectAspType(new AspVO());
			mav.addObject("carTypeList", aspList);
			data = terminalService.selectTerminal(inputVo).get(0);
			mav.addObject("data", data);
			mav.setViewName(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
	
	//수정 저장
	@RequestMapping(value="/terminal/terminalUpdate.ajax")
	public @ResponseBody ModelAndView reqUpdate(
			 HttpServletRequest request, HttpServletResponse response
			,@ModelAttribute("TerminalVo") TerminalVo inputVo
			) throws Exception{
		url = request.getRequestURI().substring(request.getContextPath().length()).split(".do")[0];
		
		ModelAndView mav = new ModelAndView("jsonView");
		TerminalVo thvo= null;
		try {
			terminalService.updateTerminal(inputVo);
			//aspService.deleteAspD(inputVo);
			aspService.updateAsp(inputVo);
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(""+e);
			mav.addObject("msg","에러가 발생했습니다.");
		}
		return mav;
	}
	
	//단말기 삭제
	@RequestMapping(value="/terminal/terminalDelete.ajax")
	public @ResponseBody ModelAndView userDelete( @RequestParam(value="idArr[]")List<String> dataArr,HttpServletRequest request) throws Exception{
		logger.debug("▶▶▶▶▶▶▶.회원정보 삭제!!!!!!!!!!!!!!!!");
		
		url = request.getRequestURI().substring(request.getContextPath().length()).split(".do")[0];
		
		ModelAndView mav = new ModelAndView("jsonView");
		try {
			terminalService.deleteTerminal(dataArr);
			aspService.deleteAsp(dataArr);
		} catch (Exception e) {
			mav.addObject("msg","에러가 발생하였습니다");
		}
		return mav;
	}
	
	//24-10-28 : 상세에서 단말기 삭제
	@RequestMapping(value="/terminal/terminalDeleteD.ajax")
 	public @ResponseBody ModelAndView terminalDetailDelete(
 			@ModelAttribute("TerminalVo") TerminalVo inputVo,
 			HttpServletRequest request) throws Exception {
		logger.debug("▶▶▶▶▶▶▶상세에서 회원정보 삭제!!!!!!!!!!!!!!!!");
		
		url = request.getRequestURI().substring(request.getContextPath().length()).split(".do")[0];
		ModelAndView mav = new ModelAndView("jsonView");
		
		try {
			terminalService.deleteTerminalD(inputVo);
			aspService.deleteAspD(inputVo);
		} catch (Exception e) {
			mav.addObject("msg","에러가 발생하였습니다");
		}
		return mav;
	}
	
	
	
	// 단말기 선택 시 단말기 정보 가져오기
	@RequestMapping(value="/chart/subDetail.do")
	public @ResponseBody ModelAndView subDetail( 
	HttpServletRequest request, HttpServletResponse response
	,@ModelAttribute("TerminalVo") TerminalVo inputVo
	) throws Exception{
		url = request.getRequestURI().substring(request.getContextPath().length()).split(".do")[0];
		
		ModelAndView mav = new ModelAndView("jsonView");
		TerminalVo data= null;
		try {
			data = terminalService.selectTerminal(inputVo).get(0);
			mav.addObject("data", data);
			mav.setViewName(url);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(""+e);
			mav.addObject("msg","에러가 발생했습니다.");
		}
		return mav;
	}
	
	//로그조회  전체 데이터 조회
	@RequestMapping(value= "/terminal/selectDownLogList.ajax")
	public @ResponseBody ModelAndView downLogList( 
	HttpServletRequest request, HttpServletResponse response
	,@ModelAttribute("TerminalVo") TerminalVo inputVo
	) throws Exception{
		url = request.getRequestURI().substring(request.getContextPath().length()).split(".do")[0];
		
		ModelAndView mav = new ModelAndView("jsonView");
		List<TerminalVo> sList= null;
		try {
			sList = terminalService.selectLog(inputVo);
			mav.addObject("data", sList);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(""+e);
			mav.addObject("msg","에러가 발생했습니다.");
		}
		return mav;
	}
	
	//로그조회 금일 데이터 조회
		@RequestMapping(value= "/terminal/selectDownLogListToday.ajax")
		public @ResponseBody ModelAndView downLogListToday( 
		HttpServletRequest request, HttpServletResponse response
		,@ModelAttribute("TerminalVo") TerminalVo inputVo
		) throws Exception{
			url = request.getRequestURI().substring(request.getContextPath().length()).split(".do")[0];
			
			ModelAndView mav = new ModelAndView("jsonView");
			List<TerminalVo> sList= null;
			try {
				sList = terminalService.selectLogToday(inputVo);
				mav.addObject("data", sList);
				
			} catch (Exception e) {
				e.printStackTrace();
				logger.debug(""+e);
				mav.addObject("msg","에러가 발생했습니다.");
			}
			return mav;
		}
	
	//엑셀다운
	@RequestMapping(value= {"/terminal/excelDown.ajax","/terminal/excelDownToday.ajax"})
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
			sList = terminalService.selectLogToday(inputVo);
		}else {
			sList = terminalService.selectLog(inputVo);
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
	
}


