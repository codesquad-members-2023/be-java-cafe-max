package kr.codesqaud.cafe.domain;

import static kr.codesqaud.cafe.util.DateUtil.*;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Article {
	private String title;
	private String content;
	private Long idx;
	private String date;
	private String id;
	private String nickName;

	public Article(String title, String content, String id, String nickName) {
		this.title = title;
		this.content = content;
		this.date = getCurrentDate();
		this.id = id;
		this.nickName = nickName;
	}

	public Article(String title, String content, Long idx) {
		this.title = title;
		this.content = content;
		this.idx = idx;
	}

	public Article(ResultSet rs) throws SQLException {
		this.title = rs.getString("title");
		this.content = rs.getString("content");
		this.idx = rs.getLong("idx");
		this.date = rs.getString("date");
		this.id = rs.getString("id");
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

	public Long getIdx() {
		return idx;
	}

	public String getId() {
		return id;
	}

	public String getNickName() {
		return nickName;
	}
}
