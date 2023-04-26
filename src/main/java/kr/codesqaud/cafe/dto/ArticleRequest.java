package kr.codesqaud.cafe.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ArticleRequest {
	@NotBlank(message = "제목을 적어주세요.")
	private String title;
	private String writer;
	@Size(min = 3, max = 1000, message = "글 내용은 3글자 이상 1000 글자 이하여야 합니다.")
	private String contents;

	public ArticleRequest(String title, String writer, String contents) {
		this.title = title;
		this.writer = writer;
		this.contents = contents;
	}

	public String getTitle() {
		return title;
	}

	public String getWriter() {
		return writer;
	}

	public String getContents() {
		return contents;
	}
}

