package kr.codesqaud.cafe.domain.user.repository;

import kr.codesqaud.cafe.domain.user.User;

import java.util.List;


public interface UserRepository {
    void save(User user);

     List<User> findAll();

     User findById(String id);

    default void update(User user){};


}
