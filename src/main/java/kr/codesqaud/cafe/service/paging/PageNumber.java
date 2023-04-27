package kr.codesqaud.cafe.service.paging;

public class PageNumber {

	private final int number;
	private final int queryStringPageNumber;

	public PageNumber(final int number) {
		this.number = number + 1;
		this.queryStringPageNumber = number;
	}
}
