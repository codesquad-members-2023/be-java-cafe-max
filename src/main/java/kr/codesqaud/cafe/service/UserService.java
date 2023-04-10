package kr.codesqaud.cafe.service;

import java.util.List;
import java.util.Optional;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.dto.UserDTO;
import kr.codesqaud.cafe.exception.signUpException.InvalidUserIdException;
import kr.codesqaud.cafe.repository.UserRepository;
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
    public void join(UserDTO userDTO) {
        validaUserIdUniqueness(userDTO);
        User user = userDTO.toEntity();
        userRepository.save(user);
    }

    private void validaUserIdUniqueness(UserDTO userDTO) {
        findOne(userDTO.getUserId()).ifPresent(user -> {
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
}
