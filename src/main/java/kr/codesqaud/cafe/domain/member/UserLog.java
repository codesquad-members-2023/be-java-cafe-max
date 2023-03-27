package kr.codesqaud.cafe.domain.member;

import kr.codesqaud.cafe.dto.UserDto;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserLog implements UserRepository {

    public static List<UserDto> userLogList = new ArrayList<>();


    @Override
    public void save(UserDto userDto) {
        userLogList.add(userDto);
    }

    @Override
    public List<UserDto> findAll(){

        return userLogList;
    }
}
