package kr.codesqaud.cafe.user.mapper;

import kr.codesqaud.cafe.user.domain.User;
import kr.codesqaud.cafe.user.dto.RequestUserForm;
import kr.codesqaud.cafe.user.dto.ResponseUserPreview;
import kr.codesqaud.cafe.user.dto.ResponseUserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserDtoMapper {

    UserDtoMapper INSTANCE = Mappers.getMapper(UserDtoMapper.class);

    User toUser(RequestUserForm requestUserForm);

    ResponseUserProfile toProfileDto(User user);

    ResponseUserPreview toPreviewDto(User user);
}
