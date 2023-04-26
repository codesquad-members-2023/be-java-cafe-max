package kr.codesqaud.cafe.user.service;

import kr.codesqaud.cafe.exception.DuplicateUserIdException;
import kr.codesqaud.cafe.exception.DuplicateUserNameException;
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
            throw new DuplicateUserIdException("중복된 아이디가 이미 존재합니다. 다른 아이디를 입력해주세요.");
        }
        if (userRepository.containsUserName(userAddForm.getUserName())) {
            throw new DuplicateUserNameException("중복된 이름이 이미 존재합니다. 다른 이름을 입력해주세요.");
        }
        return userRepository.save(userAddForm.toEntity());
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

    public SessionUser updateUser(UserUpdateForm userUpdateForm) {
        validateUpdateForm(userUpdateForm);
        User user = userUpdateForm.toEntity();
        userRepository.update(user);
        return SessionUser.from(user);
    }

    private void validateUpdateForm(UserUpdateForm userUpdateForm) {
        String password = userRepository.findByUserId(userUpdateForm.getUserId()).getPassword();
        if (!userUpdateForm.getPassword().equals(password)) {
            throw new UserUpdateFailedException("비밀번호가 틀렸습니다.", "password");
        }
        if (userRepository.containsUserName(userUpdateForm.getUserName())) {
            throw new UserUpdateFailedException("중복된 이름이 존재합니다.", "name");
        }
    }

}
