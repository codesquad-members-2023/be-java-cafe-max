package kr.codesqaud.cafe.article.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class ArticlePostRequest {
	@NotBlank
	@Pattern(regexp = "^\\s*[\\S\\s]+\\s*$")
	private final String title;

	@NotBlank
	@Pattern(regexp = "^[\\s\\S]{3,1000}$")
	private final String content;

	private String userId;

	private String nickName;

	public ArticlePostRequest(String title, String content) {
		this.title = title;
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public String getUserId() {
		return userId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
}
