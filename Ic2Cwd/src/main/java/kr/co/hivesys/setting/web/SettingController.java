package kr.co.hivesys.setting.web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.co.hivesys.depart.service.DepartService;
import kr.co.hivesys.depart.vo.DepartVo;
import kr.co.hivesys.setting.service.AspService;
import kr.co.hivesys.setting.service.CwdService;
import kr.co.hivesys.setting.vo.AspVO;
import kr.co.hivesys.setting.vo.CwdVO;
import kr.co.hivesys.user.vo.UserVO;

/**
 *  컨트롤러 클래스
 * @author 솔루션사업팀 정다빈
 * @since 2021.07.23
 * @version 1.0
 * @see
 *
 * << 개정이력(Modification Information) >>
 *
 *   수정일            수정자              수정내용
 *  -------    -------- ---------------------------
 *  2021.07.23  정다빈           최초 생성
 */

@Controller
public class SettingController {

	public static final Logger logger = LoggerFactory.getLogger(SettingController.class);
	public String url="";
	
	@Resource(name="aspService")
	private AspService aspService;
	
	@Resource(name="cwdService")
	private CwdService cwdService;
	
	//주소에 맞게 매핑
	@RequestMapping(value= {"/setting/asp/*.do","/setting/cwd/*.do"})
	public String urlMapping(HttpSession httpSession, HttpServletRequest request,Model model
			) throws Exception{
		logger.debug("▶▶▶▶▶▶▶.단말기 최초 컨트롤러");
		url = request.getRequestURI().substring(request.getContextPath().length()).split(".do")[0];
		logger.debug("▶▶▶▶▶▶▶.보내려는 url : "+url);
		return url;
	}
	
	//목록 조회
	@RequestMapping(value="/setting/asp/aspList.ajax")
	public @ResponseBody ModelAndView reqList( 
			HttpServletRequest request
			//@RequestParam(required=false, value="idArr[]")List<String> listArr
			,@ModelAttribute("AspVO") AspVO inputVo) throws Exception{
		url = request.getRequestURI().substring(request.getContextPath().length()).split(".do")[0];
		ModelAndView mav = new ModelAndView("jsonView");
		List<AspVO> sList= null;
		try {
			sList = aspService.selectAspList(inputVo);
			mav.addObject("data", sList);
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("에러메시지 : "+e.toString());
		}
		return mav;
	}
	//등록 화면
	@RequestMapping(value="/setting/asp/aspInsert.do")
	public @ResponseBody ModelAndView aspInsert (HttpServletRequest request) throws Exception{
		url = request.getRequestURI().substring(request.getContextPath().length()).split(".do")[0];
		ModelAndView mav = new ModelAndView(url);
		List<AspVO> settingList = new ArrayList<>();
		try {
			settingList = aspService.selectAspList(new AspVO());
		} catch (Exception e) {
			logger.debug("에러메시지 : "+e.toString());
		}
		return mav;
	}
	//등록 저장
	@RequestMapping(value="/setting/asp/aspInsert.ajax")
	public ModelAndView aspInsertLogic(HttpSession httpSession, 
			HttpServletRequest request,Model model
			,@ModelAttribute("AspVO") AspVO inputVo
			) throws Exception{
		ModelAndView mav = new ModelAndView("jsonView");
		try {
			//쿼리 조회
			aspService.insertAsp(inputVo);			
		} catch (Exception e) {
			logger.debug("에러메시지 : "+e.toString());
			e.printStackTrace();
			mav.addObject("msg","저장에 실패하였습니다");
		}
		return mav;
	}
	
	//(상셰)
	@RequestMapping(value="/setting/asp/aspDetail.do")
	public @ResponseBody ModelAndView aspDetail( 
	HttpServletRequest request, HttpServletResponse response
	,@ModelAttribute("AspVO") AspVO inputVo
	) throws Exception{
		url = request.getRequestURI().substring(request.getContextPath().length()).split(".do")[0];
		
		ModelAndView mav = new ModelAndView("jsonView");
		AspVO data= null;
		try {
			data = aspService.selectAsp(inputVo);
			mav.addObject("data", data);
			mav.setViewName(url);
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(""+e);
			mav.addObject("msg","에러가 발생했습니다.");
		}
		return mav;
	}
	
	// 수정 페이지 진입
	@RequestMapping(value="/setting/asp/aspUpdate.do")
	public @ResponseBody ModelAndView settingUpdate( 
			HttpServletRequest request, HttpServletResponse response
			,@ModelAttribute("AspVO") AspVO inputVo
	) throws Exception{
		logger.debug("▶▶▶▶▶▶▶.회원정보 조회 목록!!!!!!!!!!!!!!!!");
		
		url = request.getRequestURI().substring(request.getContextPath().length()).split(".do")[0];
		
		ModelAndView mav = new ModelAndView("jsonView");
		AspVO data= null;
		try {
			data = aspService.selectAsp(inputVo);
			mav.addObject("data", data);
			mav.setViewName(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
	
	//수정 저장
	@RequestMapping(value="/setting/asp/aspUpdate.ajax")
	public @ResponseBody ModelAndView reqUpdate(
			 HttpServletRequest request, HttpServletResponse response
			,@ModelAttribute("AspVO") AspVO inputVo
			) throws Exception{
		url = request.getRequestURI().substring(request.getContextPath().length()).split(".do")[0];
		
		ModelAndView mav = new ModelAndView("jsonView");
		AspVO thvo= null;
		try {
			aspService.updateAsp(inputVo);
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(""+e);
			mav.addObject("msg","에러가 발생했습니다.");
		}
		return mav;
	}
	
	// 삭제
	@RequestMapping(value="/setting/asp/aspDelete.ajax")
	public @ResponseBody ModelAndView settingDelete
	( @RequestParam(value="idArr[]")List<String> dataArr,HttpServletRequest request) throws Exception{
		logger.debug("▶▶▶▶▶▶▶.회원정보 삭제!!!!!!!!!!!!!!!!");
		
		url = request.getRequestURI().substring(request.getContextPath().length()).split(".do")[0];
		
		ModelAndView mav = new ModelAndView("jsonView");
		try {
			aspService.deleteAsp(dataArr);
		} catch (Exception e) {
			mav.addObject("msg","에러가 발생하였습니다");
		}
		return mav;
	}
	
	//상세에서  삭제
	@RequestMapping(value="/setting/asp/aspDeleteD.ajax")
 	public @ResponseBody ModelAndView settingDetailDelete(@RequestParam(value="settingId") String settingId , HttpServletRequest request) throws Exception {
		logger.debug("▶▶▶▶▶▶▶상세에서 회원정보 삭제!!!!!!!!!!!!!!!!");
		
		url = request.getRequestURI().substring(request.getContextPath().length()).split(".do")[0];
		ModelAndView mav = new ModelAndView("jsonView");
		
		try {
			//aspService.deleteAspD(settingId);
		} catch (Exception e) {
			mav.addObject("msg","에러가 발생하였습니다");
		}
		return mav;
	}
	
	@RequestMapping(value="/setting/cwd/cwdList.do")
	public @ResponseBody ModelAndView cwdList( 
			HttpServletRequest request
			,@ModelAttribute("CwdVO") CwdVO inputVo) throws Exception{
		url = request.getRequestURI().substring(request.getContextPath().length()).split(".do")[0];
		ModelAndView mav = new ModelAndView("jsonView");
		List<CwdVO> sList= null;
		try {
			sList = cwdService.selectCwdList(inputVo);
			mav.addObject("data", sList.get(0));
			mav.setViewName(url);
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("에러메시지 : "+e.toString());
		}
		return mav;
	}
	
	//수정 저장
	@RequestMapping(value="/setting/cwd/cwdUpdate.ajax")
	public @ResponseBody ModelAndView cwdUpdate(
			 HttpServletRequest request, HttpServletResponse response
			,@ModelAttribute("CwdVO") CwdVO inputVo
			) throws Exception{
		url = request.getRequestURI().substring(request.getContextPath().length()).split(".do")[0];
		ModelAndView mav = new ModelAndView("jsonView");
		try {
			cwdService.updateCwd(inputVo);
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(""+e);
			mav.addObject("msg","에러가 발생했습니다.");
		}
		return mav;
	}
}
