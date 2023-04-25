package kr.codesqaud.cafe.mainPage;

import java.util.ArrayList;
import java.util.List;

public class Pagination {
	private List<Integer> pageNumList;

	//총 페이지수
	private int totalPage;

	//현재 페이지 기준 다음 페이지
	private int nextPage;

	//현재 페이지 기준 이전 페이지
	private int previousPage;

	public Pagination(PaginationDto paginationDto, int countOfArticles) {
		pageNumList = new ArrayList<>();
		int pageSize = 5;
		calculatePage(paginationDto.getPage(), pageSize, countOfArticles, paginationDto.getRecordSize());
	}

	private void calculatePage(int page, int pageSize, int countOfArticles, int recordSize) {
		// 총 페이지 수 계산
		totalPage = (int)Math.ceil((double)countOfArticles / recordSize);

		// 시작 페이지 계산
		int startPage = ((page - 1) / pageSize) * pageSize + 1;

		// 끝 페이지 계산
		int endPage = Math.min(startPage + pageSize - 1, totalPage);

		// 다음 페이지 계산
		nextPage = Math.min(page + 1, totalPage);

		// 이전 페이지 계산
		previousPage = Math.max(page - 1, 1);

		for (int i = startPage; i <= endPage; i++) {
			pageNumList.add(i);
		}
	}
}
