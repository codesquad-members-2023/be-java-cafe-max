package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PureUserRepositoryImpl implements UserRepository {
    private final Map<Long, User> users = new ConcurrentHashMap<>();
    private final AtomicLong atomicId = new AtomicLong();

    @Override
    public Long save(User user) {
        user.setId(atomicId.incrementAndGet());
        users.put(user.getId(), user);

        return user.getId();
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        return users.values().stream()
                .filter(user -> user.getUserId().equals(userId))
                .findAny();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return users.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findAny();
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public List<User> findAll() {
        final List<User> findUsers = new ArrayList<>(this.users.values());

        findUsers.sort((u1, u2) -> (int) (u1.getId() - u2.getId()));

        return findUsers;
    }
}
