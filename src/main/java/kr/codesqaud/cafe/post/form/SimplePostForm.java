package kr.codesqaud.cafe.post.form;

import java.time.LocalDateTime;

public class SimplePostForm {
	private Long id;

	private String nickname;

	private String title;

	private String textContent;

	private LocalDateTime createdDateTime;

	public SimplePostForm() {
	}

	private SimplePostForm(Builder builder) {
		id = builder.id;
		nickname = builder.nickname;
		title = builder.title;
		textContent = builder.textContent;
		createdDateTime = builder.createdDateTime;
	}

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

	public static class Builder {
		private Long id;

		private String nickname;

		private String title;

		private String textContent;

		private LocalDateTime createdDateTime;

		public Builder id(Long id) {
			this.id = id;
			return this;
		}

		public Builder nickname(String nickname) {
			this.nickname = nickname;
			return this;
		}

		public Builder title(String title) {
			this.title = title;
			return this;
		}

		public Builder textContent(String textContent) {
			this.textContent = textContent;
			return this;
		}

		public Builder createdDateTime(LocalDateTime createdDateTime) {
			this.createdDateTime = createdDateTime;
			return this;
		}

		public SimplePostForm build() {
			return new SimplePostForm(this);
		}
	}

}
