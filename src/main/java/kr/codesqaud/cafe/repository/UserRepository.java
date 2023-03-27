package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;

public interface UserRepository {
    Long save(User user);
}
