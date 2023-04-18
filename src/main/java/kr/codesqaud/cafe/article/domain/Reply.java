package kr.codesqaud.cafe.article.domain;

import static kr.codesqaud.cafe.global.util.DateUtil.*;

public class Reply {

	private String id;

	private Long idx;

	private Long articleIdx;

	private String date;

	private String nickName;

	private String content;

	public Reply(String id, Long articleIdx, String nickName, String content) {
		this.id = id;
		this.articleIdx = articleIdx;
		this.date = getCurrentDate();
		this.nickName = nickName;
		this.content = content;
	}

	public String getId() {
		return id;
	}

	public Long getArticleIdx() {
		return articleIdx;
	}

	public String getDate() {
		return date;
	}

	public String getNickName() {
		return nickName;
	}

	public String getContent() {
		return content;
	}
}
