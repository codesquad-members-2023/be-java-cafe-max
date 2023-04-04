package kr.codesqaud.cafe.domain.member.repository;

import kr.codesqaud.cafe.domain.member.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;


public interface UserRepository {
    void save(User user);

     List<User> findAll();

     User findById(String id);

    default void update(User user){};


}
