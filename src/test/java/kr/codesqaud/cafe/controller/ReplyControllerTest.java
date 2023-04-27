package kr.codesqaud.cafe.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import kr.codesqaud.cafe.global.config.Session;
import kr.codesqaud.cafe.reply.ReplyController;
import kr.codesqaud.cafe.reply.ReplyService;
import kr.codesqaud.cafe.reply.dto.LoadMoreReplyDto;
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

	private Long articleIdx = 1L;

	private List<ReplyResponse> replies = new ArrayList<>();

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

	@Test
	@DisplayName("더보기 버튼 클릭시 loadMoreReply 메서드 실행후 추가로 load될 댓글이 존재하면 댓글 list를 return한다.")
	void loadMoreReplySuccessTest() throws Exception {
		//given
		given(replyService.getCountOfReplies(articleIdx)).willReturn(10);
		given(replyService.getRepliesByIdx(any(LoadMoreReplyDto.class))).willReturn(replies);

		//when
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/articles/reply/loadMoreReply")
				.session(httpSession)
				.param("articleIdx", String.valueOf(articleIdx))
				.param("countOfRepliesInHtml", "5"))
			//then
			.andExpect(status().isOk())
			.andReturn();

		System.out.println(mvcResult.getResponse().getContentAsString());
		Assertions.assertThat(mvcResult.getResponse().getContentAsString()).isNotBlank();
	}

	@Test
	@DisplayName("더보기 버튼 클릭시 추가로 load될 댓글이 존재하지 않으면 null을 return한다.")
	void loadMoreReplyFailTest() throws Exception {
		//given
		given(replyService.getCountOfReplies(articleIdx)).willReturn(5);
		given(replyService.getRepliesByIdx(any(LoadMoreReplyDto.class))).willReturn(replies);

		//when
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/articles/reply/loadMoreReply")
				.session(httpSession)
				.param("articleIdx", String.valueOf(articleIdx))
				.param("countOfRepliesInHtml", "5"))
			//then
			.andExpect(status().isOk())
			.andReturn();

		Assertions.assertThat(mvcResult.getResponse().getContentAsString()).isBlank();
	}

}