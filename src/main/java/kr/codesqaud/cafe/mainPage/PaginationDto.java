package kr.codesqaud.cafe.mainPage;

public class PaginationDto {

	private int page; // 현재 페이지 번호

	private int recordSize; // 페이지당 출력할 데이터 갯수

	public PaginationDto() {
		this.page = 1;
		this.recordSize = 15;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPage() {
		return page;
	}

	public int getOffset() {
		return (page - 1) * recordSize;
	}

	public int getRecordSize() {
		return recordSize;
	}
}
