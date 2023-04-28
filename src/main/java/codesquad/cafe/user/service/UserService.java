package codesquad.cafe.user.service;

import codesquad.cafe.user.domain.User;
import codesquad.cafe.user.dto.UserLoginRequestDto;
import codesquad.cafe.user.dto.UserRequestDto;
import codesquad.cafe.user.dto.UserResponseDto;
import codesquad.cafe.user.dto.UserUpdateRequestDto;
import codesquad.cafe.user.repository.UserRepository;
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

    public void updateUser(final String id, final UserUpdateRequestDto userUpdateRequestDto, final User sessionUser) {
        validateUpdateUser(sessionUser, id);
        User user = isExistUser(id);
        validatePassword(user, userUpdateRequestDto.getPassword());
        userRepository.update(user.update(userUpdateRequestDto.getName(), userUpdateRequestDto.getEmail()));
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

    public void validateUpdateUser(final User user, final String userId) {
        if (!user.getId().equals(userId)) {
            throw new CustomException(UNAUTHORIZED_USER);
        }
    }
}
