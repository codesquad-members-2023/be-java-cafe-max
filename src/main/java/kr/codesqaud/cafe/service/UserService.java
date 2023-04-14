package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.common.exception.user.UserJoinException;
import kr.codesqaud.cafe.common.exception.user.UserLoginException;
import kr.codesqaud.cafe.controller.dto.user.LoginUserDto;
import kr.codesqaud.cafe.controller.dto.user.UserJoinDto;
import kr.codesqaud.cafe.controller.dto.user.UserLoginDto;
import kr.codesqaud.cafe.controller.dto.user.UserReadDto;
import kr.codesqaud.cafe.controller.dto.user.UserUpdateDto;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.common.exception.user.UserExceptionType;
import kr.codesqaud.cafe.common.exception.user.UserUpdateException;
import kr.codesqaud.cafe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long join(final UserJoinDto userJoinDto) {
        userRepository.findByEmail(userJoinDto.getUserId())
                .ifPresent(m -> {throw new UserJoinException(UserExceptionType.DUPLICATED_EMAIL, userJoinDto);});
        userRepository.findByUserId(userJoinDto.getUserId())
                .ifPresent(m -> { throw new UserJoinException(UserExceptionType.DUPLICATED_USER_ID, userJoinDto);});

        return userRepository.save(userJoinDto.toUser());
    }

    public UserReadDto find(final Long id) {
        final User user = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException(UserExceptionType.NOT_FOUND_USER.getMessage()));

        return new UserReadDto(user);
    }

    public List<UserReadDto> findAll() {
        return userRepository.findAll().stream()
                .map(UserReadDto::new)
                .collect(Collectors.toList());
    }

    public void update(UserUpdateDto userUpdateDto) {
        final User user = userRepository.findById(userUpdateDto.getId())
                .orElseThrow(() -> new IllegalArgumentException(UserExceptionType.NOT_FOUND_USER.getMessage()));

        if (user.isChangedUserId(userUpdateDto.getUserId())) {
            userRepository.findByUserId(userUpdateDto.getUserId())
                    .ifPresent(m -> { throw new UserUpdateException(UserExceptionType.DUPLICATED_USER_ID, userUpdateDto);});
        }
        if (user.isNotMatchedPassword(userUpdateDto.getPassword())) {
            throw new UserUpdateException(UserExceptionType.NOT_MATCHED_PASSWORD, userUpdateDto);
        }

        userRepository.update(userUpdateDto.toUser());
    }

    public LoginUserDto login(UserLoginDto userLoginDto) {
        final User user = userRepository.findByUserId(userLoginDto.getUserId())
                .orElseThrow(() -> new UserLoginException(UserExceptionType.INVALID_USER_ID));

        if (user.isNotMatchedPassword(userLoginDto.getPassword())) {
            throw new UserLoginException(UserExceptionType.NOT_MATCHED_PASSWORD);
        }

        return new LoginUserDto(user);
    }
}
