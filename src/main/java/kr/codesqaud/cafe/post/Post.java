package kr.codesqaud.cafe.post;

import java.time.LocalDateTime;

import kr.codesqaud.cafe.post.form.SimplePostForm;

public class Post {
	private Long id;

	private String nickname;

	private String title;

	private String textContent;

	private LocalDateTime createdDateTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public LocalDateTime getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(LocalDateTime createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	public SimplePostForm mappingSimpleForm() {
		SimplePostForm simpleForm = new SimplePostForm();
		simpleForm.setId(getId());
		simpleForm.setNickname(getNickname());
		simpleForm.setTextContent(getTextContent());
		simpleForm.setTitle(getTitle());
		simpleForm.setCreatedDateTime(getCreatedDateTime());
		return simpleForm;
	}
}
