package kr.codesqaud.cafe.app.user.service;

import java.util.List;
import java.util.stream.Collectors;
import kr.codesqaud.cafe.app.user.controller.dto.UserLoginRequest;
import kr.codesqaud.cafe.app.user.controller.dto.UserResponse;
import kr.codesqaud.cafe.app.user.controller.dto.UserSavedRequest;
import kr.codesqaud.cafe.app.user.entity.User;
import kr.codesqaud.cafe.app.user.repository.UserRepository;
import kr.codesqaud.cafe.app.user.validator.UserValidator;
import kr.codesqaud.cafe.errors.errorcode.UserErrorCode;
import kr.codesqaud.cafe.errors.exception.ResourceNotFoundException;
import kr.codesqaud.cafe.errors.exception.RestApiException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserValidator validator;

    public UserService(UserRepository userRepository, UserValidator validator) {
        this.userRepository = userRepository;
        this.validator = validator;
    }

    // 전체 회원 목록
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
            .map(UserResponse::new)
            .collect(Collectors.toUnmodifiableList());
    }

    // 회원가입
    @Transactional
    public UserResponse signUp(UserSavedRequest userRequest) {
        validateDuplicatedUserId(userRequest.getUserId());
        validateDuplicatedUserEmail(userRequest.getEmail());
        User savedUser = userRepository.save(userRequest.toEntity());
        return new UserResponse(savedUser);
    }

    // 회원 아이디 중복 검증
    private void validateDuplicatedUserId(String userId) {
        userRepository.findByUserId(userId).ifPresent((user) -> {
            throw new RestApiException(UserErrorCode.ALREADY_EXIST_USERID);
        });
    }

    // 회원 이메일 중복 검증
    private void validateDuplicatedUserEmail(String email) {
        userRepository.findByEmail(email).ifPresent((user) -> {
            throw new RestApiException(UserErrorCode.ALREADY_EXIST_EMAIL);
        });
    }

    // 특정 회원 조회
    public UserResponse findUser(Long id) {
        User findUser = userRepository.findById(id).orElseThrow(() -> {
            throw new ResourceNotFoundException(UserErrorCode.NOT_FOUND_USER);
        });
        return new UserResponse(findUser);
    }

    // 특정 회원 조회
    public UserResponse findUser(String userId) {
        User findUser = userRepository.findByUserId(userId).orElseThrow(() -> {
            throw new ResourceNotFoundException(UserErrorCode.NOT_FOUND_USER);
        });
        return new UserResponse(findUser);
    }

    // 로그인
    public User login(UserLoginRequest requestDto) {
        User loginUser = requestDto.toEntity();
        User user = userRepository.findByUserId(loginUser.getUserId()).orElseThrow(() -> {
            throw new RestApiException(UserErrorCode.NOT_MATCH_LOGIN);
        });
        validator.validateLoginPassword(loginUser.getPassword(), user.getPassword());
        return user;
    }

    // 회원 정보 수정
    @Transactional
    public UserResponse modifyUser(Long id, UserSavedRequest userRequest) {
        User originalUser = userRepository.findById(id).orElseThrow();
        // 기존 이메일과 수정하고자 하는 이메일이 같지 않다면 수정하고자 하는 이메일이 중복되지 않았는지 검증합니다.
        if (!validator.isEmailUnChanged(originalUser.getEmail(), userRequest.getEmail())) {
            validateDuplicatedUserEmail(userRequest.getEmail());
        }
        validator.validateEqualConfirmPassword(userRequest.getPassword(),
            originalUser.getPassword());
        originalUser.modify(userRequest.toEntity());
        return new UserResponse(userRepository.modify(originalUser));
    }
}
