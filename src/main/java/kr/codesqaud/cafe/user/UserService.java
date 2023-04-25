package kr.codesqaud.cafe.user;

import java.util.List;
import java.util.Optional;
import kr.codesqaud.cafe.exception.signUpException.InvalidUserIdException;
import org.springframework.stereotype.Service;

/**
 * 회원 관련 서비스 로직을 담당하는 객체
 */

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 회원 가입
     */
    public void join(User user) {
        validaUserIdUniqueness(user);
        userRepository.save(user);
    }

    private void validaUserIdUniqueness(User user) {
        findOne(user.getLoginId()).ifPresent(findUser -> {
                    throw new InvalidUserIdException();
                });
    }

    /**
     * 전체 회원 조회
     * @return 저장소 내 전체 회원
     */
    public List<User> findUsers() {
        return userRepository.findAll();
    }

    /**
     * ID로 특정 회원 조회
     * @param userId 회원 ID
     * @return 회원 정보
     */
    public Optional<User> findOne(String userId) {
        return userRepository.findById(userId);
    }

    /**
     * @return null: 로그인 실패
     */
    public User login(User loginUser) {
        return userRepository.findById(loginUser.getLoginId())
                .filter(u -> u.getPassword().equals(loginUser.getPassword()))
                .orElse(null);
    }
}
