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

    public void update(int index, UpdateFormDto updateFormDto){
        User current = getList().get(index);
        if(checkPassword(current,updateFormDto)){
            getList().remove(index);
            String id = updateFormDto.getUserId();
            String password = updateFormDto.getNewPassword();
            String name = updateFormDto.getName();
            String email = updateFormDto.getEmail();
            User updateUser = new User(index,id, password,name,email);
            userRepository.save(updateUser);
            Collections.sort(getList());
        }
        // if 안타면 예외처리 생각중
    }
    public boolean checkPassword(User user, UpdateFormDto updateFormDto){
        if(user.getPassword().equals(updateFormDto.getPassword())){
            return true;
        }
        return false;
    }

}
