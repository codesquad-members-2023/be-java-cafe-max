package kr.codesqaud.cafe.dto.comment;

import java.util.List;
import java.util.stream.Collectors;

import kr.codesqaud.cafe.domain.Comment;

public class CommentListDto {
    private final List<CommentReadDto> comments;
    private final boolean hasNext;

    public CommentListDto(List<CommentReadDto> comments, boolean hasNext) {
        this.comments = comments;
        this.hasNext = hasNext;
    }

    public List<CommentReadDto> getComments() {
        return comments;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public static CommentListDto of(List<Comment> comments){
        final int size = comments.size();
        return new CommentListDto(toCommentReadDtoList(comments,size),hasNext(comments,size));
    }
    private static boolean hasNext(List<Comment> comments, int size){
        return comments.size() > size;
    }

    private static List<CommentReadDto> toCommentReadDtoList(List<Comment> comments, int size){
        return comments.subList(0,Math.min(comments.size(),size))
                .stream()
                .map(CommentReadDto::from)
                .collect(Collectors.toList());
    }

}
