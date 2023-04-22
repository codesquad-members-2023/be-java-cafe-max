package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.common.exception.CommonException;
import kr.codesqaud.cafe.common.exception.CommonExceptionType;
import kr.codesqaud.cafe.common.exception.user.UserExceptionType;
import kr.codesqaud.cafe.common.exception.user.UserJoinException;
import kr.codesqaud.cafe.common.exception.user.UserLoginException;
import kr.codesqaud.cafe.common.exception.user.UserUpdateException;
import kr.codesqaud.cafe.controller.dto.user.LoginUserSession;
import kr.codesqaud.cafe.controller.dto.user.UserJoinDto;
import kr.codesqaud.cafe.controller.dto.user.UserLoginDto;
import kr.codesqaud.cafe.controller.dto.user.UserReadDto;
import kr.codesqaud.cafe.controller.dto.user.UserUpdateDto;
import kr.codesqaud.cafe.domain.User;
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
        if (userRepository.existsUsername(userJoinDto.getUsername())) {
            throw new UserJoinException(UserExceptionType.DUPLICATED_USER_ID, userJoinDto);
        }
        if (userRepository.existsNickname(userJoinDto.getNickname())) {
            throw new UserJoinException(UserExceptionType.DUPLICATED_NICKNAME, userJoinDto);
        }

        return userRepository.save(userJoinDto.toUser());
    }

    public UserReadDto find(final Long id) {
        final User user = userRepository.findById(id).orElseThrow(
                () -> new CommonException(CommonExceptionType.NOT_FOUND_RESOURCE));

        return new UserReadDto(user);
    }

    public List<UserReadDto> findAll() {
        return userRepository.findAll().stream()
                .map(UserReadDto::new)
                .collect(Collectors.toList());
    }

    public void update(UserUpdateDto userUpdateDto) {
        final User user = userRepository.findById(userUpdateDto.getId())
                .orElseThrow(() -> new CommonException(CommonExceptionType.NOT_FOUND_RESOURCE));

        if (user.isDifferentNickname(userUpdateDto.getNickname()) && userRepository.existsNickname(userUpdateDto.getNickname())) {
            throw new UserUpdateException(UserExceptionType.DUPLICATED_NICKNAME, userUpdateDto);
        }
        if (user.isDifferentPassword(userUpdateDto.getPassword())) {
            throw new UserUpdateException(UserExceptionType.NOT_MATCHED_PASSWORD, userUpdateDto);
        }

        userRepository.update(userUpdateDto.toUser());
    }

    public LoginUserSession login(UserLoginDto userLoginDto) {
        final User user = userRepository.findByUsername(userLoginDto.getUsername())
                .orElseThrow(() -> new UserLoginException(UserExceptionType.INVALID_USER_ID, userLoginDto));

        if (user.isDifferentPassword(userLoginDto.getPassword())) {
            throw new UserLoginException(UserExceptionType.NOT_MATCHED_PASSWORD, userLoginDto);
        }

        return new LoginUserSession(user);
    }
}
