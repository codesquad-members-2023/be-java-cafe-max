package kr.codesqaud.cafe.user.repository;

import kr.codesqaud.cafe.user.domain.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryUserRepository implements UserRepository {

    private static Map<String, User> storage = new HashMap<>();

    //userID는 서비스에서 중복을 검사해서 고유ID이기 때문에 key로 userID를 넣어줌
    @Override
    public String save(User user) {
        storage.put(user.getUserId(), user);
        return user.getUserId();
    }

    //회원(객체) 전체 리턴
    @Override
    public List<User> findAll() {
        return new ArrayList<>(storage.values());
    }

    //userID로 찾아서 반환
    @Override
    public Optional<User> findByID(String userId) {
        return storage.values().stream()
                .filter(user -> user.getUserId().equals(userId))
                .findAny();
    }
}
