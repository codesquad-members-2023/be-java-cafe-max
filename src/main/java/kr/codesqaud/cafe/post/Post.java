package kr.codesqaud.cafe.post;

import java.time.LocalDateTime;

import kr.codesqaud.cafe.post.form.SimplePostForm;

public class Post {
	private Long id;

	private String nickname;

	private String title;

	private String textContent;

	private LocalDateTime createdDateTime;

	private Post(Builder builder) {
		this.id = builder.id;
		this.nickname = builder.nickname;
		this.createdDateTime = builder.createdDateTime;
		this.title = builder.title;
		this.textContent = builder.textContent;
	}

	public Long getId() {
		return id;
	}

	public String getNickname() {
		return nickname;
	}

	public String getTitle() {
		return title;
	}

	public String getTextContent() {
		return textContent;
	}

	public LocalDateTime getCreatedDateTime() {
		return createdDateTime;
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

	public static class Builder {
		private Long id;

		private String nickname;

		private String title;

		private String textContent;

		private LocalDateTime createdDateTime;

		public Builder(Long id) {
			this.id = id;
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

		public Post build() {
			return new Post(this);
		}
	}
}
