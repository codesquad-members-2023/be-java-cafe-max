package kr.codesqaud.cafe.user.repository;

import kr.codesqaud.cafe.user.service.User;
import kr.codesqaud.cafe.user.controller.request.UserRegisterRequest;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    User save(UserRegisterRequest userRegisterRequest);

    Optional<User> findById(String id);

    List<User> findAll();
}
