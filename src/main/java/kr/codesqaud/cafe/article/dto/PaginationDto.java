package kr.codesqaud.cafe.article.dto;

public class PaginationDto {

	private int page; // 현재 페이지 번호

	private int recordSize; // 페이지당 출력할 데이터 갯수

	private int pageSize;// 화면 하단에 출력할 페이지 사이즈

	public PaginationDto() {
		this.page = 1;
		this.recordSize = 15;
		this.pageSize = 5;
	}

	public int getOffset() {
		return (page - 1) * recordSize;
	}

	public int getRecordSize() {
		return recordSize;
	}
}
