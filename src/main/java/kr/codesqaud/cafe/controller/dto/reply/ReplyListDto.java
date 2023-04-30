package kr.codesqaud.cafe.controller.dto.reply;

import kr.codesqaud.cafe.controller.dto.Pageable;
import kr.codesqaud.cafe.domain.Reply;

import java.util.List;
import java.util.stream.Collectors;

public class ReplyListDto {
    private final List<ReplyReadDto> replies;
    private final boolean hasNext;

    private ReplyListDto(List<ReplyReadDto> replies, boolean hasNext) {
        this.replies = replies;
        this.hasNext = hasNext;
    }

    public List<ReplyReadDto> getReplies() {
        return replies;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public static ReplyListDto of(List<Reply> replies, Pageable pageable) {
        final int size = pageable.getSize();

        return new ReplyListDto(toReplyReadDtoList(replies, size), hasNext(replies, size));
    }

    private static boolean hasNext(List<Reply> replies, int size) {
        return replies.size() > size;
    }

    private static List<ReplyReadDto> toReplyReadDtoList(List<Reply> replies, int size) {
        return replies.subList(0, Math.min(replies.size(), size)).stream()
                .map(ReplyReadDto::from)
                .collect(Collectors.toList());
    }
}
