package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.db.DB;
import kr.codesqaud.cafe.dto.SignupRequestDto;
import kr.codesqaud.cafe.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    DB db = DB.getInstance();

    @Override
    public void save(SignupRequestDto signupRequestDto) {
        User user = signupRequestDto.toUser();
        db.create(user, "User");
    }

    @Override
    public List<User> findAll() {
        return db.findAll("User");
    }
    @Override
    public User findById(Long id) {
        List<User> userList = db.findAll("User");
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
