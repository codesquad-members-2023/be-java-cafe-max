package kr.codesqaud.cafe.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import kr.codesqaud.cafe.controller.dto.ArticleDto;
import kr.codesqaud.cafe.controller.dto.req.ArticleEditRequest;
import kr.codesqaud.cafe.exception.NoAuthorizationException;
import kr.codesqaud.cafe.service.ArticleService;

@WebMvcTest(ArticleController.class)
class ArticleControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ArticleService articleService;

	@DisplayName("[POST] 게시글 작성 - 정상호출")
	@Test
	void givenPostingData_whenPosting_thenRedirectsHome() throws Exception {
		// given
		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("title", "테스트코드는 어려워!");
		body.add("content", "왜 어려운거지?");

		// when & then
		mockMvc.perform(post("/articles")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.params(body)
				.sessionAttr("sessionedUser", "bruni"))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/"))
			.andDo(print());

		then(articleService).should().posting(any(ArticleDto.class));
	}

	@DisplayName("[POST] 게시글 작성 - 로그인 되어 있지 않을 때 로그인 페이지로 리다이렉트")
	@Test
	void givenNoSession_whenPosting_thenRedirectsLoginPage() throws Exception {
		// given
		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("title", "테스트코드는 어려워!");
		body.add("content", "왜 어려운거지?");

		// when & then
		mockMvc.perform(post("/articles")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.params(body))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/user/login"))
			.andDo(print());

		then(articleService).shouldHaveNoInteractions();
	}

	@DisplayName("[GET] 게시글 상세보기 - 정상호출")
	@Test
	void givenNothing_whenShowArticleDetails_thenReturnsArticleDetailsView() throws Exception {
		// given
		ArticleDto articleDto = new ArticleDto(1L, "bruni", "테스트코드", "어려워", LocalDateTime.now());
		given(articleService.findById(anyLong())).willReturn(articleDto);

		// when & then
		mockMvc.perform(request(HttpMethod.GET, "/articles/1")
				.sessionAttr("sessionedUser", "bruni"))
			.andExpect(status().isOk())
			.andExpect(view().name("qna/show"))
			.andExpect(model().attributeExists("article"))
			.andDo(print());

		then(articleService).should().findById(anyLong());
	}

	@DisplayName("[GET] 게시글 상세보기 - 로그인 되어 있지 않을 때 로그인 페이지로 리다이렉트")
	@Test
	void givenNoSession_whenShowArticleDetails_thenRedirectsLoginPage() throws Exception {
		// given

		// when & then
		mockMvc.perform(request(HttpMethod.GET, "/articles/1"))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/user/login"))
			.andDo(print());

		then(articleService).shouldHaveNoInteractions();
	}

	@DisplayName("[GET] 게시글 수정 페이지 - 정상호출")
	@Test
	void givenNothing_whenShowArticleEditPage_thenReturnsArticleEditView() throws Exception {
		// given

		// when & then
		mockMvc.perform(request(HttpMethod.GET, "/articles/1/form")
				.sessionAttr("sessionedUser", "bruni"))
			.andExpect(status().isOk())
			.andExpect(view().name("qna/edit_form"))
			.andExpect(model().attributeExists("articleId"))
			.andDo(print());

		then(articleService).should().validateHasAuthorization(1L, "bruni");
	}

	@DisplayName("[GET] 게시글 수정 페이지 - 세션이 없을 때 로그인 페이지로 리다이렉트")
	@Test
	void givenNoSession_whenShowArticleEditPage_thenRedirectsLoginPage() throws Exception {
		// given

		// when & then
		mockMvc.perform(request(HttpMethod.GET, "/articles/1/form"))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/user/login"))
			.andDo(print());

		then(articleService).shouldHaveNoInteractions();
	}

	@DisplayName("[GET] 게시글 수정 페이지 - 로그인한 사용자와 게시글 작성자가 일치하지 않을 때 error 뷰 반환")
	@Test
	void givenNotEqualUserId_whenShowArticleEditPage_thenReturnsErrorView() throws Exception {
		// given
		doThrow(NoAuthorizationException.class).when(articleService).validateHasAuthorization(anyLong(), anyString());

		// when & then
		mockMvc.perform(request(HttpMethod.GET, "/articles/1/form")
				.sessionAttr("sessionedUser", "bruni"))
			.andExpect(status().isOk())
			.andExpect(view().name("error"))
			.andDo(print());

		then(articleService).should().validateHasAuthorization(1L, "bruni");
	}

	@DisplayName("[PUT] 게시글 수정 - 정상호출")
	@Test
	void givenArticleEditInfo_whenEditArticle_thenRedirectsArticleDetailsPage() throws Exception {
		// given
		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("title", "수정된 제목");
		body.add("content", "수정된 내용");

		willDoNothing().given(articleService).editArticle(anyLong(), any(ArticleEditRequest.class));

		// when & then
		mockMvc.perform(put("/articles/1")
				.sessionAttr("sessionedUser", "bruni")
				.params(body))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/articles/1"))
			.andDo(print());

		then(articleService).should().editArticle(anyLong(), any(ArticleEditRequest.class));
	}

	@DisplayName("[PUT] 게시글 수정 - 세션이 없을 때 로그인 페이지로 리다이렉트")
	@Test
	void givenNoSession_whenEditArticle_thenRedirectsLoginPage() throws Exception {
		// given
		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("title", "수정된 제목");
		body.add("content", "수정된 내용");

		// when & then
		mockMvc.perform(put("/articles/1")
				.params(body))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/user/login"))
			.andDo(print());

		then(articleService).shouldHaveNoInteractions();
	}

	@DisplayName("[DELETE] 게시글 삭제 - 정상호출")
	@Test
	void givenNothing_whenDeleteArticle_thenRedirectsHomePage() throws Exception {
		// given
		doNothing().when(articleService).validateHasAuthorization(anyLong(), anyString());
		doNothing().when(articleService).deleteArticle(anyLong());

		// when & then
		mockMvc.perform(delete("/articles/1")
				.sessionAttr("sessionedUser", "bruni"))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/"))
			.andDo(print());

		then(articleService).should().validateHasAuthorization(1L, "bruni");
		then(articleService).should().deleteArticle(1L);
	}

	@DisplayName("[DELETE] 게시글 삭제 - 게시글 작성자와 세션 유저 아이디가 일치하지 않을 때 error 뷰를 반환한다.")
	@Test
	void givenNotEqualUserId_whenDeleteArticle_thenReturnsErrorView() throws Exception {
		// given
		willThrow(NoAuthorizationException.class).given(articleService)
			.validateHasAuthorization(anyLong(), anyString());
		doNothing().when(articleService).deleteArticle(anyLong());

		// when & then
		mockMvc.perform(delete("/articles/1")
				.sessionAttr("sessionedUser", "bruni"))
			.andExpect(status().isOk())
			.andExpect(view().name("error"))
			.andDo(print());

		then(articleService).should().validateHasAuthorization(1L, "bruni");
		then(articleService).should(times(0)).deleteArticle(anyLong());
	}

	@DisplayName("[DELETE] 게시글 삭제 - 세션이 없을 때 로그인 페이지로 리다이렉트")
	@Test
	void givenNoSession_whenDeleteArticle_thenRedirectsLoginPage() throws Exception {
		// given
		doNothing().when(articleService).validateHasAuthorization(anyLong(), anyString());
		doNothing().when(articleService).deleteArticle(anyLong());

		// when & then
		mockMvc.perform(delete("/articles/1"))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/user/login"))
			.andDo(print());

		then(articleService).shouldHaveNoInteractions();
	}
}
