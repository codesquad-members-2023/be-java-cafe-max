package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.dto.user.UserResponse;
import kr.codesqaud.cafe.dto.user.UserSaveRequest;
import kr.codesqaud.cafe.dto.user.UserUpdateRequest;
import kr.codesqaud.cafe.exception.user.DuplicateUserIdException;
import kr.codesqaud.cafe.exception.user.MismatchedPasswordException;
import kr.codesqaud.cafe.exception.user.UserNotFoundException;
import kr.codesqaud.cafe.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;


    public UserService(@Qualifier("jdbcUserRepository") UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveUser(UserSaveRequest userSaveRequest) throws DuplicateUserIdException { // 새로운 회원 저장하기
        if (userRepository.isExists(userSaveRequest.getUserId())) { // userId 중복 여부 검사
            throw new DuplicateUserIdException(userSaveRequest);
        }
        userRepository.save(userSaveRequest.toUser());
    }

    public List<UserResponse> getAllUsers() { // 모든 회원 가져오기
        return userRepository.findAll().stream().map(UserResponse::from).collect(Collectors.toUnmodifiableList());
    }

    public void updateUser(UserUpdateRequest userUpdateRequest) { // 기존 회원의 정보를 수정하기
        if (!userRepository.findByUserId(userUpdateRequest.getUserId()).isPasswordMatched(userUpdateRequest.getCurrentPassword())) { // 현재 비밀번호 일치 여부 검사
            throw new MismatchedPasswordException(userUpdateRequest);
        }
        userRepository.update(userUpdateRequest.toUser());
    }

    public UserResponse findByUserId(String userId) { // DTO 변환은 service 역할 vs controller 역할
        if (!userRepository.isExists(userId)) {
            throw new UserNotFoundException();
        }

        return UserResponse.from(userRepository.findByUserId(userId));
    }

    public UserUpdateRequest makeUserUpdateRequestByUserId(String userId) { // service 역할이 맞을까
        if (!userRepository.isExists(userId)) {
            throw new UserNotFoundException();
        }

        return UserUpdateRequest.from(userRepository.findByUserId(userId));
    }
}
