package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class PureUserRepositoryImpl implements UserRepository {
    private final Map<Long, User> users = new ConcurrentHashMap<>();
    private Long id = 0L;

    @Override
    public Long save(User user) {
        setId(user);
        users.put(id, user);

        return id;
    }

    private void setId(User user) {
        ++id;
        user.setId(id);
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(users.getOrDefault(id, null));
    }
}
