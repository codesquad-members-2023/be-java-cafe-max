package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    public void save(User user);

    public Optional<User> getUserById(Long Id);

    public List<User> getUserList();

    public void clearStore();

    public void update(User user);
}
