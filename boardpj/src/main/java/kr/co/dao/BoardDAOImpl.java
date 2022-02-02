package kr.co.dao;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import kr.co.vo.BoardVO;
import kr.co.vo.SearchCriteria;

@Repository
public class BoardDAOImpl implements BoardDAO {
	
	@Inject
	private SqlSession sqlSession;
	
	//sqlSession객체에 boardVO에 있는 데이터를 추가해서 옮긴다
	
	@Override
	public void write(BoardVO boardVO) throws Exception {
		sqlSession.insert("boardMapper.insert", boardVO);
		
	}
	
	//게시물 목록조회
	@Override
	public List<BoardVO> getlist(SearchCriteria scri) throws Exception {
		return sqlSession.selectList("boardMapper.listPage", scri);
	}
	
	//게시물 총 갯수
	@Override
	public int listCount(SearchCriteria scri) throws Exception {
		return sqlSession.selectOne("boardMapper.listCount", scri);
	}
	
	@Override
	public BoardVO read(int bno) throws Exception {
		
		return sqlSession.selectOne("boardMapper.read",bno);
	}

	@Override
	public void update(BoardVO boardVO) throws Exception {
		sqlSession.update("boardMapper.update",boardVO);
	}
	
	@Override
	public void delete(int bno) throws Exception {
		sqlSession.delete("boardMapper.delete",bno);
		
	}
	
	@Override
	public void insertFile(Map<String, Object> map) {

		sqlSession.insert("boardMapper.insertFile", map);
		
	}
	
	@Override
	public List<Map<String, Object>> selectFileList(int bno) {
		return sqlSession.selectList("boardMapper.selectFileList", bno);
	}
	
	@Override
	public Map<String, Object> selectFileInfo(Map<String, Object> map) {
		return sqlSession.selectOne("boardMapper.selectFileInfo", map);
	}
	
	@Override
	public void updateFile(Map<String, Object> map) {
		sqlSession.update("boardMapper.updateFile", map);
		
	}
	
	@Override
	public void boardHit(int bno) {
		sqlSession.update("boardMapper.boardHit", bno);
	}
}
