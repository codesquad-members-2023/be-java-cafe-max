package kr.codesqaud.cafe.article.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class Article {
	private String title;
	private String content;
	private Long articleIdx;
	private String date;
	private String userId;
	private String nickName;

	public Article(String title, String content, String userId, String nickName) {
		this.title = title;
		this.content = content;
		this.userId = userId;
		this.nickName = nickName;
	}

	public Article(String title, String content, Long articleIdx) {
		this.title = title;
		this.content = content;
		this.articleIdx = articleIdx;
	}

	public Article(ResultSet rs) throws SQLException {
		this.title = rs.getString("title");
		this.content = rs.getString("content");
		this.articleIdx = rs.getLong("article_idx");
		this.date = rs.getString("date");
		this.userId = rs.getString("user_id");
		this.nickName = rs.getString("nickName");
	}

	public String getDate() {
		return date;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public Long getArticleIdx() {
		return articleIdx;
	}

	public String getUserId() {
		return userId;
	}

	public String getNickName() {
		return nickName;
	}

	public boolean validUserIdAndArticleId(String SessionId) {
		return Objects.equals(SessionId, userId);
	}
}
