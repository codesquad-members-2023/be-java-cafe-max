package kr.codesqaud.cafe;

import kr.codesqaud.cafe.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {

    private List<User> users = new ArrayList<>();

    public void save(User user){
     users.add(User);
    }

    public void findAll() {
        return users;
    }
}
