package kr.codesqaud.cafe.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import kr.codesqaud.cafe.reply.domain.Reply;
import kr.codesqaud.cafe.reply.dto.LoadMoreReplyDto;
import kr.codesqaud.cafe.reply.dto.ReplyRequest;
import kr.codesqaud.cafe.reply.dto.ReplyResponse;
import kr.codesqaud.cafe.reply.dto.Result;

public class ReplyTestUtils {
	private static final String CONTENT = "댓글 내용";
	private static final String NICK_NAME = "tester";
	private static final String USER_ID = "testId";
	private static final Long articleIdx = 1L;
	private static final String DATE = "2023-4-27";
	private static final Long REPLY_IDX = 1L;

	public static Reply createReply() {
		return new Reply(USER_ID, articleIdx, NICK_NAME, CONTENT);
	}

	public static ReplyRequest createReplyRequest() {
		return new ReplyRequest(CONTENT, NICK_NAME);
	}

	public static ReplyResponse createReplyResponse() {
		return new ReplyResponse(NICK_NAME, CONTENT, DATE, articleIdx, REPLY_IDX);
	}

	public static LoadMoreReplyDto createLoadedReplyDto() {
		return new LoadMoreReplyDto(articleIdx, 10, 0);
	}

	public static List<Reply> createReplies() {
		return new ArrayList<>(Arrays.asList(createReply()));
	}

	public static Result createResult() {
		return new Result(true, null);
	}
}
