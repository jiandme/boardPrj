package kr.co.service;

import kr.co.vo.MemberVO;

public interface MemberService {

	
	//회원가입
	public void register(MemberVO memberVO);
	
	//로그인
	public MemberVO login(MemberVO memberVO);
	
	//회원정보수정
	public void memberUpdate(MemberVO memberVO);
	
	//회원탈퇴
	public void memberDelete(MemberVO memberVO);
	
	//비밀번호체크
	public int passChk(MemberVO memberVO);
	
	//아이디체크
	public int idChk(MemberVO memberVO);
}


	
	
