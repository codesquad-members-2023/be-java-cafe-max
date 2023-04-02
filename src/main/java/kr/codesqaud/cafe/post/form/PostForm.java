package kr.codesqaud.cafe.post.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class PostForm {
	@NotEmpty
	@NotBlank
	@Size(max = 64, min = 2, message = "닉네임이 2글자 이상 64 글자 이하여야 한다")
	private String nickname;
	@NotEmpty
	@NotBlank
	@Size(max = 64, min = 2, message = "제목은 2글자 이상 64 글자 이하여야 한다")
	private String title;
	@NotEmpty
	@Size(max = 1000, min = 3, message = "본문은 3글자 이상 1000 글자 이하여야 한다")
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
