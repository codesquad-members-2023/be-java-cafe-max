package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.dto.user.UserResponse;
import kr.codesqaud.cafe.dto.user.UserSaveRequest;
import kr.codesqaud.cafe.dto.user.UserUpdateRequest;
import kr.codesqaud.cafe.exception.user.DuplicateUserIdException;
import kr.codesqaud.cafe.exception.user.MismatchedPasswordException;
import kr.codesqaud.cafe.exception.user.UserNotFoundException;
import kr.codesqaud.cafe.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveUser(UserSaveRequest userSaveRequest) throws DuplicateUserIdException { // 새로운 회원 저장하기
        if (userRepository.isExists(userSaveRequest.getUserId())) { // userId 중복 여부 검사
            throw new DuplicateUserIdException(userSaveRequest);
        }
        userRepository.save(User.from(userSaveRequest));
    }

    public List<UserResponse> getAllUsers() { // 모든 회원 가져오기
        return userRepository.findAll().stream().map(UserResponse::from).collect(Collectors.toUnmodifiableList());
    }

    public void updateUser(UserUpdateRequest userUpdateRequest) throws MismatchedPasswordException { // 기존 회원의 정보를 수정하기
        String currentPassword = userRepository.findByUserId(userUpdateRequest.getUserId()).getPassword();

        if (!currentPassword.equals(userUpdateRequest.getCurrentPassword())) { // 현재 비밀번호 일치 여부 검사
            throw new MismatchedPasswordException();
        }
        userRepository.save(User.from(userUpdateRequest));
    }

    public UserResponse findByUserId(String userId) {
        if (!userRepository.isExists(userId)) {
            throw new UserNotFoundException();
        }

        return UserResponse.from(userRepository.findByUserId(userId));
    }
}
