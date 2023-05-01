package kr.codesqaud.cafe.controller;

import static kr.codesqaud.cafe.utils.ArticleTestUtils.*;
import static kr.codesqaud.cafe.utils.SessionTestUtils.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import kr.codesqaud.cafe.article.ArticleController;
import kr.codesqaud.cafe.article.ArticleService;
import kr.codesqaud.cafe.article.dto.ArticleResponse;
import kr.codesqaud.cafe.article.dto.ArticleTitleAndContentResponse;
import kr.codesqaud.cafe.reply.ReplyService;

@WebMvcTest(ArticleController.class)
class ArticleControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	ArticleService articleService;

	@MockBean
	ReplyService replyService;

	private MockHttpSession httpSession;

	private ArticleResponse articleResponse;

	private ArticleTitleAndContentResponse articleTitleAndContentResponse;

	private static final String TITLE = "title";
	private static final String CONTENT = "content";

	@BeforeEach
	public void setUp() {
		httpSession = createMockHttpSession();
		articleResponse = createArticleResponse();
		articleTitleAndContentResponse = createArticleTitleAndContentResponse();
	}

	@Test
	@DisplayName("post 메서드를 통해 article을 저장후 메인페이지로 리다이렉트 한다.")
	void postTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/articles")
				.session(httpSession)
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param(TITLE, TITLE)
				.param(CONTENT, CONTENT))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/"));
	}

	@Test
	@DisplayName("detail 메서드를 통해 해당 idx의 게시물 정보를 가져온다.")
	void detailTest() throws Exception {
		//given
		given(articleService.findArticleByIdx(1L)).willReturn(articleResponse);

		//when & then
		mockMvc.perform(MockMvcRequestBuilders.get("/articles/1")
				.session(httpSession))
			.andExpect(status().isOk())
			.andExpect(view().name("article/show"))
			.andExpect(model().attributeExists("article"))//객체 검증
			.andExpect(model().attribute("article", articleResponse));
	}

	@Test
	@DisplayName("해당 글을 작성한 사용자는 글의 내용을 업데이트 할수 있는 form으로 이동할수 있다.")
	void updateFormTest() throws Exception {
		//given
		given(articleService.validSessionIdAndArticleId(1L, "id")).willReturn(articleTitleAndContentResponse);

		//when & then
		mockMvc.perform(MockMvcRequestBuilders.get("/articles/update/1")
				.session(httpSession))
			.andExpect(status().isOk())
			.andExpect(view().name("article/updateForm"))
			.andExpect(model().attribute("article", articleTitleAndContentResponse))
			.andExpect(model().attribute("idx", 1L));
	}

	@Test
	@DisplayName("글 수정페이지에서 수정할 제목, 내용을 입력한후 수정버튼을 누르면 글이 수정된후 메인페이지로 이동한다.")
	void updateTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.put("/articles/update/1")
				.session(httpSession)
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param(TITLE, TITLE)
				.param(CONTENT, CONTENT))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/"));
	}

	@Test
	@DisplayName("글 세부 페이지에서 해당 삭제버튼을 누르면 글 삭제후 메인페이지로 이동한다.")
	void deleteTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/articles/1")
				.session(httpSession)
				.contentType(MediaType.APPLICATION_FORM_URLENCODED))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/"));
	}
}