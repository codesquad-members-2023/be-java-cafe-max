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
    public Optional<User> getUserByUserId(Long userId) {
        return Optional.ofNullable(dataBase.get(userId)); // 아 자동으로 포장해준건 좋은데;;; (그래도 fix 해야지)
    }  // 원래 User인데 인터페이스 때문에 굳이 Optional<User>로..(;;;) -> fix 할지도
    @Override
    public List<User> getUserList() {
        return userList;
    }
    public void clearStore(){} // 아직 필요한지도 모르는데 인터페이스 때문에 형식적으로 맞춰서.. (!)
}
