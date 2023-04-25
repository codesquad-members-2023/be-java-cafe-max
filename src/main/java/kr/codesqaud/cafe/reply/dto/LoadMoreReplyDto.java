package kr.codesqaud.cafe.reply.dto;

public class LoadMoreReplyDto {

	private Long articleIdx;

	private int recordSize;

	private int countOfReplies;

	public LoadMoreReplyDto(Long articleIdx, int countOfReplies) {
		this.articleIdx = articleIdx;
		this.recordSize = 5;
		this.countOfReplies = countOfReplies;
	}

	public Long getArticleIdx() {
		return articleIdx;
	}

	public int getRecordSize() {
		return recordSize;
	}

	public int getCountOfReplies() {
		return countOfReplies;
	}
}
