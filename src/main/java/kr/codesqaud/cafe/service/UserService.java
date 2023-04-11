package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.Repository.user.MemoryUserRepository;
import kr.codesqaud.cafe.Repository.user.UserRepository;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.dto.user.UserRequestDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void join(final UserRequestDto userRequestDto) {
        checkDuplicateId(userRequestDto.getEmail());
        userRepository.save(userRequestDto.toEntity());
    }

    public User findUserById(final String id) {

        final User user = userRepository.findById(id).orElseThrow(
                 () ->  new IllegalArgumentException("해당 유저가 없습니다.")
         );
        return user;
    }

    private void checkDuplicateId(final String email) {
        userRepository.findByEmail(email)
                .ifPresent(m -> {
                    throw new IllegalArgumentException("이미 존재하는 회원입니다.");
                });
    }

    public List<User> showAllUser() {
        return userRepository.findAllUser();
    }
}
