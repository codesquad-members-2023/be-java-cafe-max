package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository {
    public void save(User user);

    public List<User> findAll();

    public Optional<User> findByUserId(String userId);
}
