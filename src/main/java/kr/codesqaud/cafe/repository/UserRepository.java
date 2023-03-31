package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.controller.dto.ProfileEditDTO;
import kr.codesqaud.cafe.controller.dto.UserDTO;

import java.util.*;
import java.util.stream.Collectors;

public class UserRepository {

    private Map<Integer, User> userRepository;

    private static int sequence = 1;

    public UserRepository() {
        this.userRepository = new HashMap();
    }

    public void save(UserDTO userDto) {
        userDto.setId(userDto.getId() == null ? sequence++ : userDto.getId());
        userRepository.put(userDto.getId(), userDto.toUser());
    }

    public List<User> findAll() {
        List<User> users = userRepository.values().stream()
                .map(user -> new User(user.getNickName(), user.getEmail(), user.getPassword(), user.getId()))
                .collect(Collectors.toList());
        return Collections.unmodifiableList(users);
    }

    public UserDTO findUser(int userId) {
        return userRepository.values().stream()
                .filter(user -> user.getId() == userId)
                .findFirst()
                .map(User::toDTO)
                .orElse(null);
    }

    public void findOne(ProfileEditDTO profileEditDto) {
        String tempPassword = userRepository.get(profileEditDto.getId()).getPassword();
        if (tempPassword.equals(profileEditDto.getOriPassword())) {
            save(profileEditDto.toUserDto());
        }
    }
}
