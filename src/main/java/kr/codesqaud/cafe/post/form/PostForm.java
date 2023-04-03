package kr.codesqaud.cafe.post.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class PostForm {
	@NotEmpty
	@NotBlank
	@Size(max = 64, min = 2, message = "{error.nickname.size}")
	private String nickname;
	@NotEmpty
	@NotBlank
	@Size(max = 64, min = 2, message = "{error.title.size}")
	private String title;
	@NotEmpty
	@Size(max = 1000, min = 3, message = "{error.textContent.size}")
	private String textContent;

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTextContent() {
		return textContent;
	}

	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}
}
