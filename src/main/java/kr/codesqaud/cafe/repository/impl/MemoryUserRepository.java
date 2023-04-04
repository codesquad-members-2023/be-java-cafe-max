package kr.codesqaud.cafe.repository.impl;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.exception.InvalidPasswordException;
import kr.codesqaud.cafe.exception.UserNotFoundException;
import kr.codesqaud.cafe.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class MemoryUserRepository implements UserRepository {

    private final Map<String, User> userRepository;

    public MemoryUserRepository() {
        this.userRepository = new LinkedHashMap<>();
    }

    @Override
    public void save(User user) {
        userRepository.put(user.getId(), user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.values().stream()
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public User findUserById(String userId) {
        return userRepository.values().stream()
                .filter(user -> user.getId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("해당 사용자가 없습니다.") );
    }

    @Override
    public void updateUser(User user,String oriPassword) {
        String userRepositoryOriPassword = userRepository.get(user.getId()).getPassword();
        if (!userRepositoryOriPassword.equals(oriPassword)) {
            throw new InvalidPasswordException("비밀번호가 일치하지 않습니다.");
        }
        save(user);
    }
}
