package kr.codesqaud.cafe.comment.mapper;

import kr.codesqaud.cafe.comment.domain.Comment;
import kr.codesqaud.cafe.comment.dto.RequestCommentForm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CommentDtoMapper {

    CommentDtoMapper INSTANCE = Mappers.getMapper(CommentDtoMapper.class);

    @Mapping(target = "userId", source = "userId")
    Comment toComment(RequestCommentForm requestCommentForm, String userId);

}
