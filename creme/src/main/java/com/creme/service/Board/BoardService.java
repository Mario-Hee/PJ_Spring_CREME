package com.creme.service.Board;

import java.util.List;

import com.creme.domain.BoardDTO;

public interface BoardService {

	// 게시글 갯수 계산
	public int countArticle(String search_option, String keyword);
	// 목록(페이지 나누기, 검색 기능 포함)
	public List<BoardDTO> listView(String sort_option, String search_option, String keyword, int start, int end);
	// 상세게시글 조회
	public int boardView(int bno, BoardDTO bDto);
}