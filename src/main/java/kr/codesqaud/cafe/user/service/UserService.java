package kr.codesqaud.cafe.user.service;

import kr.codesqaud.cafe.exception.DuplicateKeyException;
import kr.codesqaud.cafe.exception.LoginFailedException;
import kr.codesqaud.cafe.exception.UserUpdateFailedException;
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

    public void validateAndUpdateUser(UserUpdateForm userUpdateForm) {
        if (checkPassword(userUpdateForm)) {
            if (checkDuplicateName(userUpdateForm.getUserName())) {
                userRepository.update(userUpdateForm.toUser());
            } else {
                throw new UserUpdateFailedException("중복된 이름이 존재합니다.", "name");
            }
        } else {
            throw new UserUpdateFailedException("비밀번호가 틀렸습니다.", "password");
        }
    }

    private boolean checkPassword(UserUpdateForm userUpdateForm) {
        String password = userRepository.findByUserId(userUpdateForm.getUserId()).getPassword();
        return userUpdateForm.getPassword().equals(password);
    }

    private boolean checkDuplicateName(String userName) {
        return !userRepository.containsUserName(userName);
    }
}
