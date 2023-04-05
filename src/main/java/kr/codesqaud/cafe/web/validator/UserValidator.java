package kr.codesqaud.cafe.web.validator;

import kr.codesqaud.cafe.domain.user.User;
import kr.codesqaud.cafe.domain.user.UserRepository;
import kr.codesqaud.cafe.web.exception.user.UserDuplicatedException;
import kr.codesqaud.cafe.web.exception.user.UserExceptionType;
import kr.codesqaud.cafe.web.exception.user.UserNotFoundException;
import kr.codesqaud.cafe.web.exception.user.UserNotLoginMatchingException;
import kr.codesqaud.cafe.web.exception.user.UserNotPasswordMatchingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {

    private final UserRepository repository;

    @Autowired
    public UserValidator(UserRepository repository) {
        this.repository = repository;
    }

    // 두 회원의 비밀번호가 일치하는지 검증
    public void validateEqualPassword(User requestUser, User currentUser) {
        if (!isPasswordMatching(requestUser, currentUser)) {
            throw new UserNotPasswordMatchingException(UserExceptionType.NOT_MATCH_PASSWORD);
        }
    }

    // 로그인 검증 (아이디 존재, 비밀번호 일치)
    public User validateLogin(User requestUser) {
        User user = repository.findByUserId(requestUser.getUserId()).orElseThrow(() -> {
            throw new UserNotLoginMatchingException(UserExceptionType.NOT_MATCH_LOGIN);
        });

        if (!isPasswordMatching(requestUser, user)) {
            throw new UserNotLoginMatchingException(UserExceptionType.NOT_MATCH_LOGIN);
        }

        return user;
    }

    // 비밀번호가 일치하는지 검사
    private boolean isPasswordMatching(User requestUser, User user) {
        return requestUser.getPassword().equals(user.getPassword());
    }

    // 회원 존재 검증
    public User validateExistUser(Long id) {
        return repository.findById(id).orElseThrow(() -> {
            throw new UserNotFoundException(UserExceptionType.NOT_FOUND_USER);
        });
    }

    // 회원 존재 검증
    public User validateExistUser(String userId) {
        return repository.findByUserId(userId).orElseThrow(() -> {
            throw new UserNotFoundException(UserExceptionType.NOT_FOUND_USER);
        });
    }

    // 회원 아이디 중복 검증
    public void validateDuplicatedUserId(User requestUser) {
        repository.findByUserId(requestUser.getUserId()).ifPresent((user) -> {
            throw new UserDuplicatedException(UserExceptionType.ALREADY_EXIST_USERID);
        });
    }

    // 회원 이메일 중복 검증
    public void validateDuplicatedUserEmail(User requestUser) {
        repository.findByEmail(requestUser.getEmail()).ifPresent((user) -> {
            throw new UserDuplicatedException(UserExceptionType.ALREADY_EXIST_EMAIL);
        });
    }

    // 수정하려는 이메일 검증
    public void validateModifiedUserEmail(User requestUser, User currentUser) {
        // 기존 이메일과 같은 경우
        if (isEmailMatching(requestUser, currentUser)) {
            return;
        }
        // 수정하려고 하는 이메일이 중복되는지 검증
        validateDuplicatedUserEmail(requestUser);
    }

    private boolean isEmailMatching(User requestUser, User currentUser) {
        return requestUser.getEmail().equals(currentUser.getEmail());
    }
}
