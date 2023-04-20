package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class UserRepository {
    private final List<User> userList;
    private final Map<String, User> dataBase;

    public UserRepository() {
        this.userList = new ArrayList<>();
        this.dataBase = new ConcurrentHashMap<>();
    }

    public void save(User user) {
        userList.add(user);
        dataBase.put(user.getUserId(), user);
    }

    public List<User> getUserList() {
        return userList;
    }
    public User getUserByUserId(String userId) {
        return dataBase.get(userId);
    }

}
