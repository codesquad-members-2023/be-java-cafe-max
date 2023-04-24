package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.controller.dto.login.LoggedInDTO;
import kr.codesqaud.cafe.controller.dto.user.JoinDTO;
import kr.codesqaud.cafe.controller.dto.user.ModifiedUserDTO;
import kr.codesqaud.cafe.controller.dto.user.ProfileDTO;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.UserRepository;
import kr.codesqaud.cafe.util.LoginSessionManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final LoginSessionManager loginSessionManager;

    public UserService(UserRepository userRepository, LoginSessionManager loginSessionManager) {
        this.userRepository = userRepository;
        this.loginSessionManager = loginSessionManager;
    }

    public void signUp(final JoinDTO joinDTO) {
        User user = joinDTO.toEntity();
        userRepository.join(user);
    }

    public boolean checkDuplicate(String userId) {
        return userRepository.findByUserId(userId).isPresent();
    }

    //todo : null 예외처리 하기
    public void modify(final long id, final ModifiedUserDTO modifiedUserDTO) {
        User user = userRepository.findById(id).orElse(null);
        assert user != null;
        user.update(modifiedUserDTO);
        userRepository.update(user);
        loginSessionManager.updateInfo(LoggedInDTO.from(user));
    }

    //todo : null 예외처리 하기
    public boolean isPasswordRight(long id, ModifiedUserDTO modifiedUserDTO) {
        User originUser = userRepository.findById(id).orElse(null);
        assert originUser != null;
        return originUser.matchPassword(modifiedUserDTO.getOriginPassword());
    }

    public boolean isOwner(long id) {
        Long loggedInId = loginSessionManager.getLoginUser().getId();
        return loggedInId == id;
    }

    public ProfileDTO findOne(final long id) {
        Optional<User> wantedUser = userRepository.findById(id);
        return wantedUser.map(ProfileDTO::from).orElse(null);
    }

    public List<ProfileDTO> findUsers() {
        return userRepository.findAll().stream()
                .map(ProfileDTO::from)
                .collect(Collectors.toList());
    }
}