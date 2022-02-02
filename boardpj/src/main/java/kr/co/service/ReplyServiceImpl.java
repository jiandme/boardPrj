package kr.co.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.co.dao.ReplyDAO;
import kr.co.vo.ReplyVO;

@Service
public class ReplyServiceImpl implements ReplyService {

	@Inject
	private ReplyDAO dao;
	
	@Override
	public List<ReplyVO> readReply(int bno) throws Exception {
		// TODO Auto-generated method stub
		return dao.readReply(bno);
	}
	
	@Override
	public void writeReply(ReplyVO replyVO) {
		dao.writeReply(replyVO);
	}
	
	@Override
	public void deleteReply(ReplyVO replyVO) {
		dao.deleteReply(replyVO);
		
	}
	
	@Override
	public ReplyVO selectReply(int rno) {
		return dao.selectReply(rno);
	}
	
	@Override
	public void UpdateReply(ReplyVO replyVO) {
		dao.UpdateReply(replyVO);
		
	}
	
}
