package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.member.UserMemoryImpl;
import kr.codesqaud.cafe.domain.member.UserRepository;
import kr.codesqaud.cafe.dto.SignUpFormDto;
import kr.codesqaud.cafe.dto.UpdateFormDto;
import kr.codesqaud.cafe.domain.member.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void signUp(SignUpFormDto dto) {
        int index = userRepository.findAll().size();
        User user = new User(index, dto.getUserId(), dto.getPassword(), dto.getName(), dto.getEmail());
        userRepository.save(user, index);
    }

    public List<User> getList() {
        return new ArrayList<>(userRepository.findAll().values());
    }

    public void update(int index, UpdateFormDto dto) {
        User current = userRepository.findAll().get(index);
        if (checkPassword(current, dto)) {
            User updateUser = new User(index, dto.getUserId(), dto.getNewPassword(), dto.getName(), dto.getEmail());
            userRepository.save(updateUser, index);
        }
    }

    public boolean checkPassword(User user, UpdateFormDto updateFormDto) {
        if (user.getPassword().equals(updateFormDto.getPassword())) {
            return true;
        }
        return false;
    }

}
