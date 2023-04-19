package kr.codesqaud.cafe.user.service;

import kr.codesqaud.cafe.exception.DuplicateKeyException;
import kr.codesqaud.cafe.exception.LoginFailedException;
import kr.codesqaud.cafe.user.domain.User;
import kr.codesqaud.cafe.user.dto.*;
import kr.codesqaud.cafe.user.repository.UserJdbcRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserJdbcRepository userRepository;

    public UserService(UserJdbcRepository userJdbcRepository) {
        this.userRepository = userJdbcRepository;
    }

    public String addUser(UserAddForm userAddForm) {
        if (userRepository.containsUserId(userAddForm.getUserId())) {
            throw new DuplicateKeyException("중복된 아이디가 이미 존재합니다.");
        }
        return userRepository.save(userAddForm.toUser());
    }

    public SessionUser loginCheck(UserLoginForm userLoginForm) {
        if (userRepository.containsUserId(userLoginForm.getUserId())) {
            User user = userRepository.findByUserId(userLoginForm.getUserId());
            if (userLoginForm.getPassword().equals(user.getPassword())) {
                return SessionUser.from(user);
            } else {
                throw new LoginFailedException("비밀번호가 틀렸습니다.");
            }
        } else {
            throw new LoginFailedException("존재하지 않는 아이디입니다.");
        }
    }

    public UserResponse getUser(String userId) {
        return UserResponse.from(userRepository.findByUserId(userId));
    }

    public List<UserResponse> getUserList() {
        return userRepository.findAll().stream()
                .map(UserResponse::from)
                .collect(Collectors.toList());
    }

    public boolean checkPassword(UserUpdateForm userUpdateForm) {
        User user = userRepository.findByUserId(userUpdateForm.getUserId());
        return userUpdateForm.getPassword().equals(user.getPassword());
    }

    public boolean updateUser(UserUpdateForm userUpdateForm) {
        if (userRepository.containsUserName(userUpdateForm.getUserName())) {
            return false;
        }
        userRepository.update(userUpdateForm.toUser());
        return true;
    }
}
