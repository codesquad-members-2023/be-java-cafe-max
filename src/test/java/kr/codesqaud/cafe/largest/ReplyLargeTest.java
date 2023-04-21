package kr.codesqaud.cafe.largest;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.DynamicTest.*;

import java.util.stream.Stream;

import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kr.codesqaud.cafe.domain.article.dto.request.ArticleSaveRequestDto;
import kr.codesqaud.cafe.domain.article.repository.ArticleRepository;
import kr.codesqaud.cafe.domain.reply.controller.ReplyController;
import kr.codesqaud.cafe.domain.reply.dto.request.ReplySaveRequestDto;
import kr.codesqaud.cafe.domain.reply.repository.ReplyRepository;

@SpringBootTest
public class ReplyLargeTest {

	@Autowired
	private ReplyController replyController;

	@Autowired
	private ReplyRepository replyRepository;

	@Autowired
	private ArticleRepository articleRepository;

	@Autowired
	HttpSession httpSession;

	@DisplayName("댓글 작성 및 삭제")
	@TestFactory
	Stream<DynamicTest> postAndDeleteReply() {
		return Stream.of(
			dynamicTest("댓글 작성", () -> {
				//given
				ArticleSaveRequestDto articleSaveRequestDto = new ArticleSaveRequestDto("제목입니다", "내용입니다");
				ReplySaveRequestDto replySaveRequestDto = new ReplySaveRequestDto("댓글");

				//when
				articleRepository.save(articleSaveRequestDto.toEntity("June"));
				replyRepository.save(replySaveRequestDto.toEntity(1L, "June"));

				//then
				assertThat(replyRepository.findByArticleId(1L).get(0).getContent()).isEqualTo("댓글");
				assertThat(replyRepository.findByArticleId(1L).get(0).getWriter()).isEqualTo("June");

			}),
			dynamicTest("댓글 삭제", () -> {
				//when
				replyRepository.delete("1");

				//then
				assertThat(replyRepository.findByArticleId(0L).size()).isEqualTo(0);
			})
		);
	}

}
