package kr.codesqaud.cafe.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import kr.codesqaud.cafe.config.Session;
import kr.codesqaud.cafe.controller.dto.article.ArticleDTO;
import kr.codesqaud.cafe.controller.dto.article.ArticleInfoDTO;
import kr.codesqaud.cafe.service.ArticleService;

@WebMvcTest(ArticleController.class)
class ArticleControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	ArticleService articleService;

	private MockHttpSession httpSession;

	@BeforeEach
	public void setUp() {
		httpSession = new MockHttpSession();
		Session session = new Session("id", "testUser");
		httpSession.setAttribute(Session.LOGIN_USER, session);
	}

	@Test
	@DisplayName("postArticle 메서드를 통해 article을 저장후 메인페이지로 리다이렉트 한다.")
	void postArticleTest() throws Exception {
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/article/submit")
				.session(httpSession)
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("title", "title")
				.param("content", "content"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/"));

		MvcResult mvcResult = resultActions.andReturn();
		MockHttpSession resultSession = (MockHttpSession)mvcResult.getRequest().getSession();
		Session session = (Session)resultSession.getAttribute(Session.LOGIN_USER);

		assertAll(
			() -> Assertions.assertThat(session.getId().equals("id")).isTrue(),
			() -> Assertions.assertThat(session.getNickName().equals("testUser")).isTrue()
		);
	}

	@Test
	@DisplayName("showDetailArticle 메서드를 통해 해당 idx의 게시물 정보를 가져온다.")
	void showDetailArticleTest() throws Exception {

		//given
		ArticleDTO articleDto = new ArticleDTO("제목입니다", "내용입니다", 1L, "2023-4-10");
		Session session = (Session)httpSession.getAttribute(Session.LOGIN_USER);
		articleDto.setId(session.getId());
		articleDto.setNickName(session.getNickName());
		given(articleService.findArticleByIdx(1L)).willReturn(articleDto);

		//when & then
		mockMvc.perform(MockMvcRequestBuilders.get("/article/1")
				.session(httpSession))
			.andExpect(status().isOk())
			.andExpect(view().name("post/show"))
			.andExpect(model().attributeExists("article"))//객체 검증
			.andExpect(model().attribute("article", articleDto));
	}

	@Test
	@DisplayName("해당 글을 작성한 사용자는 글의 내용을 업데이트 할수 있는 form으로 이동할수 있다.")
	void showUpdateFormTest() throws Exception {
		ArticleInfoDTO articleInfoDto = new ArticleInfoDTO("제목입니다", "내용입니다");
		given(articleService.validSessionIdAndArticleId(1L, "id")).willReturn(articleInfoDto);

		mockMvc.perform(MockMvcRequestBuilders.get("/article/update-form/1")
				.session(httpSession))
			.andExpect(status().isOk())
			.andExpect(view().name("post/updateForm"))
			.andExpect(model().attribute("article", articleInfoDto))
			.andExpect(model().attribute("idx", 1L));
	}

	@Test
	@DisplayName("글 수정페이지에서 수정할 제목, 내용을 입력한후 수정버튼을 누르면 글이 수정된후 메인페이지로 이동한다.")
	void updateArticleTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.put("/article/submit/update-form/1")
				.session(httpSession)
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("title", "제목입니다")
				.param("content", "내용입니다"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/"));
	}

	@Test
	@DisplayName("글 세부 페이지에서 해당 삭제버튼을 누르면 글 삭제후 메인페이지로 이동한다.")
	void deleteArticleTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/article/1/delete")
				.session(httpSession)
				.contentType(MediaType.APPLICATION_FORM_URLENCODED))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/"));
	}

}