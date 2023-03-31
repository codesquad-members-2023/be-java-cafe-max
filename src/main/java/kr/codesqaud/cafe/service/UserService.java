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
import java.util.stream.IntStream;

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
        List<User> users = userRepository.findAll();
        List<UserListResponse> userResponse = IntStream.range(0, users.size())
                .mapToObj(index -> users.get(index).toListResponse(index + 1))
                .collect(Collectors.toList());
        model.addAttribute("users", userResponse);
    }

    public void showProfile(String id, Model model) {
        UserProfileResponse profile = userRepository.findById(id)
                        .orElseThrow().toProfileResponse();
        model.addAttribute("name", profile.getName());
        model.addAttribute("email", profile.getEmail());
    }
}
