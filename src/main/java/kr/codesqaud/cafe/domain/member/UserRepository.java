package kr.codesqaud.cafe.domain.member;

import java.util.List;

public interface UserRepository {
    void save(UserLog userLog);
    List<UserLog> findAll();

}
