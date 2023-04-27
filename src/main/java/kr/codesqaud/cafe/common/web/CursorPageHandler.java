package kr.codesqaud.cafe.common.web;

public class CursorPageHandler {
	private long cursor = 0;
	private final int size = 5;

	public CursorPageHandler(long cursor) {
		this.cursor = cursor;
	}

	public long getCursor() {
		return cursor;
	}

	public int getSize() {
		return size;
	}
}
