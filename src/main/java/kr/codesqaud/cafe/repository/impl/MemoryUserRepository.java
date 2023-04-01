package kr.codesqaud.cafe.repository.impl;

import kr.codesqaud.cafe.controller.dto.ProfileEditDTO;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.exception.UserNotFoundException;
import kr.codesqaud.cafe.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class MemoryUserRepository implements UserRepository {

    private Map<Integer, User> userRepository;

    private static int sequence = 1;

    public MemoryUserRepository() {
        this.userRepository = new HashMap();
    }

    @Override
    public void save(User user) {
        user.setId(user.getId() == -1 ? sequence++ : user.getId());
        userRepository.put(user.getId(), user);
    }

    @Override
    public List<User> findAll() {
        List<User> users = userRepository.values().stream()
                .collect(Collectors.toList());
        return Collections.unmodifiableList(users);
    }

    @Override
    public User findUserById(int userId) {
        return userRepository.values().stream()
                .filter(user -> user.getId() == userId)
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("해당 사용자가 없습니다.") );
    }

    public void updateUser(ProfileEditDTO profileEditDto, int id) {
        String oriPassword = userRepository.get(id).getPassword();
        if (oriPassword.equals(profileEditDto.getOriPassword())) {
            save(profileEditDto.toUser(id));
        }
    }
}
