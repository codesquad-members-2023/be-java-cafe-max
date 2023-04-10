package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class MemoryUserRepository implements UserRepository {
    private static final Map<Long, User> store = new ConcurrentHashMap<>(); // (회원번호, User)
    private static final AtomicLong customerId = new AtomicLong(0);

    @Override
    public User save(User user) {
        user.setCustomerId(customerId.incrementAndGet());
        store.put(user.getCustomerId(), user);
        return user;
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        // null 일 때 optinal을 감싸 반환하면 클라이언트에서 처리해줄 수 있음.
//        return Optional.ofNullable(store.get(customerId));
        return store.values().stream().filter(user -> user.getUserId().equals(userId)).findAny();
    }

    @Override
    public Optional<User> findByName(String name) {
        return store.values().stream()
                .filter(user -> user.getName().equals(name))
                .findAny();
    }

    @Override
    public List<User> findAll() {
        return store.values().stream().collect(Collectors.toUnmodifiableList());
    }

    @Override
    public void clearStore(){
        store.clear();
    }
}
