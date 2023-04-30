package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.controller.dto.UserDto;
import kr.codesqaud.cafe.controller.dto.request.userRequest.JoinRequest;
import kr.codesqaud.cafe.controller.dto.request.userRequest.ProfileEditRequest;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
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
        userRepository.findByUserId(user.getUserId())
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

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User findByUserId(String userId) {
        return userRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void editUserProfile(final String userId, final ProfileEditRequest request) {
        User savedUser = userRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("User not found"));
        boolean isPasswordTrue = savedUser.getPassword().equals(request.getOriginalPassword());
        if (isPasswordTrue) {
            savedUser.editProfile(request.getNewPassword(), request.getNewUserName(), request.getNewUserEmail());
            userRepository.update(savedUser);
        } else {
            //todo : 오류메세지 출력
        }
    }

    public User login(String userId, String password) {
        return userRepository.findByUserId(userId)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }
}
