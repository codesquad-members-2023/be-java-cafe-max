package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.dto.user.UserResponse;
import kr.codesqaud.cafe.dto.user.UserSaveRequest;
import kr.codesqaud.cafe.dto.user.UserUpdateRequest;
import kr.codesqaud.cafe.exception.user.AlreadyNameExistenceException;
import kr.codesqaud.cafe.exception.user.AlreadyUserExistenceException;
import kr.codesqaud.cafe.exception.user.LoginFailedException;
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


    public UserService(@Qualifier("mySqlUserRepository") UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String saveUser(UserSaveRequest userSaveRequest) {
        if (userRepository.exist(userSaveRequest.getUserId())) {
            throw new AlreadyUserExistenceException(userSaveRequest);
        }
        if (userRepository.existByName(userSaveRequest.getName())) {
            throw new AlreadyNameExistenceException(userSaveRequest);
        }
        return userRepository.save(userSaveRequest.toUser());
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(UserResponse::from).collect(Collectors.toUnmodifiableList());
    }

    public int updateUser(UserUpdateRequest userUpdateRequest) {
        if (!userRepository.findByUserId(userUpdateRequest.getUserId()).orElseThrow(UserNotFoundException::new).isPasswordMatched(userUpdateRequest.getCurrentPassword())) {
            throw new MismatchedPasswordException(userUpdateRequest);
        }
        return userRepository.update(userUpdateRequest.toUser());
    }

    public UserResponse findByUserId(String userId) {
        return UserResponse.from(userRepository.findByUserId(userId).orElseThrow(UserNotFoundException::new));
    }

    public UserUpdateRequest makeUserUpdateRequestByUserId(String userId) {
        return UserUpdateRequest.from(userRepository.findByUserId(userId).orElseThrow(UserNotFoundException::new));
    }

    public UserResponse login(String userId, String password) {
        if (!userRepository.exist(userId)) {
            throw new LoginFailedException();
        }

        final User user = userRepository.findByUserId(userId).orElseThrow(UserNotFoundException::new);
        if (!user.isPasswordMatched(password)) {
            throw new LoginFailedException();
        }

        return UserResponse.from(user);
    }
}
