package kr.co.dao;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import kr.co.vo.ReplyVO;

@Repository
public class ReplyDAOImpl implements ReplyDAO {
	
	@Inject
	private SqlSession sqlSession;
	
	@Override
	public List<ReplyVO> readReply(int bno) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList("replyMapper.readReply", bno);
	}
	
	@Override
	public void writeReply(ReplyVO replyVO) {
		sqlSession.insert("replyMapper.writeReply", replyVO);
		
	}
	
	@Override
	public void deleteReply(ReplyVO replyVO) {
		sqlSession.delete("replyMapper.deleteReply", replyVO);
		
	}
	
	@Override
	public ReplyVO selectReply(int rno) {
		return sqlSession.selectOne("replyMapper.selectReply", rno);
	}
	
	@Override
	public void UpdateReply(ReplyVO replyVO) {
		sqlSession.update("replyMapper.updateReply", replyVO);
		
	}
	
}
