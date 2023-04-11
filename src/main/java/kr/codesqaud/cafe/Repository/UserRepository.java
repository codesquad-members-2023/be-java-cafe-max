package kr.codesqaud.cafe.Repository;

import kr.codesqaud.cafe.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    public void save(User user);


    public Optional<User> findById(String id);


    public Optional<User> findByEmail(String email);


    public List<User> findAllUser();


}
