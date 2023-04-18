package kr.codesqaud.cafe.article.domain;

import static kr.codesqaud.cafe.global.util.DateUtil.*;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Reply {

	private String id;

	private Long replyIdx;

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

	public Reply(ResultSet rs) throws SQLException {
		this.content = rs.getString("content");
		this.date = rs.getString("date");
		this.nickName = rs.getString("nickName");
		this.articleIdx = rs.getLong("article_idx");
		this.replyIdx = rs.getLong("idx");
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

	public Long getReplyIdx() {
		return replyIdx;
	}
}
