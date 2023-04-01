package kr.codesqaud.cafe.domain.member;

import java.util.Map;


public interface UserRepository {
    void save(User user, int index);

    Map<Integer, User> findAll();

}
