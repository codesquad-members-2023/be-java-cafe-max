package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.controller.dto.LoginDTO;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {

    private final UserRepository userRepository;

    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean checkIdAndPw(LoginDTO loginDTO) {
        Optional<User> loginUser = userRepository.findByUserId(loginDTO.getUserId());
        return loginUser.isPresent() && loginUser.get().getPassword().equals(loginDTO.getPassword());
    }

    public LoginDTO findId(final String userId) {
        Optional<User> wantedId = userRepository.findByUserId(userId);
        return wantedId.map(LoginDTO::from).orElse(null);
    }


//    public boolean authenticate(HttpServletRequest request) {
//        //session 있을 때만 생성 없을땐 생성x
//        HttpSession session = request.getSession(false);
//        if(session != null) {
//            return session.getAttribute("id") != null;
//        }
//        return false;
//    }

}
