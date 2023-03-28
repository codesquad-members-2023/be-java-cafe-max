package kr.codesqaud.cafe.user;

import org.springframework.stereotype.Repository;

import java.util.*;

//  메모리 회원 저장소 구현체
@Repository
public class MemoryUserRepository implements UserRepository {

    private List<User> users;

    public MemoryUserRepository(List<User> users) {
        this.users = users;
    }

    @Override
    public void save(User user) {
        users.add(user);
    }

    @Override
    public List<User> findAll() {
        return users;
    }
}