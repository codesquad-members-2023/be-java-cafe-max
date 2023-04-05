package kr.codesqaud.cafe.web.service;

import java.util.List;
import java.util.stream.Collectors;
import kr.codesqaud.cafe.domain.user.User;
import kr.codesqaud.cafe.domain.user.UserRepository;
import kr.codesqaud.cafe.exception.user.UserDuplicatedException;
import kr.codesqaud.cafe.exception.user.UserDuplicatedExceptionType;
import kr.codesqaud.cafe.exception.user.UserNotFoundException;
import kr.codesqaud.cafe.exception.user.UserNotFoundExceptionType;
import kr.codesqaud.cafe.web.dto.UserResponseDto;
import kr.codesqaud.cafe.web.dto.UserSavedRequestDto;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserResponseDto> findAll() {
        return userRepository.findAll().stream()
            .map(UserResponseDto::new)
            .collect(Collectors.toUnmodifiableList());
    }

    public UserResponseDto save(UserSavedRequestDto requestDto) {
        validateDuplicatedUserId(requestDto);
        validateDuplicateUserEmail(requestDto);
        User saveUser = userRepository.save(requestDto.toEntity());
        return new UserResponseDto(saveUser);
    }

    private void validateDuplicatedUserId(UserSavedRequestDto requestDto) {
        if (userRepository.findByUserId(requestDto.getUserId()).isPresent()) {
            throw new UserDuplicatedException(UserDuplicatedExceptionType.ALREADY_EXIST_USERID);
        }
    }

    private void validateDuplicateUserEmail(UserSavedRequestDto requestDto) {
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new UserDuplicatedException(UserDuplicatedExceptionType.ALREADY_EXIST_EMAIL);
        }
    }

    public UserResponseDto findById(String userId) {
        User user = userRepository.findByUserId(userId).orElseThrow(() -> {
            throw new UserNotFoundException(UserNotFoundExceptionType.NOT_FOUND_USER);
        });
        return new UserResponseDto(user);
    }
}
