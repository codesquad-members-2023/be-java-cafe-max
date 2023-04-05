package kr.codesqaud.cafe.user.repository;

import kr.codesqaud.cafe.user.domain.User;
import kr.codesqaud.cafe.user.dto.UserDto;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class UserMemoryRepository {

    private Map<String, User> users = new HashMap<>();

    public String add(User user) {
        if (containsId(user.getUserId())) {
            return null;
        }
        users.put(user.getUserId(), user);
        return user.getUserId();
    }

    public boolean containsId(String userId) {
        return users.containsKey(userId);
    }

    public UserDto getUser(String userId) {
        return users.get(userId).toUserDto();
    }

    public List<UserDto> getUserList() {
        return users.values().stream().map(user -> user.toUserDto()).collect(Collectors.toList());
    }

}
