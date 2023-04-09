package kr.codesqaud.cafe;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepository {

    private List<User> users = new ArrayList<>();
    private Map<String, User> dataBase  = new HashMap<>();

    public void save(User user){
        User newUser = user;
        dataBase.put(user.getUserId(), newUser);
    }

    public List<User> getUserList() {
        return users;
    } // 머스테치 #users에 쓰일 듯
}
