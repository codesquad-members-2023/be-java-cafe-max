package kr.codesqaud.cafe.domain.member;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserMemoryImpl implements UserRepository{

    private  List<User> userLogList = new ArrayList<>();

    @Override
    public void save(User user) {
        userLogList.add(user);
    }

    @Override
    public List<User> findAll(){

        return userLogList;
    }
}
