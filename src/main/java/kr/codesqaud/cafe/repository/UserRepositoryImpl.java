package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.db.DB;
import kr.codesqaud.cafe.dto.SignupRequestDto;
import kr.codesqaud.cafe.model.TableName;
import kr.codesqaud.cafe.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    DB db = DB.getInstance();

    @Override
    public void save(SignupRequestDto signupRequestDto) {
        // dto -> entity
        User user = signupRequestDto.toUser();
        db.create(user, TableName.USER);
    }

    @Override
    public List<User> findAll() {
        return (List<User>) db.getTableList(TableName.USER);
    }

    @Override
    public User findById(Long id) {
        return (User) db.data.get(TableName.USER).get(id);
    }
    @Override
    public User findByEmail(String email) {
        // key 는 id 니까 key 를 모르는 채로 순회하면 리스트가 낫지 않을까?
        List<User> userList = db.getTableList(TableName.USER);
        return userList.stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }
}
