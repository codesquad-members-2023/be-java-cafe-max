package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.member.UserMemoryImpl;
import kr.codesqaud.cafe.domain.member.UserRepository;
import kr.codesqaud.cafe.dto.SignUpFormDto;
import kr.codesqaud.cafe.dto.UpdateFormDto;
import kr.codesqaud.cafe.domain.member.User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class UserService  {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public void signUp(SignUpFormDto signUpFormDto){
        int index = userRepository.findAll().size();
        String id = signUpFormDto.getUserId();
        String password = signUpFormDto.getPassword();
        String name = signUpFormDto.getName();
        String email = signUpFormDto.getEmail();
        User user = new User(index,id,password,name,email);
        userRepository.save(user);
    }

    public List<User> getList(){
        return userRepository.findAll();
    }



}
