package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.dto.user.UserResponse;
import kr.codesqaud.cafe.dto.user.UserSaveRequest;
import kr.codesqaud.cafe.dto.user.UserUpdateRequest;
import kr.codesqaud.cafe.exception.user.AlreadyUserExistenceException;
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

    public void saveUser(UserSaveRequest userSaveRequest) throws AlreadyUserExistenceException {
        if (userRepository.exist(userSaveRequest.getUserId())) {
            throw new AlreadyUserExistenceException(userSaveRequest);
        }
        userRepository.save(userSaveRequest.toUser());
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(UserResponse::from).collect(Collectors.toUnmodifiableList());
    }

    public void updateUser(UserUpdateRequest userUpdateRequest) {
        if (!userRepository.findByUserId(userUpdateRequest.getUserId()).isPasswordMatched(userUpdateRequest.getCurrentPassword())) {
            throw new MismatchedPasswordException(userUpdateRequest);
        }
        userRepository.update(userUpdateRequest.toUser());
    }

    public UserResponse findByUserId(String userId) {
        if (!userRepository.exist(userId)) {
            throw new UserNotFoundException();
        }

        return UserResponse.from(userRepository.findByUserId(userId));
    }

    public UserUpdateRequest makeUserUpdateRequestByUserId(String userId) {
        if (!userRepository.exist(userId)) {
            throw new UserNotFoundException();
        }

        return UserUpdateRequest.from(userRepository.findByUserId(userId));
    }
}
