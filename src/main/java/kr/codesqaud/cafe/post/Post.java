package kr.codesqaud.cafe.post;

import java.time.LocalDateTime;

public class Post {
	private final Long id;

	private final String nickname;

	private final String title;

	private final String textContent;

	private final LocalDateTime createdDateTime;

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

	public static class Builder {
		private final Long id;

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
