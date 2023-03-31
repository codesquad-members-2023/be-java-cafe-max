package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.dto.UserRegisterRequest;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    User save(UserRegisterRequest userRegisterRequest);

    Optional<User> findById(long id);

    List<User> findAll();
}
