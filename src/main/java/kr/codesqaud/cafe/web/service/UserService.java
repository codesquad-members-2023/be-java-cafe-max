package kr.codesqaud.cafe.web.service;

import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpSession;
import kr.codesqaud.cafe.domain.user.User;
import kr.codesqaud.cafe.domain.user.UserRepository;
import kr.codesqaud.cafe.web.dto.user.UserLoginRequestDto;
import kr.codesqaud.cafe.web.dto.user.UserResponseDto;
import kr.codesqaud.cafe.web.dto.user.UserSavedRequestDto;
import kr.codesqaud.cafe.web.dto.user.UserUpdatedResponseDto;
import kr.codesqaud.cafe.web.exception.user.UserExceptionType;
import kr.codesqaud.cafe.web.exception.user.UserNotFoundException;
import kr.codesqaud.cafe.web.exception.user.UserNotLoginMatchingException;
import kr.codesqaud.cafe.web.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserValidator validator;

    @Autowired
    public UserService(UserRepository userRepository, UserValidator validator) {
        this.userRepository = userRepository;
        this.validator = validator;
    }

    // 전체 회원 목록
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream()
            .map(UserResponseDto::new)
            .collect(Collectors.toUnmodifiableList());
    }

    // 회원가입
    public UserResponseDto signUp(UserSavedRequestDto requestDto) {
        validator.validateDuplicatedUserId(requestDto.toEntity());
        validator.validateDuplicatedUserEmail(requestDto.toEntity());
        User saveUser = userRepository.save(requestDto.toEntity());
        return new UserResponseDto(saveUser);
    }

    // 특정 회원 조회
    public User findUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> {
            throw new UserNotFoundException(UserExceptionType.NOT_FOUND_USER);
        });
    }

    // 특정 회원 조회
    public User findUser(String userId) {
        return userRepository.findByUserId(userId).orElseThrow(() -> {
            throw new UserNotFoundException(UserExceptionType.NOT_FOUND_USER);
        });
    }

    // 특정 회원 조회 (비밀번호 포함)
    public UserUpdatedResponseDto findUpdateUser(Long id) {
        return new UserUpdatedResponseDto(findUser(id));
    }

    // 로그인
    public void login(UserLoginRequestDto requestDto, HttpSession session) {
        User loginUser = requestDto.toEntity();
        User user = userRepository.findByUserId(loginUser.getUserId()).orElseThrow(() -> {
            throw new UserNotLoginMatchingException(UserExceptionType.NOT_MATCH_LOGIN);
        });
        validator.validateLogin(loginUser, user);
        session.setAttribute("user", new UserResponseDto(user));
    }

    // 회원 정보 수정
    public UserResponseDto modifyUser(Long id, UserSavedRequestDto requestDto) {
        User requestUser = requestDto.toEntity(id);
        User currentUser = findUser(id);
        validator.validateModifiedUserEmail(requestUser, currentUser);
        User modifyUser = userRepository.modify(requestUser);
        return new UserResponseDto(modifyUser);
    }

    // 비밀번호 확인
    public void confirmPassword(Long id, UserSavedRequestDto requestDto) {
        User requestUser = requestDto.toEntity();
        User currentUser = findUser(id);
        validator.validateEqualPassword(requestUser, currentUser);
    }
}
