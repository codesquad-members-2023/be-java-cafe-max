package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class BasicUserRepository implements UserRepository {
    private final List<User> userList;
    private final Map<String, User> dataBase;

    public BasicUserRepository() {
        this.userList = new ArrayList<>();
        this.dataBase = new ConcurrentHashMap<>();
    }
    @Override
    public void save(User user) {
        userList.add(user);
        dataBase.put(user.getUserId(), user);
    }
    @Override
    public Optional<User> getUserById(Long Id) {
        return Optional.ofNullable(dataBase.get(Id));
    }
    @Override
    public List<User> getUserList() {
        return userList;
    }
    public void clearStore(){}

    @Override
    public void update(User user) {}

    @Override
    public Optional<User> getUserByUserId(String userId){
        return Optional.ofNullable(dataBase.get(userId));
    }
}
