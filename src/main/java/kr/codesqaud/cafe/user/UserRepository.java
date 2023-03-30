package kr.codesqaud.cafe.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRepository {
    private Map<String, User> repository = new HashMap<>();

    public void save(User user) {
        repository.put(user.getUserId(), user);
    }

    public List<User> getRepository() {
        return new ArrayList<>(repository.values());
    }

    public User fineByUserId(String userId) {
        return repository.get(userId);
    }
}
