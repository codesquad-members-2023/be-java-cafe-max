package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.member.UserLog;
import kr.codesqaud.cafe.domain.member.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserRepository {


    @Override
    public void save(UserLog userLog) {
        UserLog.userLogList.add(userLog);
    }

    @Override
    public List<UserLog> findAll(){

        return UserLog.userLogList;
    }


}
