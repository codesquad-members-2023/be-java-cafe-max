package kr.codesqaud.cafe.post.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PostForm {
	@NotNull
	@NotBlank
	@Size(max = 64, min = 2)
	private String nickname;
	@NotNull
	@NotBlank
	@Size(max = 64, min = 2)
	private String title;
	@NotNull
	@Size(max = 1000, min = 3)
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
