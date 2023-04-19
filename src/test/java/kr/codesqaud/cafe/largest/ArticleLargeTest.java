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
				ArticleSaveRequestDto articleSaveRequestDto = new ArticleSaveRequestDto("제목입니다", "내용입니다");

				//when
				articleRepository.save(articleSaveRequestDto.toEntity("June"));

				//then
				assertThat(articleRepository.findById(1L).get().getTitle()).isEqualTo("제목입니다");
				assertThat(articleRepository.findById(1L).get().getContent()).isEqualTo("내용입니다");
				assertThat(articleRepository.findById(1L).get().getWriter()).isEqualTo("June");

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
