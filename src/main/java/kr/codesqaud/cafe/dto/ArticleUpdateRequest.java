package kr.codesqaud.cafe.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ArticleUpdateRequest {
	@NotBlank(message = "제목을 적어주세요.")
	private String title;
	@Size(min = 3, max = 1000, message = "글 내용은 3글자 이상 1000 글자 이하여야 합니다.")
	private String contents;

	public ArticleUpdateRequest(String title, String contents) {
		this.title = title;
		this.contents = contents;
	}

	public String getTitle() {
		return title;
	}

	public String getContents() {
		return contents;
	}
}
