package kr.co.dao;

import java.util.List;
import java.util.Map;

import kr.co.vo.BoardVO;
import kr.co.vo.SearchCriteria;
//클래스가 아니라 인터페이스로 만들어야 lmpl에서 구현이가능함
public interface BoardDAO {
	/*
	 * 게시글 작성 게시글작성시에 데이터를 DB에 꽂아줘야하는데 이때 유저가 작성한 데이터가 VO객체에 들어있음 이것을 DAO에서 받아서
	 * SQL과의 통신을 통해 DB에 꽂아넣어줘야함 그래서 VO객체를 파라미터로 받는것이고 이것의 구현은 Impl에서 할것임 즉 BoardDAO는
	 * 껍데기
	 */
	//게시물 작성
	public void write(BoardVO boardVO) throws Exception;
	//게시물 목록조회
	public List<BoardVO> getlist(SearchCriteria scri) throws Exception;
	//게시물 총 갯수
	public int listCount(SearchCriteria scri) throws Exception;
	//게시물 상세조회
	public BoardVO read(int bno) throws Exception;
	//게시물 수정
	public void update(BoardVO boardVO) throws Exception;
	//게시물 삭제
	public void delete(int bno) throws Exception;
	//첨부파일 업로드
	public void insertFile(Map<String, Object> map);
	//첨부파일 조회
	public List<Map<String, Object>> selectFileList(int bno);
	//첨부파일 다운로드
	public Map<String, Object> selectFileInfo(Map<String, Object> map);
	//첨부파일 수정
	public void updateFile(Map<String, Object> map);
	//게시판 조회수
	public void boardHit(int bno);
}
