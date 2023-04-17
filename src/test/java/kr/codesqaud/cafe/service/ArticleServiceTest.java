package kr.codesqaud.cafe.service;

import static org.mockito.BDDMockito.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import kr.codesqaud.cafe.article.ArticleService;
import kr.codesqaud.cafe.article.domain.Article;
import kr.codesqaud.cafe.article.dto.ArticleDTO;
import kr.codesqaud.cafe.article.repository.ArticleRepository;
import kr.codesqaud.cafe.global.mapper.ArticleMapper;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

	@InjectMocks
	private ArticleService articleService;

	@Mock
	private ArticleRepository articleRepository;

	@Mock
	private ArticleMapper articleMapper;

	private ArticleDTO articleDto;

	private Article article;

	@BeforeEach
	void setup() {
		articleDto = new ArticleDTO("제목입니다", "내용입니다", 1l, "2023-4-15");
		article = new Article("제목입니다", "내용입니다", "id", "nickName");
	}

	@Test
	void postTest() {
		//given
		given(articleMapper.toArticle(articleDto)).willReturn(article);

		//when
		articleService.post(articleDto);

		// Then
		Assertions.assertThat(articleMapper.toArticle(articleDto).equals(article)).isTrue();
	}

	@Test
	void getArticleListTest() {
		//given
		//        Article article1 = new Article("재목입니다","본문입니다",);
		//        ArticleDTO articleDto = new ArticleDTO("제목입니다","내용입니다","")
		//        List<Article> articleList = new ArrayList<>(Arrays.asList(article,article1));
		//        given(articleRepository.findAll()).willReturn(articleList);
		//        given(articleMapper.toArticleDTO(article)).willReturn(articleDto);
		//        given(articleMapper.toArticleDTO(article1)).willReturn(articleDto);
		//
		//        //when
		//        List<ArticleDTO> articleDto = articleService.getArticleList();
		//
		//        //then
		//        Assertions.assertThat(articleDto.size()==2).isTrue();
		//        Assertions.assertThat(articleDto.get(0).equals(article1)).isTrue();
		//        Assertions.assertThat(articleDto.get(1).equals(article)).isTrue();

	}

	@Test
	void findArticleByIdx() {
	}

	@Test
	void updateArticle() {
	}

	@Test
	void validSessionIdAndArticleId() {
	}

	@Test
	void deleteArticleByIdx() {
	}
}