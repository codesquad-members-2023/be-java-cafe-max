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
import kr.codesqaud.cafe.web.exception.user.UserDuplicatedException;
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
        validateDuplicatedUserId(requestDto.getUserId());
        validateDuplicatedUserEmail(requestDto.getEmail());
        User saveUser = userRepository.save(requestDto.toEntity());
        return new UserResponseDto(saveUser);
    }

    // 회원 아이디 중복 검증
    public void validateDuplicatedUserId(String userId) {
        userRepository.findByUserId(userId).ifPresent((user) -> {
            throw new UserDuplicatedException(UserExceptionType.ALREADY_EXIST_USERID);
        });
    }

    // 회원 이메일 중복 검증
    public void validateDuplicatedUserEmail(String email) {
        userRepository.findByEmail(email).ifPresent((user) -> {
            throw new UserDuplicatedException(UserExceptionType.ALREADY_EXIST_EMAIL);
        });
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
        validator.validateLoginPassword(loginUser.getPassword(), user.getPassword());
        session.setAttribute("user", new UserResponseDto(user));
    }

    // 회원 정보 수정
    public UserResponseDto modifyUser(Long id, UserSavedRequestDto requestDto) {
        User requestUser = requestDto.toEntity(id);
        User currentUser = findUser(id);
        // 기존 이메일과 수정하고자 하는 이메일이 같지 않다면 수정하고자 하는 이메일이 중복되지 않았는지 검증합니다.
        if (!validator.isEmailUnChanged(currentUser.getEmail(), requestUser.getEmail())) {
            validateDuplicatedUserEmail(requestUser.getEmail());
        }
        User modifyUser = userRepository.modify(requestUser);
        return new UserResponseDto(modifyUser);
    }

    // 비밀번호 확인
    public void confirmPassword(Long id, UserSavedRequestDto requestDto) {
        String requestPassword = requestDto.getPassword();
        String password = findUser(id).getPassword();
        validator.validateEqualConfirmPassword(requestPassword, password);
    }
}
