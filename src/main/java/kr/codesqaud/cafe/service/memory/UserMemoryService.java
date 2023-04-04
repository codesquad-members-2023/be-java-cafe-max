package kr.codesqaud.cafe.service.memory;

import kr.codesqaud.cafe.domain.user.repository.UserRepository;
import kr.codesqaud.cafe.dto.SignUpFormDto;
import kr.codesqaud.cafe.dto.UpdateFormDto;
import kr.codesqaud.cafe.domain.user.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserMemoryService {

    private final UserRepository userRepository;

    public UserMemoryService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void signUp(SignUpFormDto dto) {
        int index = userRepository.findAll().size();
        User user = new User(index, dto.getUserId(), dto.getPassword(), dto.getName(), dto.getEmail());
        userRepository.save(user);
    }

    public List<User> getList() {
        return userRepository.findAll();
    }

    public void update(String id, UpdateFormDto dto) {
        User current = userRepository.findById(id);
        int index = current.getIndex();
        if (checkPassword(current, dto)) {
            User updateUser = new User(index, dto.getUserId(), dto.getNewPassword(), dto.getName(), dto.getEmail());
            userRepository.save(updateUser);
        }
    }

    public boolean checkPassword(User user, UpdateFormDto updateFormDto) {
        return user.getPassword().equals(updateFormDto.getPassword());
    }

    public User findById(String id) {
        return userRepository.findById(id);
    }
}
