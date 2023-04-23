package kr.codesqaud.cafe.reply;

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

import kr.codesqaud.cafe.global.config.Session;
import kr.codesqaud.cafe.reply.dto.ReplyRequest;
import kr.codesqaud.cafe.reply.dto.ReplyResponse;
import kr.codesqaud.cafe.reply.dto.Result;

@WebMvcTest(ReplyController.class)
class ReplyControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	ReplyService replyService;

	private MockHttpSession httpSession;

	@BeforeEach
	public void setUp() {
		httpSession = new MockHttpSession();
		Session session = new Session("id", "testUser");
		httpSession.setAttribute(Session.LOGIN_USER, session);
	}

	@Test
	@DisplayName("댓글 작성시 replyDB에 댓글 저장후 ReplyResponse 객체를 json 형태로 return 한다.")
	void reply() throws Exception {
		ReplyResponse replyResponse = new ReplyResponse("testUser", "댓글 내용입니다",
			"2023-4-23", 1L, 1L);

		given(replyService.save(any(ReplyRequest.class))).willReturn(replyResponse);

		mockMvc.perform(MockMvcRequestBuilders.post("/articles/reply/1")
				.session(httpSession)
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("content", "댓글 내용입니다")
				.param("nickName", "testUser"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.nickName").value(replyResponse.getNickName()))
			.andExpect(jsonPath("$.articleIdx").value(replyResponse.getArticleIdx()))
			.andExpect(jsonPath("$.replyIdx").value(replyResponse.getReplyIdx()))
			.andExpect(jsonPath("$.content").value(replyResponse.getContent()));
	}

	@Test
	@DisplayName("삭제버튼 클릭시 delete 메서드 실행후 result 객체를 json형태로 return한다.")
	void deleteReply() throws Exception {
		Result result = new Result(true, null);

		given(replyService.delete("id", 1L)).willReturn(result);

		mockMvc.perform(MockMvcRequestBuilders.delete(("/articles/reply/1"))
				.session(httpSession))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.ok").value(true));
	}
}