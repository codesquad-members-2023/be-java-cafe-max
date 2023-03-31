package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.dto.UserProfileResponse;
import kr.codesqaud.cafe.dto.UserRegisterRequest;
import kr.codesqaud.cafe.dto.UserListResponse;
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

    public void register(UserRegisterRequest user) {
        userRepository.save(user);
    }

    public void showUserList(Model model) {
        List<UserListResponse> users = userRepository.findAll().stream()
                .map(User::toListResponse)
                .collect(Collectors.toList());
        model.addAttribute("users", users);
    }

    public void showProfile(long id, Model model) {
        UserProfileResponse profile = userRepository.findById(id)
                        .orElseThrow().toProfileResponse();
        model.addAttribute("name", profile.getName());
        model.addAttribute("email", profile.getEmail());
    }
}
