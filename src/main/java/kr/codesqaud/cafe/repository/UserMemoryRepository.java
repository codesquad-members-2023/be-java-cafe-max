package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class UserMemoryRepository implements UserRepository {

    //디버깅하다가 nonstatic으로 바꾸긴 했는데 강의에서 왜 static 이었을까?? -> 싱글톤으로 사용하기 위해
    private static final Map<String, User> userList = new ConcurrentHashMap<>();

    //TODO: 왜 제 개발자모드에서는 GET, POST 가 안나오는 건가여
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
    public void clearUserList() {
        userList.clear();
    }
}
