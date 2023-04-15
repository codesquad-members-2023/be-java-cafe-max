package codesquad.cafe.domain.user.service;

import codesquad.cafe.domain.user.domain.User;
import codesquad.cafe.domain.user.dto.UserRequestDto;
import codesquad.cafe.domain.user.dto.UserResponseDto;
import codesquad.cafe.domain.user.dto.UserUpdateRequestDto;
import codesquad.cafe.domain.user.repository.UserRepository;
import codesquad.cafe.global.exception.CustomException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static codesquad.cafe.global.exception.ErrorCode.*;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User join(UserRequestDto userRequestDto) {
        User user = userRequestDto.toEntity();
        validateDuplicateUser(user);
        return userRepository.save(user);
    }

    private void validateDuplicateUser(final User user) {
        userRepository.findById(user.getId())
                .ifPresent(u -> {
                    throw new CustomException(ALREADY_EXIST_USER);
                });
    }

    public List<UserResponseDto> showUsers() {
        List<User> users = userRepository.findAll();
        List<UserResponseDto> userResponseDtos = new ArrayList<>();
        for (User user : users) {
            userResponseDtos.add(new UserResponseDto(user.getId(), user.getName(), user.getEmail()));
        }

        return userResponseDtos;
    }

    public UserResponseDto findUser(final String id) {
        return userRepository.findById(id)
                .map(user -> new UserResponseDto(user.getId(), user.getName(), user.getEmail()))
                .orElseThrow(() -> new CustomException(NOT_FOUND_USER));
    }

    public void updateUser(final String id, final UserUpdateRequestDto userUpdateRequestDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException(NOT_FOUND_USER));
        validatePassword(user, userUpdateRequestDto);
        userRepository.update(user.update(userUpdateRequestDto));
    }

    private void validatePassword(final User user, final UserUpdateRequestDto userUpdateRequestDto) {
        if(!user.getPassword().equals(userUpdateRequestDto.getPassword())) {
            throw new CustomException(WRONG_INPUT_PASSWORD);
        }
    }
}
