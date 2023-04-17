package codesquad.cafe.domain.user.service;

import codesquad.cafe.domain.user.domain.User;
import codesquad.cafe.domain.user.dto.UserLoginRequestDto;
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
        User user = isExistUser(id);
        return new UserResponseDto(user.getId(), user.getName(), user.getEmail());
    }

    public void updateUser(final String id, final UserUpdateRequestDto userUpdateRequestDto) {
        User user = isExistUser(id);
        validatePassword(user, userUpdateRequestDto.getPassword());
        userRepository.update(user.update(userUpdateRequestDto));
    }

    private User isExistUser(final String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new CustomException(NOT_FOUND_USER));
    }

    private void validatePassword(final User user, final String password) {
        if(!user.getPassword().equals(password)) {
            throw new CustomException(WRONG_INPUT_PASSWORD);
        }
    }

    public User login(final UserLoginRequestDto userLoginRequestDto) {
        User user = isExistUser(userLoginRequestDto.getUserId());
        validatePassword(user, userLoginRequestDto.getPassword());
        return user;
    }
}
