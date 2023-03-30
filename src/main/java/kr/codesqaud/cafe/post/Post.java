package kr.codesqaud.cafe.post;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;

import kr.codesqaud.cafe.post.form.SimplePostForm;

public class Post {
	private static final AtomicLong postId = new AtomicLong();
	private final Long id;

	private String nickname;

	private String title;

	private String textContent;

	private LocalDateTime createdDateTime;

	public Post(Long id) {
		this.id = id;
	}

	public static long createNewId() {
		return postId.getAndIncrement();
	}

	public Long getId() {
		return id;
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
		simpleForm.setTextContent(getTextContent().substring(0,30));
		simpleForm.setTitle(getTitle());
		simpleForm.setCreatedDateTime(getCreatedDateTime());
		return simpleForm;
	}
}
