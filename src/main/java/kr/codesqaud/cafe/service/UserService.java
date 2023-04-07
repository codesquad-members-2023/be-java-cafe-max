package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.controller.dto.UserJoinDto;
import kr.codesqaud.cafe.controller.dto.UserReadDto;
import kr.codesqaud.cafe.controller.dto.UserUpdateDto;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.common.exception.user.UserExceptionType;
import kr.codesqaud.cafe.common.exception.user.UserJoinFailedException;
import kr.codesqaud.cafe.common.exception.user.UserUpdateFailedException;
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
                .ifPresent(m -> {throw new UserJoinFailedException(UserExceptionType.DUPLICATED_EMAIL, userJoinDto);});
        userRepository.findByUserId(userJoinDto.getUserId())
                .ifPresent(m -> { throw new UserJoinFailedException(UserExceptionType.DUPLICATED_USER_ID, userJoinDto);});

        return userRepository.save(userJoinDto.toUser());
    }

    public UserReadDto find(final Long id) {
        final User user = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다."));

        return new UserReadDto(user);
    }

    public List<UserReadDto> findAll() {
        return userRepository.findAll().stream()
                .map(UserReadDto::new)
                .collect(Collectors.toList());
    }

    public void update(UserUpdateDto userUpdateDto) {
        final User user = userRepository.findById(userUpdateDto.getId()).orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다."));
        userRepository.findByUserId(userUpdateDto.getUserId())
                .ifPresent(m -> { throw new UserUpdateFailedException(UserExceptionType.DUPLICATED_USER_ID, userUpdateDto);});

        if (!user.isNotMatchedPassword(userUpdateDto.getPassword())) {
            throw new UserUpdateFailedException(UserExceptionType.NOT_MATCHED_BEFORE_PASSWORD, userUpdateDto);
        }

        userRepository.update(userUpdateDto.toUser());
    }
}
