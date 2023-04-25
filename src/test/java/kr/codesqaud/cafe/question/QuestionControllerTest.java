package kr.codesqaud.cafe.question;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import kr.codesqaud.cafe.question.controller.QuestionController;
import kr.codesqaud.cafe.question.controller.response.QuestionDetailResponseDTO;
import kr.codesqaud.cafe.question.domain.QuestionEntity;
import kr.codesqaud.cafe.question.exception.QuestionNotExistException;
import kr.codesqaud.cafe.question.service.QuestionService;
import kr.codesqaud.cafe.user.controller.response.AuthSession;

@WebMvcTest(QuestionController.class)
public class QuestionControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private QuestionService questionService;

	private MockHttpSession session;

	private static MockedStatic<QuestionDetailResponseDTO> questionDetailDTOMockedStatic;

	@Nested
	@DisplayName("게시글 상세 보기 페이지 테스트")
	class QuestionDetailTest {

		@BeforeEach
		void initTest() throws QuestionNotExistException {
			session = new MockHttpSession();
			session.setAttribute("authSession", new AuthSession(1, "writer"));

			given(questionService.findById(1))
				.willReturn(new QuestionEntity(1, 1, "writer", "title", "contents", false, null));

			questionDetailDTOMockedStatic = mockStatic(QuestionDetailResponseDTO.class);
			questionDetailDTOMockedStatic.when(
					() -> QuestionDetailResponseDTO.from(
						new QuestionEntity(1, 1, "writer", "title", "contents", false, null)))
				.thenReturn(new QuestionDetailResponseDTO(1, 1, "writer", "title", "contents", null));
		}

		@AfterEach
		void close() {
			questionDetailDTOMockedStatic.close();
		}

		@Test
		@DisplayName("게시글 상세 보기 페이지 접속시 게시물 보기 페이지로 이동")
		void testQuestionDetail() throws Exception {
			MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();
			paramsMap.add("id", "1");

			mockMvc.perform(get("/questions/1").params(paramsMap).session(session))
				.andExpect(status().is2xxSuccessful())
				.andExpect(view().name("qna/show"));
		}

		@Test
		@DisplayName("잘못된 게시물 번호 입력시 게시글 정보를 반환하지 않는다.")
		void testQuestionDetailFail() throws Exception {
			MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();
			paramsMap.add("id", "incorrect questionId");

			mockMvc.perform(get("/questions/1").params(paramsMap).session(session))
				.andExpect(status().is2xxSuccessful())
				.andExpect(model().attributeDoesNotExist("questionDetailDTO"))
				.andExpect(view().name("qna/show"));
		}
	}
}
