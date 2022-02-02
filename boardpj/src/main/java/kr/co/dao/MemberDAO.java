package kr.co.dao;

import kr.co.vo.MemberVO;
//클래스가 아니라 인터페이스로 만들어야 lmpl에서 구현이가능함
public interface MemberDAO {
	/*
	 * 게시글 작성 게시글작성시에 데이터를 DB에 꽂아줘야하는데 이때 유저가 작성한 데이터가 VO객체에 들어있음 이것을 DAO에서 받아서
	 * SQL과의 통신을 통해 DB에 꽂아넣어줘야함 그래서 VO객체를 파라미터로 받는것이고 이것의 구현은 Impl에서 할것임 즉 BoardDAO는
	 * 껍데기
	 */


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
