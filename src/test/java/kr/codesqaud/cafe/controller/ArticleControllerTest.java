package kr.codesqaud.cafe.controller;

import static kr.codesqaud.cafe.fixture.FixtureFactory.createArticle;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import kr.codesqaud.cafe.common.filter.RequestContext;
import kr.codesqaud.cafe.controller.dto.ArticleDetails;
import kr.codesqaud.cafe.controller.dto.ArticleResponse;
import kr.codesqaud.cafe.controller.dto.CommentResponse;
import kr.codesqaud.cafe.controller.dto.req.ArticleEditRequest;
import kr.codesqaud.cafe.controller.dto.req.PostingRequest;
import kr.codesqaud.cafe.domain.comment.Comment;
import kr.codesqaud.cafe.exception.NoAuthorizationException;
import kr.codesqaud.cafe.service.ArticleService;

@WebMvcTest(ArticleController.class)
@Import(RequestContext.class)
class ArticleControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ArticleService articleService;

	@DisplayName("[POST] 게시글 작성 정보가 주어질 때 게시글 작성요청을 하면 홈으로 리다이렉트된다.")
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

		then(articleService).should().post(any(PostingRequest.class), anyString());
	}

	@DisplayName("[POST] 로그인 되어 있지 않을 때 게시글 작성 요청을 하면 로그인 페이지로 리다이렉트된다.")
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

	@DisplayName("[GET] 게시글 상세보기 요청을 하면 게시글 상세화면 뷰가 반환된다.")
	@Test
	void givenNothing_whenShowArticleDetails_thenReturnsArticleDetailsView() throws Exception {
		// given
		ArticleDetails articleDetails = new ArticleDetails(ArticleResponse.from(createArticle()), List.of(
			CommentResponse.from(new Comment(1L, "이건 댓글!", LocalDateTime.now(), "익명의 사용자", 1L))));
		given(articleService.getArticleDetails(anyLong())).willReturn(articleDetails);

		// when & then
		mockMvc.perform(request(HttpMethod.GET, "/articles/1")
			                .sessionAttr("sessionedUser", "bruni"))
			.andExpect(status().isOk())
			.andExpect(view().name("qna/show"))
			.andExpect(model().attributeExists("article"))
			.andExpect(model().attributeExists("comments"))
			.andExpect(model().attributeExists("commentCount"))
			.andDo(print());
		then(articleService).should().getArticleDetails(1L);
	}

	@DisplayName("[GET] 로그인 되어 있지 않을 때 게시글 상세보기 요청을 하면 로그인 페이지로 리다이렉트된다.")
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

	@DisplayName("[GET] 게시글 수정화면보기 요청을 하면 게시글 수정 뷰가 반환된다.")
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

	@DisplayName("[GET] 세션이 없을 때 게시글 수정화면 보기를 요청하면 로그인 페이지로 리다이렉트된다.")
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

	@DisplayName("[GET] 로그인한 사용자와 게시글 작성자가 일치하지 않을 때 게시글 수정화면 보기를 요청하면 에러 뷰를 반환한다.")
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

	@DisplayName("[PUT] 게시글 수정정보가 주어질 때 게시글 수정 요청을 하면 게시글 상세화면 페이지로 리다이렉트된다.")
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

	@DisplayName("[PUT] 세션이 없을 때 게시글 수정요청을 하면 로그인 페이지로 리다이렉트된다.")
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

	@DisplayName("[DELETE] 게시글을 삭제 요청을 하면 홈으로 리다이렉트된다.")
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

	@DisplayName("[DELETE] 게시글 작성자와 세션 유저 아이디가 일치하지 않을 때 게시글 삭제 요청을 하면 에러 뷰를 반환한다.")
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

	@DisplayName("[DELETE] 세션이 없을 때 게시글 삭제 요청을 하면 로그인 페이지로 리다이렉트")
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
