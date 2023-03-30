package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository {
    public User save(User user);
    public List<User> findAll();
    public User findByUserId(String userId);
    public void clearUserList();
}
