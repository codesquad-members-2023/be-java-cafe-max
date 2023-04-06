package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.controller.dto.user.UserProfileForm;
import kr.codesqaud.cafe.controller.dto.user.UserUpdateForm;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void join(User user) {
        validateDuplicateUser(user);
        userRepository.save(user);
    }

    private void validateDuplicateUser(User user) {
        userRepository.findByUserId(user.getUserId())
                .ifPresent(u -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    public List<User> findUsers() {
        return userRepository.findAll();
    }

    public UserProfileForm findProfile(Long id) {
        User user;
        if (userRepository.findById(id).isPresent()) {
            user = userRepository.findById(id).get();
        } else {
            throw new NoSuchElementException();
        }

        return new UserProfileForm(user);
    }

    public UserUpdateForm findUpdate(Long id) {
        User user;
        if (userRepository.findById(id).isPresent()) {
            user = userRepository.findById(id).get();
        } else {
            throw new NoSuchElementException();
        }

        return new UserUpdateForm(user);
    }

    public void updateUser(Long id, User updateUser) {
        userRepository.update(id, updateUser);
    }
}
