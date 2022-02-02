package kr.co.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.co.service.MemberService;
import kr.co.vo.MemberVO;

/* @Controller 어노테이션의 경우 해당 클래스를 스프링의 빈으로 인식하도록 하기 위함 
 * @RequestMapping("/board/*")의 경우 '/board'로 시작하는 모든 처리를 BoardController.java 가 하도록 지정하는 역할*/

@Controller
@RequestMapping("/member/*")
public class MemberController {
	
	/*
	 * log 메서드를 사용할 수 있게 아래의 코드를 추가. 롬복(lombok) 라이브러리가 추가되어 잇는 경우 @log4j 어노테이션문 추가하면
	 * 해당 코드 없이 log메서드 사용가능
	 */
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	

	@Inject
	MemberService memberService;
	
	@Inject
	BCryptPasswordEncoder pwdEncoder;
	
	//회원가입 GET
	@GetMapping("/register")
	public void getRegister() throws Exception {
		logger.info("get register");
	}
	
	
	//회원가입 POST
	@PostMapping("/register")
	public String postRegister(MemberVO memberVO) throws Exception {
		logger.info("post register");
		//패스워드 암호화 적용
		String pwd = pwdEncoder.encode(memberVO.getUserPass());
		memberVO.setUserPass(pwd);
		memberService.register(memberVO);

		return "/member/login";
	}
	
	//로그인 GET
	@GetMapping("/login")
	public void getLogin() throws Exception
	{
		logger.info("get login");
	}
	
	//로그인 POST
	@PostMapping("/login")
	public String postLogin(MemberVO memberVO, HttpServletRequest req, RedirectAttributes rttr) throws Exception {
		
		logger.info("post login");
		HttpSession session = req.getSession();
		MemberVO login = memberService.login(memberVO);
		boolean pwdMatch = pwdEncoder.matches(memberVO.getUserPass(), login.getUserPass());
		
		
		if(login != null && pwdMatch == true) {
			session.setAttribute("member", login);
			return "redirect:/board/list";
		}
		else {
			session.setAttribute("member", null);
			rttr.addFlashAttribute("msg", false);
			return "redirect:/member/login";
		}
		
		
	}
	
	//로그아웃
	@GetMapping("/logout")
	public String logout(HttpSession session) throws Exception{
		
		session.invalidate();
		
		return "redirect:/board/list";
	}
	
	//회원정보수정 GET
	@GetMapping("/memberUpdateView")
	public String getMemberUpdate() {
		
		logger.info("memberUpdateView");
		
		return "/member/memberUpdateView";
		
	}
	
	//회원정보수정 POST
	@PostMapping("/memberUpdate")
	public String postMemberUpdate(MemberVO memberVO, HttpSession session) {
		
		logger.info("post member update");
	
		memberService.memberUpdate(memberVO);
		session.invalidate();
		
		return "redirect:/board/list";
	}
	
	//회원탈퇴 GET
	@GetMapping("/memberDeleteView")
	public String MemberDeleteView() {
		
		logger.info("member DeleteView");
		
		return "/member/memberDeleteView";
		
	}
	
	//회원탈퇴 POST
	@PostMapping("/memberDelete")
	public String memberDelete(MemberVO memberVO, HttpSession session, RedirectAttributes rttr) {
		
		logger.info("member Delete");
		
		/*
		 * MemberVO SessionMember = (MemberVO) session.getAttribute("member"); //로그인하면서
		 * 남아있던 session에서의 패스워드와 입력받은 memberVO의 패스워드가 일치하는지 확인
		 * if(!(SessionMember.getUserPass().equals(memberVO.getUserPass()))) {
		 * rttr.addFlashAttribute("msg",false); return
		 * "redirect:/member/memberDeleteView"; }
		 */
		
		memberService.memberDelete(memberVO);
		session.invalidate();
		
		return "redirect:/member/login";
	
	}
	
	//비밀번호체크
	@ResponseBody
	@PostMapping("/passChk")
	public boolean passChk(MemberVO memberVO) {
		MemberVO login = memberService.login(memberVO);
		boolean pwdChk = pwdEncoder.matches(memberVO.getUserPass(), login.getUserPass());
		return pwdChk;
	}
	
	//아이디체크
	@ResponseBody
	@PostMapping("/idChk")
	public int idChk(MemberVO memberVO) {
		return memberService.idChk(memberVO);
	}
	
}