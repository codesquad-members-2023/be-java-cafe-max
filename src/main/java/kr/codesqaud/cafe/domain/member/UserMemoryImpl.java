package kr.codesqaud.cafe.domain.member;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserMemoryImpl implements UserRepository {

    private final List<User> users = new ArrayList<>();

    @Override
    public void save(User user) {
        users.add(user);
    }

    @Override
    public List<User> findAll() {

        return users;
    }
}
