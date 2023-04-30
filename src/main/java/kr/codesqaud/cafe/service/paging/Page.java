package kr.codesqaud.cafe.service.paging;

import java.util.List;

public class Page<T> {

	private final List<T> contents;
	private final int totalPages;

	public Page(final List<T> contents, final long totalElementCnt) {
		this.contents = contents;
		this.totalPages = calculateTotalPages(totalElementCnt);
	}

	private int calculateTotalPages(final long totalElementCnt) {
		if (totalElementCnt % 15 == 0) {
			return (int)totalElementCnt / 15;
		}
		return (int)totalElementCnt / 15 + 1;
	}

	public List<T> getContents() {
		return contents;
	}

	public int getTotalPages() {
		return totalPages;
	}
}
