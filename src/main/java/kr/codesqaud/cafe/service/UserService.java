package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.user.User;
import kr.codesqaud.cafe.domain.user.repository.UserRepository;
import kr.codesqaud.cafe.dto.SignUpFormDto;
import kr.codesqaud.cafe.dto.UpdateFormDto;
import kr.codesqaud.cafe.exception.DeniedAccessException;
import kr.codesqaud.cafe.exception.LoginFailedException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean signUp(SignUpFormDto dto) {
        User user = new User(dto.getUserId(), dto.getPassword(), dto.getName(), dto.getEmail());
        userRepository.save(user);
        return true;
    }

    public User findById(String id) {

        return userRepository.findById(id);
    }


    public boolean update(User user, UpdateFormDto dto) {
        User current = user;
        if (current.checkPassword(dto.getPassword())) {
            userRepository.update(new User(user.getUserId(), dto.getNewPassword(), dto.getName(), dto.getEmail()));
            return true;
        }
        return false;

    }

    public boolean login(String id,String password){
        User loginUser = userRepository.findById(id);
        if(!loginUser.checkPassword(password)){
            throw new LoginFailedException("로그인 실패");
        }
        return true;
    }

    public boolean updateAccess(String id , HttpSession session){
        if(!id.equals(session.getAttribute("sessionID"))){
            throw new DeniedAccessException("돌아가라");
        }
        return true;
    }



    public List<User> getUserList() {
        return userRepository.findAll();
    }
}