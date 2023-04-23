package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.controller.dto.JoinDTO;
import kr.codesqaud.cafe.controller.dto.LoginDTO;
import kr.codesqaud.cafe.controller.dto.ModifiedUserDTO;
import kr.codesqaud.cafe.controller.dto.ProfileDTO;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static kr.codesqaud.cafe.controller.LoginController.LOGIN_USER;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //수정 : User.toUser -> joinDTO.toEntity
    public void signUp(final JoinDTO joinDTO) {
        User user = joinDTO.toEntity();
        userRepository.join(user);
    }

    public boolean checkDuplicate(String userId) {
        return userRepository.findByUserId(userId).isPresent();
    }

    //todo : null일 때 예외처리 하기
    public void modify(final long id, final ModifiedUserDTO modifiedUserDTO) {
        User originUser = userRepository.findById(id).orElse(null);
        assert originUser != null;
        originUser.update(modifiedUserDTO);
        userRepository.update(originUser);
    }

    //todo : null일 때 예외처리 하기
    public boolean isPasswordRight(long id, ModifiedUserDTO modifiedUserDTO) {
        User originUser = userRepository.findById(id).orElse(null);
        assert originUser != null;
        return originUser.matchPassword(modifiedUserDTO.getOriginPassword());
    }

    public boolean isOwner(long id, HttpSession session) {
        long loggedInId = obtainId(session);
        return loggedInId == id;
    }

    private long obtainId(HttpSession session) {
        LoginDTO loggedInUser = (LoginDTO) session.getAttribute(LOGIN_USER);
        assert loggedInUser != null;
        return loggedInUser.getId();
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



/*
        if (wantedUser.isPresent()) {
        //Optional 객체를 꺼내 가져오려면 get() 메서드 사용
            return UserReadDTO.toUserReadDTO(wantedUser.get());
        }
        return null;
        을 한줄로 변환하면
        => return wantedUser.map(UserReadDTO::toUserReadDTO).orElse(null);

 */
