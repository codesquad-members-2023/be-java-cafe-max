package kr.codesqaud.cafe.service;

import static org.mockito.BDDMockito.*;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import kr.codesqaud.cafe.global.mapper.ReplyMapper;
import kr.codesqaud.cafe.reply.ReplyService;
import kr.codesqaud.cafe.reply.domain.Reply;
import kr.codesqaud.cafe.reply.dto.LoadMoreReplyDto;
import kr.codesqaud.cafe.reply.dto.ReplyRequest;
import kr.codesqaud.cafe.reply.dto.ReplyResponse;
import kr.codesqaud.cafe.reply.dto.Result;
import kr.codesqaud.cafe.reply.repository.ReplyRepository;
import kr.codesqaud.cafe.utils.ReplyTestUtils;

@ExtendWith(MockitoExtension.class)
class ReplyServiceTest {

	@InjectMocks
	ReplyService replyService;

	@Mock
	ReplyRepository replyRepository;

	@Mock
	ReplyMapper replyMapper;

	private Reply reply;
	private ReplyRequest replyRequest;
	private ReplyResponse replyResponse;
	private LoadMoreReplyDto loadedReplyDto;
	private List<Reply> replies;
	private static final Long REPLY_IDX = 1L;
	private static final String USER_ID = "testId";

	@BeforeEach
	void setUp() {
		reply = ReplyTestUtils.createReply();
		replyRequest = ReplyTestUtils.createReplyRequest();
		replyResponse = ReplyTestUtils.createReplyResponse();
		loadedReplyDto = ReplyTestUtils.createLoadedReplyDto();
		replies = ReplyTestUtils.createReplies();
	}

	@Test
	@DisplayName("save 메서드를 통해 댓글을 저장할수 있다.")
	void saveTest() {
		//given
		given(replyMapper.toReply(replyRequest)).willReturn(reply);
		given(replyRepository.saveReply(reply)).willReturn(reply);
		given(replyMapper.toReplyResponse(reply)).willReturn(replyResponse);

		//when
		replyService.save(replyRequest);

		//then
		verify(replyRepository, times(1)).saveReply(reply);
	}

	@Test
	@DisplayName("ArticleIdx를 통해 해당 게시글의 댓글을 가져올수 있다.")
	void getRepliesByIdxTest() {
		//given
		given(replyRepository.findAllReply(loadedReplyDto)).willReturn(replies);
		given(replyMapper.toReplyResponse(any(Reply.class))).willReturn(replyResponse);

		//when
		List<ReplyResponse> result = replyService.getRepliesByIdx(loadedReplyDto);

		//then
		Assertions.assertThat(result.size()).isEqualTo(1);
		Assertions.assertThat(result.get(0)).isEqualTo(replyResponse);
	}

	@Test
	@DisplayName("사용자는 자신이 작성한 댓글을 삭제할수 있다.")
	void deleteTest() {
		//given
		given(replyService.findIdByIdx(REPLY_IDX)).willReturn(USER_ID);

		//when
		Result result = replyService.delete(USER_ID, REPLY_IDX);

		//then
		Assertions.assertThat(result.isOk()).isTrue();
	}

	@Test
	@DisplayName("사용자는 다른 사용자가 작성한 댓글을 삭제할수 없다.")
	void deleteFailTest() {
		//given
		given(replyService.findIdByIdx(REPLY_IDX)).willReturn(USER_ID + "k");

		//when
		Result result = replyService.delete(USER_ID, REPLY_IDX);

		//then
		Assertions.assertThat(result.isOk()).isFalse();
	}
}