package kr.codesqaud.cafe.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import kr.codesqaud.cafe.article.ArticleService;
import kr.codesqaud.cafe.article.dto.ArticleListItem;
import kr.codesqaud.cafe.mainPage.MainController;
import kr.codesqaud.cafe.mainPage.PaginationDto;

@WebMvcTest(MainController.class)
class MainControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	ArticleService articleService;

	private List<ArticleListItem> articles;

	private static final Long COUNT_OF_ARTICLES = 10L;

	@BeforeEach
	void setUp() {
		articles = new ArrayList<>();
	}

	@Test
	@DisplayName("mainPage메서드를 통해 article의 list를 가져온후 나열한다.")
	void mainPageTest() throws Exception {
		//given
		given(articleService.getArticleList(any(PaginationDto.class))).willReturn(articles);
		given(articleService.getCountOfArticles()).willReturn(COUNT_OF_ARTICLES);

		//when & then
		mockMvc.perform(MockMvcRequestBuilders.get("/"))
			.andExpect(status().isOk())
			.andExpect(view().name("index"))
			.andExpect(model().attributeExists("pagination"))
			.andExpect(model().attributeExists("articles"))//객체 검증
			.andExpect(model().attribute("articles", articles))
			.andExpect(model().attribute("countOfArticles", COUNT_OF_ARTICLES));
	}

}