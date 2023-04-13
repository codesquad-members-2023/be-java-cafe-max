package kr.codesqaud.cafe.user.mapper;

import kr.codesqaud.cafe.user.domain.User;
import kr.codesqaud.cafe.user.dto.UserFormDto;
import kr.codesqaud.cafe.user.dto.UserPreviewDto;
import kr.codesqaud.cafe.user.dto.UserProfileDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {

    UserDtoMapper INSTANCE = Mappers.getMapper(UserDtoMapper.class);

    User toUser(UserFormDto userFormDto);

    UserProfileDto toProfileDto(User user);

    UserPreviewDto toPreviewDto(User user);
}
