package com.creme.service.Board;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.creme.domain.BoardDTO;

public interface BoardService {

	// 게시글 갯수 계산
	public int countArticle(String search_option, String keyword);
	// 목록(페이지 나누기, 검색 기능 포함)
	public List<BoardDTO> listView(String sort_option, String search_option, String keyword, int start, int end);
	// 상세게시글 조회
	public BoardDTO view(int bno);
	// 조회수 +1증가
	public void increaseViewCnt(int bno, HttpSession session );
	// 게시글 삭제
	public String delete(int bno);
	// 게시글 등록
	public void write(BoardDTO bDto);
	// 게시글 수정
	public void updateView(BoardDTO bDto);
	// 게시글 답글 등록
	public void answer(BoardDTO bDto);
	// 해당 게시글 첨부파일 목록 출력
	public List<String> getAttach(int bno);
}
