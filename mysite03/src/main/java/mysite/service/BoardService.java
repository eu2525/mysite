package mysite.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import mysite.repository.BoardRepository;
import mysite.vo.BoardVo;

@Service
public class BoardService {
	private static final int BOARDPERPAGE = 5;  
	private BoardRepository boardRepository;
	
	public BoardService(BoardRepository boardRepository) {
		this.boardRepository = boardRepository;
	}
	
	
   public void addContents(BoardVo vo) {
        if (vo.getgNo() != 0 && vo.getoNo() != 0) {
            boardRepository.insertReplyBoard(vo.getTitle(), vo.getContents(), vo.getgNo(), vo.getoNo(), vo.getDepth(), vo.getUserId());
        }
        else {
        	boardRepository.insertNewBoard(vo, vo.getUserId());
        }
    }
   
	public BoardVo getContents(Long id) {
		return boardRepository.findById(id);
	}
	
	public BoardVo getContents(Long id, Long userId) {
		return this.getContents(id);
	}
	
//	public void UpdateContents(BoardVo vo) {
//		
//	}
//	
	public void deleteContents(Long id, Long userId) {
		boardRepository.deleteByTitleId(id);
	}
	
	public Map<String, Object> getContentsList(int currentPage, String keyword) {
	
		// view의 pagination을 위한 데이터 값 계산		
		int boardCount = boardRepository.getBoardCount(keyword);
		int totalPage = (int) Math.ceil((double) boardCount / BOARDPERPAGE);
        int prevPage = (((currentPage - 1) / BOARDPERPAGE) * 5) + 1;
        int endPage = Math.min((prevPage + 4), totalPage);
        int boardStartIdx = boardCount - (currentPage - 1) * BOARDPERPAGE;
		List<BoardVo> list = boardRepository.findByPage(keyword, currentPage, 5);
        
        Map<String, Object> map = Map.of(
        						  "totalPage", totalPage,
        						  "prevPage", prevPage,
        						  "endPage", endPage,
        						  "boardStartIdx", boardStartIdx,
        						  "currentPage", currentPage,
        						  "list", list);        
        
		return map;
	}

	
}
