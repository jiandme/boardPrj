package kr.co.dao;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import kr.co.vo.MemberVO;

@Repository
public class MemberDAOImpl implements MemberDAO {
	
	@Inject
	private SqlSession sqlSession;
	
	@Override
	public void register(MemberVO memberVO) {
		
		sqlSession.insert("memberMapper.register", memberVO);
		
	}
	
	@Override
	public MemberVO login(MemberVO memberVO) {
		
		return sqlSession.selectOne("memberMapper.login", memberVO);
	}
	
	@Override
	public void memberUpdate(MemberVO memberVO) {
		
		sqlSession.update("memberMapper.memberUpdate", memberVO);
		
	}
	
	@Override
	public void memberDelete(MemberVO memberVO) {
		sqlSession.delete("memberMapper.memberDelete", memberVO);
		
	}
	
	@Override
	public int passChk(MemberVO memberVO) {
		
		return sqlSession.selectOne("memberMapper.passChk", memberVO);
	}
	
	@Override
	public int idChk(MemberVO memberVO) {
		return sqlSession.selectOne("memberMapper.idChk", memberVO);
	}
}
