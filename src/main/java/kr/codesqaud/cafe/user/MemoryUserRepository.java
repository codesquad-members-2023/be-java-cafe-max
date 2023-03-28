package kr.codesqaud.cafe.user;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

//  메모리 회원 저장소 구현체
@Repository
public class MemoryUserRepository implements UserRepository {

    private static Map<String, User> users = new HashMap<>();

    @Override
    public void save(User user) {
        users.put(user.getUserId(), user);
    }

    @Override
    public User findById(String id) {
        return users.get(id);
    }
}
