package kr.codesqaud.cafe.common.web;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class PageHandler {
	private final long totalPostsCount; // 총 개시글 수
	private static final int PAGE_SIZE = 15; // 한 페이지에 보여줄 게시글 수
	private static final int PAGING_BAR_SIZE = 5; // 페이징 바에 보여줄 페이지 수
	private static final int FIRST_PAGE = 1; // 1 페이지
	private final long lastPage; // 마지막 페이지
	private final long currentPage; // 현재 페이지
	private final long beginPageInPagingBar; // 현재 페이징 바의 첫 번째 페이지
	private final long endPageInPagingBar; // 현재 페이징 바의 마지막 페이지
	private final long postOffset; // 현재 페이지의 첫 번째 게시글의 변위
	private final boolean previousBlock; // 페이징 바에서 이전 블록이 존재하는가?
	private final boolean nextBlock; // 페이징 바에서 다음 블록이 존재하는가?
	private final long previousPage; // 이전 블록으로 넘어갈 때 페이지 번호
	private final long nextPage; // 다음 블록으로 넘어갈 때 페이지 번호
	private final List<Long> pagingBar; // 페이징 바

	public PageHandler(long totalPostsCount, long currentPage) {
		this.totalPostsCount = totalPostsCount;
		this.lastPage = (getTotalPostsCount() - 1) / getPageSize() + 1;
		this.currentPage = (currentPage > 0) ? (Math.min(currentPage, getLastPage())) : 1;
		this.postOffset = (getCurrentPage() - 1) * getPageSize();
		this.beginPageInPagingBar = ((getCurrentPage() - 1) / getPagingBarSize() * getPagingBarSize()) + 1;
		this.endPageInPagingBar = Math.min(getBeginPageInPagingBar() + getPagingBarSize() - 1, getLastPage());
		this.previousBlock = getCurrentPage() > getPagingBarSize();
		this.nextBlock = getCurrentPage() <= getLastPage() - getPagingBarSize() + 1;
		this.previousPage = (getCurrentPage() - getPagingBarSize());
		this.nextPage = Math.min(getCurrentPage() + getPagingBarSize(), getLastPage());
		this.pagingBar = LongStream.rangeClosed(beginPageInPagingBar, endPageInPagingBar)
			.boxed()
			.collect(Collectors.toUnmodifiableList());
	}

	public long getTotalPostsCount() {
		return totalPostsCount;
	}

	public int getPageSize() {
		return PAGE_SIZE;
	}

	public int getPagingBarSize() {
		return PAGING_BAR_SIZE;
	}

	public int getFirstPage() {
		return FIRST_PAGE;
	}

	public long getLastPage() {
		return lastPage;
	}

	public long getCurrentPage() {
		return currentPage;
	}

	public long getBeginPageInPagingBar() {
		return beginPageInPagingBar;
	}

	public long getEndPageInPagingBar() {
		return endPageInPagingBar;
	}

	public long getPostOffset() {
		return postOffset;
	}

	public boolean isPreviousBlock() {
		return previousBlock;
	}

	public boolean isNextBlock() {
		return nextBlock;
	}

	public long getPreviousPage() {
		return previousPage;
	}

	public long getNextPage() {
		return nextPage;
	}

	public List<Long> getPagingBar() {
		return pagingBar;
	}
}
