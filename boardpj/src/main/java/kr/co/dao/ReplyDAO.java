package kr.co.dao;

import java.util.List;

import kr.co.vo.ReplyVO;
//클래스가 아니라 인터페이스로 만들어야 lmpl에서 구현이가능함
public interface ReplyDAO {
	/*
	 * 게시글 작성 게시글작성시에 데이터를 DB에 꽂아줘야하는데 이때 유저가 작성한 데이터가 VO객체에 들어있음 이것을 DAO에서 받아서
	 * SQL과의 통신을 통해 DB에 꽂아넣어줘야함 그래서 VO객체를 파라미터로 받는것이고 이것의 구현은 Impl에서 할것임 즉 BoardDAO는
	 * 껍데기
	 */

	//댓글 목록
	public List<ReplyVO> readReply(int bno) throws Exception;
	
	//댓글 작성
	public void writeReply(ReplyVO replyVO);
	
	//댓글 삭제
	public void deleteReply(ReplyVO replyVO);
	
	//댓글 조회
	public ReplyVO selectReply(int rno);
	
	//댓글 수정
	public void UpdateReply(ReplyVO replyVO);
}
