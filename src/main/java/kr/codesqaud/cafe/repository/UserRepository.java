package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.dto.UserDto;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserRepository {

    private Map<Integer, User> userRepository;

    private static int sequence = 1;
    public UserRepository() {
        this.userRepository = new HashMap();
    }

    public void save(UserDto userDto) {
        userRepository.put(userDto.getId(),userDto.toUser());
    }

    public List<User> findAll() {
        List<User> users = userRepository.values().stream()
                .map(user -> new User(user.getNickName(), user.getEmail(), user.getPassword(),user.getId()))
                .collect(Collectors.toList());
        return Collections.unmodifiableList(users);
    }

    /**
     * userId를 통해 해당 id에 해당하는 userDto를 return한다.
     * @param userId
     * @return userDto
     */

    public UserDto findUser(int userId) {
        return userRepository.values().stream()
                .filter(user -> user.getId() == userId)
                .findFirst()
                .map(User::toDTO)
                .orElse(null);
    }


}
