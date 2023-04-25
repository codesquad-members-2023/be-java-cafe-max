package kr.codesqaud.cafe.reply.dto;

public class LoadMoreReplyDto {

	private Long articleIdx;

	private int recordSize;

	private Long countOfRepliesInDb;

	private Long countOfRepliesInHtml;

	// replydb에서 꺼내올 댓글의 시작 지점
	private Long start;

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
	 * db에 저장된 맨뒤에서 recordSize만큼 떨어진 데이터부터 가져온다.
	 * @return
	 */

	//todo 근데 if문은 여기말고 controller에 있는게 나을듯 db에 필요없는 접근함
	public Long getStart() {
		if (countOfRepliesInHtml >= countOfRepliesInDb) {
			return countOfRepliesInDb;
		}
		long difference = countOfRepliesInDb - countOfRepliesInHtml;
		recordSize = (int)Math.min(difference, recordSize);
		return difference - recordSize;
	}
}
