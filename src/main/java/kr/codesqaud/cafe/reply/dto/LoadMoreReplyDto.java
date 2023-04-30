package kr.codesqaud.cafe.reply.dto;

public class LoadMoreReplyDto {

	private Long articleIdx;

	private int recordSize = 5;

	private Integer countOfRepliesInDb;

	private Integer countOfRepliesInHtml;

	private boolean isLoad;

	public LoadMoreReplyDto(Long articleIdx, Integer countOfRepliesInDb, Integer countOfRepliesInHtml) {
		this.articleIdx = articleIdx;
		this.countOfRepliesInHtml = countOfRepliesInHtml;
		this.countOfRepliesInDb = countOfRepliesInDb;
		this.isLoad = isLoadButtonAvailable();
	}

	public Long getArticleIdx() {
		return articleIdx;
	}

	public int getRecordSize() {
		return recordSize;
	}

	private boolean isLoadButtonAvailable() {
		if (countOfRepliesInDb > 5) {
			return true;
		}
		return false;
	}

	/**
	 * db에 저장된 맨뒤 데이터에서 recordSize만큼 떨어진 데이터부터 recordSize 만큼 가져온다.
	 * @return
	 */
	public Long getStart() {
		long difference = countOfRepliesInDb - countOfRepliesInHtml;
		recordSize = (int)Math.min(difference, recordSize);
		return difference - recordSize;
	}

}
