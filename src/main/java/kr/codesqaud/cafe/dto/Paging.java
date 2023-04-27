package kr.codesqaud.cafe.dto;

public class Paging {
	public static final int PAGE_SIZE = 15;
	public static final int PAGE_BAR_SIZE = 5;
	int totalPage;
	Integer prev;
	Integer start;
	Integer current;
	Integer end;
	Integer next;

	public Paging(int current, int articleSize) {
		this.current = current;
		totalPage = (int)Math.ceil(articleSize / (double)PAGE_SIZE);
		int lastBar = 5 * (int)Math.ceil(current / (double)PAGE_BAR_SIZE);
		end = setEnd(lastBar);
		next = setNext(lastBar);
		start = lastBar - 4;
		prev = setPrev(start);
	}

	private Integer setPrev(Integer start) {
		if (start > PAGE_BAR_SIZE) {
			return start - 5;
		}
		return null;
	}

	private Integer setNext(int lastBar) {
		if (totalPage > lastBar) {
			return lastBar + 1;
		}
		return null;
	}

	private Integer setEnd(int lastBar) {
		return Math.min(totalPage, lastBar);
	}

	public int getTotalPage() {
		return totalPage;
	}

	public Integer getPrev() {
		return prev;
	}

	public Integer getStart() {
		return start;
	}

	public Integer getCurrent() {
		return current;
	}

	public Integer getEnd() {
		return end;
	}

	public Integer getNext() {
		return next;
	}
}
