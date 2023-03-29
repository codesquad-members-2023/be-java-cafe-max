package kr.codesqaud.cafe.web.service;

import java.util.List;
import java.util.stream.Collectors;
import kr.codesqaud.cafe.domain.user.User;
import kr.codesqaud.cafe.domain.user.UserRepository;
import kr.codesqaud.cafe.exception.user.UserException;
import kr.codesqaud.cafe.exception.user.UserExceptionType;
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
            .collect(Collectors.toList());
    }

    public UserResponseDto save(UserSavedRequestDto requestDto) {
        validateDuplicatedUserId(requestDto);
        validateDuplicateUserEmail(requestDto);
        User saveUser = userRepository.save(requestDto.toEntity());
        return new UserResponseDto(saveUser);
    }

    private void validateDuplicatedUserId(UserSavedRequestDto requestDto) {
        if (userRepository.findByUserId(requestDto.getUserId()).isPresent()) {
            throw new UserException(UserExceptionType.ALREADY_EXIST_USERID);
        }
    }

    private void validateDuplicateUserEmail(UserSavedRequestDto requestDto) {
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new UserException(UserExceptionType.ALREADY_EXIST_EMAIL);
        }
    }
}
