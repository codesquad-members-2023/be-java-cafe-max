package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;
import org.springframework.stereotype.Repository;

import java.util.*;
@Repository
public class MemoryUserRepository implements UserRepository {
    // 원래는 아래의 store, sequence는 동시성 문제때문에 concurrentHashMap 등을 쓴다.
    private static Map<Long, User> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public User save(User user) {
        user.setCustomerId(customerId.incrementAndGet()); //
        store.put(user.getCustomerId(), user);
        return user;
    }

    @Override
    public Optional<User> findByNumber(Long number) {
        // null 일 때 optinal을 감싸 반환하면 클라이언트에서 처리해줄 수 있음.
        return Optional.ofNullable(store.get(number));
    }

    @Override
    public Optional<User> findByName(String name) {
        return store.values().stream()
                .filter(user -> user.getName().equals(name))
                .findAny();
    }

    @Override
    public List<User> findAll() { // 모든 user가 반환됨.
        return new ArrayList<>(store.values());
    }

    public void clearStore(){
        store.clear();
    }
}
