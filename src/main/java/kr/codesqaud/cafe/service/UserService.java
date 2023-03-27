package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.member.UserLog;
import kr.codesqaud.cafe.domain.member.UserRepository;
import kr.codesqaud.cafe.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService  {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public void signUp(UserDto userDto){
        userRepository.save(userDto);
    }

    public List<UserDto> getList(){
        return UserLog.userLogList;
    }




}
