package kr.codesqaud.cafe.user.repository;

import kr.codesqaud.cafe.user.domain.User;
import kr.codesqaud.cafe.user.dto.UserResponse;
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

    public UserResponse getUser(String userId) {
        return users.get(userId).toUserResponse();
    }

    public List<UserResponse> getUserList() {
        return users.values().stream().map(user -> user.toUserResponse()).collect(Collectors.toList());
    }

}
