package kr.codesqaud.cafe.user.repository;

import kr.codesqaud.cafe.user.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MemoryUserRepository implements UserRepository {

    private static List<User> store = new ArrayList<>();
    private static long index = 0L;
        @Override
        public User save(User user) {
            user.setIndex(++index);
            store.add(user);
        return user;
    }

    @Override
    public Optional<User> findById(String id) {
        return store.stream()
                .filter(user -> user.getId().equals(id))
                .findAny();
    }

    @Override
    public Optional<User> findByName(String name) {
        return store.stream()
                .filter(user -> user.getName().equals(name))
                .findAny();
    }

    @Override
    public List<User> findAll() {
        return store;
    }

    @Override
    public void clearStore(){
            store.clear();
    }
}
