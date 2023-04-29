package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.dto.SignupRequestDto;
import kr.codesqaud.cafe.model.User;


public interface UserRepository extends Repository<User, Long>{

    void save(SignupRequestDto signupRequestDto);
    User findById(Long id);
}
