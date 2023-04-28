package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class MemoryUserRepository implements UserRepository {

    private static Long id = 1L;
    private final Map<Long, User> repository = new ConcurrentHashMap<>();

    @Override
    public synchronized void save(User input) {
        User user = User.builder()
                .id(id)
                .userId(input.getUserId())
                .password(input.getPassword())
                .userName(input.getUserName())
                .email(input.getEmail())
                .build();

        repository.put(id, user);
        id += 1;
    }

    @Override
    public List<User> findAll() {
        return repository.values().stream().collect(Collectors.toUnmodifiableList());
    }

    @Override
    public User findByUserId(String userId) {
        return repository.values().stream()
                .filter(user -> user.getUserId().equals(userId))
                .findAny()
                .get();
    }
}
