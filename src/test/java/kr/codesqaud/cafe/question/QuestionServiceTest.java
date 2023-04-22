package kr.codesqaud.cafe.question;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import kr.codesqaud.cafe.question.domain.Question;
import kr.codesqaud.cafe.question.exception.QuestionNotExistException;
import kr.codesqaud.cafe.question.repository.QuestionRepository;
import kr.codesqaud.cafe.question.service.QuestionService;

class QuestionServiceTest {
	private final QuestionRepository questionRepository = Mockito.mock(QuestionRepository.class);
	private QuestionService questionService;

	@BeforeEach
	void initTest() {
		questionService = new QuestionService(questionRepository);
	}

	@Test
	@DisplayName("Q&A 게시글 상세보기: id값에 해당하는 게시글을 불러온다")
	void testFindById() throws QuestionNotExistException {
		Question questionSample = new Question(1, "writer", "title", "contents", LocalDateTime.now());
		Mockito.when(questionRepository.findById(1))
			.thenReturn(questionSample);

		Question questionResult = questionService.findById(1);
		assertThat(questionResult.getId()).isEqualTo(questionSample.getId());
		assertThat(questionResult.getWriter()).isEqualTo(questionSample.getWriter());
		assertThat(questionResult.getTitle()).isEqualTo(questionSample.getTitle());
		assertThat(questionResult.getContents()).isEqualTo(questionSample.getContents());
		assertThat(questionResult.getRegistrationDateTime()).isEqualTo(questionSample.getRegistrationDateTime());

		verify(questionRepository, atMostOnce()).findById(1);
	}

	@Test
	@DisplayName("Q&A 게시글 목록 불러오기: 지정한 페이지 크기 만큼의 게시글을 불러온다")
	void testFindAll() {
		int pageSize = 2;
		List<Question> questionsSample = new ArrayList<>();
		Question questionSample1 = new Question(1, "writer", "title", "contents", LocalDateTime.now());
		Question questionSample2 = new Question(2, "writer", "title", "contents", LocalDateTime.now());
		Question questionSample3 = new Question(3, "writer", "title", "contents", LocalDateTime.now());
		questionsSample.add(questionSample1);
		questionsSample.add(questionSample2);
		questionsSample.add(questionSample3);

		Mockito.when(questionRepository.findAll(0, pageSize))
			.thenReturn(questionsSample.subList(0, pageSize));

		List<Question> questionsResult = questionService.findAll(0, pageSize);
		assertThat(questionsResult).hasSameSizeAs(questionsSample.subList(0, pageSize));
	}
}
