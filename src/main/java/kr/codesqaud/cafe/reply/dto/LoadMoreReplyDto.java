package kr.codesqaud.cafe.reply.dto;

public class LoadMoreReplyDto {

	private Long articleIdx;

	private int recordSize;

	private Long countOfRepliesInDb;

	private Long countOfRepliesInHtml;

	public LoadMoreReplyDto(Long articleIdx) {
		this.articleIdx = articleIdx;
		this.recordSize = 5;
		this.countOfRepliesInHtml = 0l; // 기본값을 주는 이유는 우리가 처음 post를 볼때는 이게 null이라 빼기연산을 못함 추후 더보기에선 setter를 통해 client로 부터온 값을 바인딩함
	}

	public Long getArticleIdx() {
		return articleIdx;
	}

	public int getRecordSize() {
		return recordSize;
	}

	public void setCountOfRepliesInDb(Long countOfRepliesInDb) {
		this.countOfRepliesInDb = countOfRepliesInDb;
	}

	public void setCountOfRepliesInHtml(Long countOfRepliesInHtml) {
		this.countOfRepliesInHtml = countOfRepliesInHtml;
	}

	/**
	 * db에 저장된 맨뒤 데이터에서 recordSize만큼 떨어진 데이터부터 recordSize 만큼 가져온다.
	 * @return
	 */
	public Long getStart() {
		if (hasMoreRepliesToLoad()) {
			long difference = countOfRepliesInDb - countOfRepliesInHtml;
			recordSize = (int)Math.min(difference, recordSize);
			return difference - recordSize;
		}
		return countOfRepliesInDb;
	}

	/**
	 * db에서 가져올 댓글이 더 남았는지 확인하는 기능 수행.
	 * @return
	 */
	public boolean hasMoreRepliesToLoad() {
		if (countOfRepliesInHtml < countOfRepliesInDb) {
			return true;
		}
		return false;
	}
}
