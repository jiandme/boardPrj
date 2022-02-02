package kr.co.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.co.service.BoardService;
import kr.co.service.ReplyService;
import kr.co.vo.BoardVO;
import kr.co.vo.PageMaker;
import kr.co.vo.ReplyVO;
import kr.co.vo.SearchCriteria;

/* @Controller 어노테이션의 경우 해당 클래스를 스프링의 빈으로 인식하도록 하기 위함 
 * @RequestMapping("/board/*")의 경우 '/board'로 시작하는 모든 처리를 BoardController.java 가 하도록 지정하는 역할*/
@Controller
@RequestMapping("/board/*")
public class BoardController {
	
	/*
	 * log 메서드를 사용할 수 있게 아래의 코드를 추가. 롬복(lombok) 라이브러리가 추가되어 잇는 경우 @log4j 어노테이션문 추가하면
	 * 해당 코드 없이 log메서드 사용가능
	 */
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@Inject
	BoardService service; // Controller - > service - > DAO - > sql로 보내기 위해 객체생성
	@Inject
	ReplyService replyService;
	
	//@RequestMapping(value = "/board/writerView", method = RequestMethod.GET)// 밑과같음
	@GetMapping("/writeView")
	public void writerView() throws Exception {
		logger.info("writeView");
	}
	
	// 게시판 글 작성
		
	//@RequestMapping(value = "/board/write", method = RequestMethod.POST)
	@PostMapping("/write")
	public String write(BoardVO boardVO, MultipartHttpServletRequest mpRequest) throws Exception {
		logger.info("write");

		service.write(boardVO, mpRequest);

		return "redirect:/board/list";
	}
	
	//게시물 목록 조회
	@GetMapping("/list")
	public String list(Model model, @ModelAttribute("scri") SearchCriteria scri) throws Exception {
		logger.info("list");
		
		model.addAttribute("list",service.getlist(scri));
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(scri);

		pageMaker.setTotalCount(service.listCount(scri));

		model.addAttribute("pageMaker", pageMaker);

		return "/board/list";
	}
	
	//게시글 상세 조회
	@GetMapping("/readView")
	public String read(BoardVO boardVO, @ModelAttribute("scri") SearchCriteria scri, Model model) throws Exception {
		logger.info("readView");
		//게시글 조회
		model.addAttribute("read",service.read(boardVO.getBno()));
		model.addAttribute("scri", scri);
		//댓글 조회
		List<ReplyVO> replyList= replyService.readReply(boardVO.getBno());
		model.addAttribute("replyList", replyList);
		//첨부파일 조회
		List<Map<String, Object>> fileList = service.selectFileList(boardVO.getBno());
		model.addAttribute("file", fileList);
		
		return "/board/readView";
	}
	
	//게시글 수정
	@PostMapping("/update")
	public String update(BoardVO boardVO, @ModelAttribute("scri") SearchCriteria scri, 
						 RedirectAttributes rttr, @RequestParam(value="fileNoDel[]") String[] files,
						 @RequestParam(value="fileNameDel[]") String[] fileNames,
						 MultipartHttpServletRequest mpRequest) throws Exception {
		
		logger.info("update");
		service.update(boardVO, files, fileNames, mpRequest);
		
		rttr.addAttribute("bno", boardVO.getBno());
		rttr.addAttribute("page", scri.getPage());
		rttr.addAttribute("perPageNum", scri.getPerPageNum());
		rttr.addAttribute("searchType", scri.getSearchType());
		rttr.addAttribute("keyword", scri.getKeyword());
		
		return "redirect:/board/readView";
	}
	
	//게시글 수정뷰
	@GetMapping("/updateView")
	public String updateView(BoardVO boardVO, @ModelAttribute("scri") SearchCriteria scri, Model model) throws Exception{
		logger.info("updateView");
		
		model.addAttribute("update", service.read(boardVO.getBno()));
		model.addAttribute("scri", scri);
		
		List<Map<String, Object>> fileList = service.selectFileList(boardVO.getBno());
		model.addAttribute("file", fileList);
		return "board/updateView";
	}
	
	//게시글 삭제
	
	@PostMapping("/delete")
	public String delete(BoardVO boardVO, @ModelAttribute("scri") SearchCriteria scri, RedirectAttributes rttr) throws Exception {
		logger.info("delete");
		System.out.println(scri.getKeyword());
		service.delete(boardVO.getBno());
		System.out.println(scri.getKeyword());
		rttr.addAttribute("page", scri.getPage());
		rttr.addAttribute("perPageNum", scri.getPerPageNum());
		rttr.addAttribute("searchType", scri.getSearchType());
		rttr.addAttribute("keyword", scri.getKeyword());
		return "redirect:/board/list";
	}
	
	//댓글 추가
	@PostMapping("/replyWrite")
	public String replyWrite(ReplyVO replyVO, @ModelAttribute("scri") SearchCriteria scri, RedirectAttributes rttr) throws Exception {
		logger.info("replyWrite");
		replyService.writeReply(replyVO);
		
		rttr.addAttribute("bno", replyVO.getBno());
		rttr.addAttribute("page", scri.getPage());
		rttr.addAttribute("perPageNum", scri.getPerPageNum());
		rttr.addAttribute("searchType", scri.getSearchType());
		rttr.addAttribute("keyword", scri.getKeyword());
		
		return "redirect:/board/readView";
	}
	
	//댓글 삭제 GET
	@GetMapping("/replyDeleteView")
	public String replyDeleteView(ReplyVO replyVO, @ModelAttribute("scri") SearchCriteria scri, Model model) throws Exception {
		logger.info("reply Write View");
		
		model.addAttribute("replyDelete", replyService.selectReply(replyVO.getRno()));
		model.addAttribute("scri",scri);
		

		
		return "/board/replyDeleteView";
	}
	
	//댓글 삭제
	@PostMapping("/replyDelete")
	public String replyDelete(ReplyVO replyVO, @ModelAttribute("scri") SearchCriteria scri, RedirectAttributes rttr) throws Exception {
		logger.info("replyWrite");
		
		replyService.deleteReply(replyVO);
		
		rttr.addAttribute("bno", replyVO.getBno());
		rttr.addAttribute("page", scri.getPage());
		rttr.addAttribute("perPageNum", scri.getPerPageNum());
		rttr.addAttribute("searchType", scri.getSearchType());
		rttr.addAttribute("keyword", scri.getKeyword());
		
		return "redirect:/board/readView";
	}
	
	//댓글 수정 GET
	@GetMapping("/replyUpdateView")
	public String replyUpdateView(ReplyVO replyVO, @ModelAttribute("scri") SearchCriteria scri, Model model) throws Exception {
		logger.info("Update View");
		
		model.addAttribute("replyDelete", replyService.selectReply(replyVO.getRno()));
		model.addAttribute("scri",scri);
		

		
		return "/board/replyUpdateView";
	}
	
	//댓글 수정
	@PostMapping("/replyUpdate")
	public String replyUpdate(ReplyVO replyVO, @ModelAttribute("scri") SearchCriteria scri, RedirectAttributes rttr) throws Exception {
		logger.info("replyUpdate");
		
		replyService.UpdateReply(replyVO);
		
		rttr.addAttribute("bno", replyVO.getBno());
		rttr.addAttribute("page", scri.getPage());
		rttr.addAttribute("perPageNum", scri.getPerPageNum());
		rttr.addAttribute("searchType", scri.getSearchType());
		rttr.addAttribute("keyword", scri.getKeyword());
		
		return "redirect:/board/readView";
	}
	
	//첨부파일 다운로드
	@RequestMapping(value="/fileDown")
	public void fileDown(@RequestParam Map<String, Object> map, HttpServletResponse response) throws Exception {
		logger.info("fileDown");
		Map<String, Object> resultMap = service.selectFileInfo(map);
		String storedFileName = (String) resultMap.get("STORED_FILE_NAME");
		String originalFileName = (String) resultMap.get("ORG_FILE_NAME");
		//파일을 저장했던 위치에서 첨부파일을 읽어 byte형식으로 변환
		byte fileByte[] = org.apache.commons.io.FileUtils.readFileToByteArray(new File("C:\\mp\\file\\"+storedFileName));
		
		response.setContentType("application/octet-stream");
		response.setContentLength(fileByte.length);
		response.setHeader("Content-Disposition",  "attachment; fileName=\""+URLEncoder.encode(originalFileName, "UTF-8")+"\";");
		response.getOutputStream().write(fileByte);
		response.getOutputStream().flush();
		response.getOutputStream().close();
	
	}
	
}