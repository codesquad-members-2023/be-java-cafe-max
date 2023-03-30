package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(UserDto userDto);
    Optional<User> findByUserID(String uerID);
    Optional<User> findByEmail(String email);
    Optional<User> findByNickname(String nickname);
    Optional<User> findByPassword(String password);
    List<User> findAll();
}
