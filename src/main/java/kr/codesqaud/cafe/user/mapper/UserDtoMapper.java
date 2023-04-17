package kr.codesqaud.cafe.user.mapper;

import kr.codesqaud.cafe.user.domain.User;
import kr.codesqaud.cafe.user.dto.RequestForm;
import kr.codesqaud.cafe.user.dto.ResponsePreview;
import kr.codesqaud.cafe.user.dto.ResponseProfile;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {

    UserDtoMapper INSTANCE = Mappers.getMapper(UserDtoMapper.class);

    User toUser(RequestForm requestForm);

    ResponseProfile toProfileDto(User user);

    ResponsePreview toPreviewDto(User user);
}
