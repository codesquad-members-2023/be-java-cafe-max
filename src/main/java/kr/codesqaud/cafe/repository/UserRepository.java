package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.controller.dto.ProfileEditDTO;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.exception.UserNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class UserRepository {

    private Map<Integer, User> userRepository;

    private static int sequence = 1;

    public UserRepository() {
        this.userRepository = new HashMap();
    }

    public void save(User user) {
        user.setId(user.getId() == -1 ? sequence++ : user.getId());
        userRepository.put(user.getId(), user);
    }

    public List<User> findAll() {
        List<User> users = userRepository.values().stream()
                .collect(Collectors.toList());
        return Collections.unmodifiableList(users);
    }

    public User findUser(int userId) {
        return userRepository.values().stream()
                .filter(user -> user.getId() == userId)
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("해당 사용자가 없습니다.") );
    }

    public void findOne(ProfileEditDTO profileEditDto,int id) {
        String tempPassword = userRepository.get(id).getPassword();
        if (tempPassword.equals(profileEditDto.getOriPassword())) {
            save(profileEditDto.toUser(id));
        }
    }
}
