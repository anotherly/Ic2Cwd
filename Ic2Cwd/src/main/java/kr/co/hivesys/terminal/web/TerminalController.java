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
import org.springframework.web.bind.annotation.PostMapping;
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
	
}


