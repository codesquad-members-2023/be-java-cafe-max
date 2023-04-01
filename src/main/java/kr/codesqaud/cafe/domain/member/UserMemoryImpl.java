package kr.codesqaud.cafe.domain.member;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class UserMemoryImpl implements UserRepository {

    private final Map<Integer, User> users = new HashMap<>();

    @Override
    public void save(User user, int index) {
        users.put(index++, user);
    }

    @Override
    public Map<Integer, User> findAll() {
        return users;
    }
}
