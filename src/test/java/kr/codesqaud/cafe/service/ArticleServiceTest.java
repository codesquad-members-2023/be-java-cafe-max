package kr.codesqaud.cafe.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import kr.codesqaud.cafe.controller.dto.req.PostingRequest;
import kr.codesqaud.cafe.repository.impl.ArticleMemoryRepository;

class ArticleServiceTest {

	private ArticleService articleService;

	@BeforeEach
	void setArticleRepository() {
		articleService = new ArticleService(new ArticleMemoryRepository());
	}

	@DisplayName("게시글을 올릴 때")
	@Nested
	class PostingTest {

		@DisplayName("포스팅 정보가 주어지면 포스팅에 성공한다.")
		@Test
		void givenPostingRequest_whenPosting_thenReturnsNothing() {
			// given
			PostingRequest postingRequest = new PostingRequest("브루니", "게시글 제목", "게시글 내용");

			// when & then
			assertThatCode(() -> articleService.posting(postingRequest))
				.doesNotThrowAnyException();
		}
	}
}