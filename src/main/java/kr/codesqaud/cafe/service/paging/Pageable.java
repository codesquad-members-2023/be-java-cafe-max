package kr.codesqaud.cafe.service.paging;

public class Pageable {

	private static final int ARTICLES_PER_PAGE = 15;

	private final int page;

	public Pageable(int page) {
		this.page = page;
	}

	public int getPageNumber() {
		return page;
	}

	public int getOffset() {
		return page * ARTICLES_PER_PAGE;
	}
}
