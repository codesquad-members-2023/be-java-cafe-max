package kr.codesqaud.cafe.domain.member;

import kr.codesqaud.cafe.dto.UserDto;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface UserRepository {
    void save(UserDto userDto);
    List<UserDto> findAll();

}
