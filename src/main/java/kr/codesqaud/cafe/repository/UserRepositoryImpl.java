package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.db.DB;
import kr.codesqaud.cafe.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    DB db = DB.getInstance();

    public void save(User user) {
        db.create(user);
    }
    @Override
    public List<User> findAll() {
        return db.read("User");
    }
    @Override
    public User findById(Long id) {
        List<User> userList = db.read("User");
        User userDto = userList.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElse(null);
        if (userDto == null) {
            throw new IllegalArgumentException("존재하지 않는 회원입니다.");
        }
        return userDto;
    }



}
