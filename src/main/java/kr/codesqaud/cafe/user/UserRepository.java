package kr.codesqaud.cafe.user;

import java.util.List;

/**
 * 회원 저장소 인터페이스
 */
public interface UserRepository {

    void save(User user);

    List<User> findAll();

    User findById(String userId);
}
