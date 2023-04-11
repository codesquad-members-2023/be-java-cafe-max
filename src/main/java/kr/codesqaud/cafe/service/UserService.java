package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.Repository.MemoryUserRepository;
import kr.codesqaud.cafe.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final MemoryUserRepository memberUserRepository;

    public UserService(MemoryUserRepository memberUserRepository) {
        this.memberUserRepository = memberUserRepository;
    }

    public void join(final User user) {
        checkDuplicateId(user.getEmail());
        memberUserRepository.save(user);
    }

    public User findUserById(final String id) {

        final User user = memberUserRepository.findById(id).orElseThrow(
                 () ->  new IllegalArgumentException("해당 유저가 없습니다.")
         );
        return user;
    }

    private void checkDuplicateId(final String email) {
        memberUserRepository.findByEmail(email)
                .ifPresent(m -> {
                    throw new IllegalArgumentException("이미 존재하는 회원입니다.");
                });
    }

    public List<User> showAllUser() {
        return memberUserRepository.findAllUser();
    }
}
