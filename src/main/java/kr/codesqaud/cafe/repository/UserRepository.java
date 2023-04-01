package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.controller.dto.ProfileEditDTO;
import kr.codesqaud.cafe.domain.User;

import java.util.List;

public interface UserRepository {
    void save(User user);

    List<User> findAll();

    User findUserById(int id);

    void updateUser(ProfileEditDTO profileEditDTO,int id);
}
