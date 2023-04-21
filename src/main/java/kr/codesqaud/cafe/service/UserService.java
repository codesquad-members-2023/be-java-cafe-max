package kr.codesqaud.cafe.service;

import com.sun.jdi.request.DuplicateRequestException;
import kr.codesqaud.cafe.domain.user.User;
import kr.codesqaud.cafe.domain.user.repository.UserRepository;
import kr.codesqaud.cafe.dto.LoginSessionDto;
import kr.codesqaud.cafe.dto.SignUpFormDto;
import kr.codesqaud.cafe.dto.UpdateFormDto;
import kr.codesqaud.cafe.exception.DeniedAccessException;
import kr.codesqaud.cafe.exception.DuplicatedIdException;
import kr.codesqaud.cafe.exception.LoginFailedException;
import kr.codesqaud.cafe.exception.NotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void signUp(SignUpFormDto dto) {
        User user = new User(dto.getUserId(), dto.getPassword(), dto.getName(), dto.getEmail());
        userRepository.save(user);
    }

    public User findById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("유저를 찾을수 없음"));
    }


    public void update(User user, UpdateFormDto dto) {
        User current = user;
        if (userRepository.exist(dto.getName())) {
            throw new DuplicatedIdException("중복된 닉네임으로는 변경 할 수 없습니다.");
        }
        if (current.checkPassword(dto.getPassword())) {
            userRepository.update(new User(user.getUserId(), dto.getNewPassword(), dto.getName(), dto.getEmail()));
            return;
        }
        throw new NotFoundException("비밀번호입력 오류");

    }

    public void login(User loginUser, String password) {
        if (!loginUser.checkPassword(password)) {
            throw new LoginFailedException("로그인 실패");
        }
    }

    public void updateAccess(String id, LoginSessionDto sessionId) {
        if (sessionId == null || !id.equals(sessionId.getId())) {
            throw new DeniedAccessException("수정 권한 없습니다.");
        }
    }

    public void duplicatedId(SignUpFormDto dto) {
        userRepository.findById(dto.getUserId()).filter(u -> {
            throw new DuplicatedIdException("중복된 아이디 입니다.");
        });
        if (userRepository.exist(dto.getName())) {
            throw new DuplicatedIdException("중복된 닉네임");
        }

    }


    public List<User> getUserList() {
        return userRepository.findAll();
    }
}
