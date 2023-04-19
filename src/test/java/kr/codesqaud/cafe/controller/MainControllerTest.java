package kr.codesqaud.cafe.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import kr.codesqaud.cafe.article.ArticleService;
import kr.codesqaud.cafe.article.dto.ArticleResponse;
import kr.codesqaud.cafe.global.MainController;

@WebMvcTest(MainController.class)
class MainControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	ArticleService articleService;

	@Test
	@DisplayName("mainPage메서드를 통해 article의 list를 가져온후 나열한다.")
	void mainPageTest() throws Exception {
		//given
		List<ArticleResponse> articleList = new ArrayList<>();
		given(articleService.getArticles()).willReturn(articleList);

		//when & then
		mockMvc.perform(MockMvcRequestBuilders.get("/"))
			.andExpect(status().isOk())
			.andExpect(view().name("index"))
			.andExpect(model().attributeExists("articleList"))//객체 검증
			.andExpect(model().attribute("articleList", articleList));
	}

}