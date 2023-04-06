package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.controller.dto.UserDto;
import kr.codesqaud.cafe.controller.dto.request.JoinRequest;
import kr.codesqaud.cafe.repository.UserRepository;
import kr.codesqaud.cafe.domain.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long join(JoinRequest joinRequest) {
        User user = User.from(joinRequest);
        validateDuplicateMember(user);
        userRepository.save(user);
        return user.getId();
    }


    private void validateDuplicateMember(User user) {
        userRepository.findByName(user.getUserName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }
    public List<UserDto> getUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserDto::from)
                .collect(Collectors.toUnmodifiableList());
    }

    public List<User> findUsers() {
        return new ArrayList<>(userRepository.findAll());
    }

    public User findByUserId(String userId) {
        return userRepository.findByName(userId).get();
    }
}
