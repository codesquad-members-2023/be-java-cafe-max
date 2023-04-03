package codesquad.cafe.service;

import codesquad.cafe.domain.User;
import codesquad.cafe.dto.UserResponseDto;
import codesquad.cafe.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User join(User user) {
        validateDuplicateUser(user);
        return userRepository.save(user);
    }

    private void validateDuplicateUser(final User user) {
        userRepository.findById(user.getId())
                .ifPresent(u -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
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
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 회원입니다."));
    }
}
