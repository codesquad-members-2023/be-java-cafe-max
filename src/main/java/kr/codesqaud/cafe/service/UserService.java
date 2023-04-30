package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.controller.dto.login.LoggedInDTO;
import kr.codesqaud.cafe.controller.dto.user.JoinDTO;
import kr.codesqaud.cafe.controller.dto.user.ModifiedUserDTO;
import kr.codesqaud.cafe.controller.dto.user.ProfileDTO;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.exception.user.UserNotFoundException;
import kr.codesqaud.cafe.repository.UserRepository;
import kr.codesqaud.cafe.util.LoginSessionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public void modify(final long id, final ModifiedUserDTO modifiedUserDTO) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        user.update(modifiedUserDTO);
        userRepository.update(user);
        loginSessionManager.updateInfo(LoggedInDTO.from(user));
    }


    public boolean isPasswordRight(long id, ModifiedUserDTO modifiedUserDTO) {
        User originUser = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        return originUser.matchPassword(modifiedUserDTO.getOriginPassword());
    }

    public boolean isOwner(long id) {
        Long loggedInId = loginSessionManager.getLoginUser().getId();
        return loggedInId == id;
    }

    public ProfileDTO findOne(final long id) {
        Optional<User> wantedUser = userRepository.findById(id);
        return wantedUser.map(ProfileDTO::from).orElseThrow(UserNotFoundException::new);
    }

    public List<ProfileDTO> findUsers() {
        return userRepository.findAll().stream()
                .map(ProfileDTO::from)
                .collect(Collectors.toList());
    }
}