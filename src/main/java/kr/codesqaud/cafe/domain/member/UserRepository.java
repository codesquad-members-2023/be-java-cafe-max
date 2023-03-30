package kr.codesqaud.cafe.domain.member;

import java.util.List;


public interface UserRepository {
    void save(User user);
    List<User> findAll();

}
