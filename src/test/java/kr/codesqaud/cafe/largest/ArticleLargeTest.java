package kr.codesqaud.cafe.largest;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.DynamicTest.*;

import java.util.NoSuchElementException;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

import kr.codesqaud.cafe.domain.article.controller.ArticleController;
import kr.codesqaud.cafe.domain.article.dto.request.ArticleSaveRequestDto;
import kr.codesqaud.cafe.domain.article.repository.ArticleRepository;

@SpringBootTest
public class ArticleLargeTest {

	@Autowired
	private ArticleController articleController;

	@Autowired
	private ArticleRepository articleRepository;

	@DisplayName("게시글 작성 및 조회")
	@TestFactory
	Stream<DynamicTest> postArticle() {
		return Stream.of(
                dynamicTest("게시글 작성", () -> {
                    //given
                    final String title = "제목입니다.";
                    final String content = "내용입니다.";
                    final String writer = "June";
                    Article article = new ArticleSaveRequestDto(title, content).toEntity(writer);

                    //when
                    articleRepository.save(article);

                    //then
                    Article target = articleRepository.findById(1L).get();
                    assertThat(target.getTitle()).isEqualTo(title);
                    assertThat(target.getContent()).isEqualTo(content);
                    assertThat(target.getWriter()).isEqualTo(writer);
                }),
			dynamicTest("존재하지 않는 게시글 조회시", () -> {
				//given
				Model model = new ConcurrentModel();
				//when & then
				assertThatThrownBy(() -> articleController.viewArticle(2L, model)).isInstanceOf(
					NoSuchElementException.class);
			})
		);
	}
}
