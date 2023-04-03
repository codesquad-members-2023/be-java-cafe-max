package kr.codesqaud.cafe.service.jdbc;

import kr.codesqaud.cafe.domain.member.User;
import kr.codesqaud.cafe.domain.member.repository.UserRepository;
import kr.codesqaud.cafe.dto.SignUpFormDto;
import kr.codesqaud.cafe.dto.UpdateFormDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserJdbcService {
    private final UserRepository userRepository;

    public UserJdbcService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void signUp(SignUpFormDto dto) {
        User user = new User(dto.getUserId(), dto.getPassword(), dto.getName(), dto.getEmail());
        userRepository.save(user);
    }

    public User findById(String id) {

        return userRepository.findById(id);
    }


    public void update(User user, UpdateFormDto dto) {
        User current = user;
        if (checkPassword(current, dto)) {
            userRepository.update(new User(user.getUserId(), dto.getNewPassword(), dto.getName(), dto.getEmail()));
        }

    }

    public boolean checkPassword(User user, UpdateFormDto updateFormDto) {
        return user.getPassword().equals(updateFormDto.getPassword());
    }


    public List<User> users() {
        return userRepository.findAllList();
    }
}
