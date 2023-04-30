package kr.codesqaud.cafe.mainPage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Pagination {
	private List<Integer> pageNumList;

	//총 페이지수
	private int totalPage;

	//현재 페이지 기준 다음 페이지 리스트로 이동 ex) <1,2,3,4,5> 에서 누르면 <6,7,8,9,10>
	private int nextPage;

	private int previousPage;

	private static final int pageSize = 5;

	public Pagination(PaginationDto paginationDto, Long countOfArticles) {
		pageNumList = new ArrayList<>();
		calculatePage(paginationDto.getPage(), countOfArticles, paginationDto.getRecordSize());
	}

	private void calculatePage(int page, Long countOfArticles, int recordSize) {
		// 총 페이지 수 계산
		totalPage = (int)Math.ceil((double)countOfArticles / recordSize);

		// 시작 페이지 계산
		int startPage = ((page - 1) / pageSize) * pageSize + 1;

		// 끝 페이지 계산
		int endPage = Math.min(startPage + pageSize - 1, totalPage);

		// 다음 페이지네이션 시작점
		nextPage = Math.min(startPage + pageSize, endPage + 1);
		if (nextPage > totalPage) {
			nextPage = page;
		}

		// 이전 페이지네이션 시작점
		previousPage = Math.max(startPage - pageSize, 1);

		pageNumList = IntStream.rangeClosed(startPage, endPage)
			.boxed()
			.collect(Collectors.toUnmodifiableList());
	}
}
