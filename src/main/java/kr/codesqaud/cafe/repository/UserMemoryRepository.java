package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class UserMemoryRepository implements UserRepository {

    private static final Map<String, User> userList = new ConcurrentHashMap<>();

    @Override
    public User save(User user) {
        userList.put(user.getUserId(), user);
        return user;
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(userList.values());
    }

    @Override
    public User findByUserId(String userId) {
        return userList.get(userId);
    }

    @Override
    public void updateUserPassword(User user, String password) {
        user.setPassword(password);
    }

    @Override
    public void clearUserList() {
        userList.clear();
    }


}
