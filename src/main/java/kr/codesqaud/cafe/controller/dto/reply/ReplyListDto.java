package kr.codesqaud.cafe.controller.dto.reply;

import kr.codesqaud.cafe.controller.dto.Pageable;
import kr.codesqaud.cafe.domain.Reply;

import java.util.List;
import java.util.stream.Collectors;

public class ReplyListDto {
    private final List<ReplyReadDto> replies;
    private final boolean hasNext;
    private final boolean isLast;

    private ReplyListDto(List<ReplyReadDto> replies, boolean hasNext, boolean isLast) {
        this.replies = replies;
        this.hasNext = hasNext;
        this.isLast = isLast;
    }

    public List<ReplyReadDto> getReplies() {
        return replies;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public boolean isLast() {
        return isLast;
    }

    public static ReplyListDto of(List<Reply> replies, Pageable pageable) {
        final int size = pageable.getSize();

        return new ReplyListDto(toReplyReadDtoList(replies, size), hasNext(replies, size), isLast(replies, size));
    }

    private static boolean isLast(List<Reply> replies, int size) {
        return replies.size() <= size;
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
