package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.dto.UserRequest;
import kr.codesqaud.cafe.dto.UserResponse;
import kr.codesqaud.cafe.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void register(UserRequest user) {
        userRepository.save(user);
    }

    public void showUserList(Model model) {
        List<UserResponse> users = userRepository.findAll().stream()
                .map(User::toResponse)
                .collect(Collectors.toList());
        model.addAttribute("users", users);
    }
}
