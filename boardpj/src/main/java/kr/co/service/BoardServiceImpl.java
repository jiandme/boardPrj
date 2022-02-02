package kr.co.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.co.dao.BoardDAO;
import kr.co.util.FileUtils;
import kr.co.vo.BoardVO;
import kr.co.vo.SearchCriteria;

@Service
public class BoardServiceImpl implements BoardService {

	@Inject
	private BoardDAO dao;
	
	@Resource(name="fileUtils")
	private FileUtils fileUtils;
	
	/*
	 * 게시글 작성 VO객체에 담겨져 있는 데이터를 DAO객체를 생성해 담아준다 VO -> Service -> DAO -> SQL
	 */
	@Override
	public void write(BoardVO boardVO, MultipartHttpServletRequest mpRequest) throws Exception {
		dao.write(boardVO);
		List<Map<String,Object>> list = fileUtils.parseInsertFileInfo(boardVO, mpRequest);
		int size = list.size();
		for(int i=0; i<size; i++) {
			dao.insertFile(list.get(i));
		}
		
	}
	
	@Override
	public List<BoardVO> getlist(SearchCriteria scri) throws Exception {
		return dao.getlist(scri);
	}
	
	@Override
	public int listCount(SearchCriteria scri) throws Exception {
		return dao.listCount(scri);
	}
	
	@Override
	public BoardVO read(int bno) throws Exception {
		dao.boardHit(bno);
		return dao.read(bno);
	}
	
	@Override
	public void update(BoardVO boardVO, String[] files, String[] fileNames, MultipartHttpServletRequest mpRequest) throws Exception {
		//게시글 수정
		dao.update(boardVO);
		//첨부파일 수정
		List<Map<String, Object>> list = fileUtils.parseUpdateFileInfo(boardVO, files, fileNames, mpRequest);
		Map<String, Object> tempMap = null;
		int size = list.size();
		for(int i = 0; i<size; i++) {
			tempMap = list.get(i);
			if(tempMap.get("IS_NEW").equals("Y")) {
				dao.insertFile(tempMap);
			}else {
				dao.updateFile(tempMap);
			}
		}
		
	}
	
	@Override
	public void delete(int bno) throws Exception {
		dao.delete(bno);
		
	}
	
	@Override
	public List<Map<String, Object>> selectFileList(int bno) {
		return dao.selectFileList(bno);
	}
	
	@Override
	public Map<String, Object> selectFileInfo(Map<String, Object> map) {
		return dao.selectFileInfo(map);
	}
	
	
}
