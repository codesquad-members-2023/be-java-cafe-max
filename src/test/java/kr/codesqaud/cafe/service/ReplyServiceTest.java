package kr.codesqaud.cafe.service;

import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.Arrays;
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

	private static final String content = "댓글 내용";
	private static final String nickName = "tester";
	private static final String userId = "testId";
	private static final Long articleIdx = 1L;
	private static final String date = "2023-4-27";
	private static final Long replyIdx = 1L;

	@BeforeEach
	void setup() {
		reply = new Reply(userId, articleIdx, nickName, content);
		replyRequest = new ReplyRequest(content, nickName);
		replyResponse = new ReplyResponse(nickName, content, date, articleIdx, replyIdx);
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
		LoadMoreReplyDto loadedReplyDto = new LoadMoreReplyDto(articleIdx, 10, 0);
		List<Reply> replies = new ArrayList<>(Arrays.asList(reply));
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
		given(replyService.findIdByIdx(replyIdx)).willReturn(userId);

		//when
		Result result = replyService.delete(userId, replyIdx);

		//then
		Assertions.assertThat(result.isOk()).isTrue();
	}

	@Test
	@DisplayName("사용자는 다른 사용자가 작성한 댓글을 삭제할수 없다.")
	void deleteFailTest() {
		//given
		given(replyService.findIdByIdx(replyIdx)).willReturn(userId + "k");

		//when
		Result result = replyService.delete(userId, replyIdx);

		//then
		Assertions.assertThat(result.isOk()).isFalse();
	}
}