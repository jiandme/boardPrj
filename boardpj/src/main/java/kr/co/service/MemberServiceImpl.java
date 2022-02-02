package kr.co.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.co.dao.MemberDAO;
import kr.co.vo.MemberVO;

@Service
public class MemberServiceImpl implements MemberService {

	@Inject
	private MemberDAO dao;
	
	@Override
	public void register(MemberVO memberVO) {
		
		dao.register(memberVO);
	}
	
	@Override
	public MemberVO login(MemberVO memberVO) {
		
		return dao.login(memberVO);
	}
	
	@Override
	public void memberUpdate(MemberVO memberVO) {
		
		dao.memberUpdate(memberVO);
		
	}
	
	@Override
	public void memberDelete(MemberVO memberVO) {
		dao.memberDelete(memberVO);
		
	}
	
	@Override
	public int passChk(MemberVO memberVO) {
		return dao.passChk(memberVO);
	}
	
	@Override
	public int idChk(MemberVO memberVO) {
		return dao.idChk(memberVO);
	}
}
