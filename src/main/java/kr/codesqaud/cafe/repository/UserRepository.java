package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.model.User;


public interface UserRepository extends Repository<User, Long>{

    User findById(Long id);
}
