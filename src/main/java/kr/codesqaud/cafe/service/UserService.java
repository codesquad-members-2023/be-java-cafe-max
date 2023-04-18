package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.dto.UserDto;
import kr.codesqaud.cafe.dto.UserForm;
import kr.codesqaud.cafe.repository.JdbcUserRepository;
import kr.codesqaud.cafe.repository.MemoryUserRepository;
import kr.codesqaud.cafe.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(MemoryUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveUser(UserForm userForm) {
        User user = User.builder()
                .userId(userForm.getUserId())
                .password(userForm.getPassword())
                .userName(userForm.getUserName())
                .email(userForm.getEmail())
                .build();

        userRepository.save(user);
    }

    public List<UserDto> findAll() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        
        for (User user : users) {
            UserDto dto = UserDto.builder()
                    .userId(user.getUserId())
                    .userName(user.getUserName())
                    .email(user.getEmail())
                    .build();
            userDtos.add(dto);
        }
        return userDtos;
    }

    public User findByUserId(String userId) {
        return userRepository.findByUserId(userId);
    }
}
