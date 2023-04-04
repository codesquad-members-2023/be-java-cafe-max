package kr.codesqaud.cafe.domain;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PageHandler {
	private int totalPostsCount; // 총 개시글 수
	private int pageSize = 15; // 한 페이지에 보여줄 게시글 수
	private int pagingBarSize = 5; // 페이징 바에 보여줄 페이지 수
	private final int firstPage = 1; // 1 페이지
	private int lastPage; // 마지막 페이지
	private int currentPage; // 현재 페이지
	private int beginPageInPagingBar; // 현재 페이징 바의 첫 번째 페이지
	private int endPageInPagingBar; // 현재 페이징 바의 마지막 페이지
	private int postOffset; // 현재 페이지의 첫 번째 게시글의 변위
	private boolean previousBlock; // 페이징 바에서 이전 블록이 존재하는가?
	private boolean nextBlock; // 페이징 바에서 다음 블록이 존재하는가?

	private List<Integer> pagingBar; // 페이징 바

	public PageHandler(int totalPostsCount, int currentPage) {
		this.totalPostsCount = totalPostsCount;
		this.currentPage = currentPage;
		this.lastPage = (getTotalPostsCount() - 1) / getPageSize() + 1;
		this.postOffset = (getCurrentPage() - 1) * getPageSize();
		this.beginPageInPagingBar = ((getCurrentPage() - 1) / getPagingBarSize() * getPagingBarSize()) + 1;
		this.endPageInPagingBar = getBeginPageInPagingBar() + getPagingBarSize() - 1;
		if (getEndPageInPagingBar() > getLastPage())
			this.endPageInPagingBar = getLastPage();
		this.previousBlock = getCurrentPage() > getPagingBarSize();
		this.nextBlock = getCurrentPage() <= getLastPage() - getPagingBarSize() + 1;
		this.pagingBar = IntStream.rangeClosed(beginPageInPagingBar, endPageInPagingBar)
			.boxed()
			.collect(Collectors.toUnmodifiableList());
	}

	public int getTotalPostsCount() {
		return totalPostsCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getPagingBarSize() {
		return pagingBarSize;
	}

	public int getFirstPage() {
		return firstPage;
	}

	public int getLastPage() {
		return lastPage;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public int getBeginPageInPagingBar() {
		return beginPageInPagingBar;
	}

	public int getEndPageInPagingBar() {
		return endPageInPagingBar;
	}

	public int getPostOffset() {
		return postOffset;
	}

	public boolean isPreviousBlock() {
		return previousBlock;
	}

	public boolean isNextBlock() {
		return nextBlock;
	}

	public List<Integer> getPagingBar() {
		return pagingBar;
	}
}
