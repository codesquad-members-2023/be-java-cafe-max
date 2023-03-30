package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.dto.UserDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserRepository {

    private List<User> userRepository;

    public UserRepository() {
        this.userRepository = new ArrayList<>();
    }

    public void save(UserDto userDto) {
        userDto.setUserId(userRepository.size());
        userRepository.add(userDto.ToUser());
    }

    public List<UserDto> findAll() {
        return userRepository.stream()
                .map(user -> new UserDto(user.getNickName(), user.getEmail(), user.getPassword(),user.getUserId()))
                .collect(Collectors.toList());
    }

    /**
     * userId를 통해 해당 id에 해당하는 userDto를 return한다.
     * @param userId
     * @return userDto
     */

    public UserDto findUser(int userId) {
        return userRepository.stream()
                .filter(user -> user.getUserId() == userId)
                .findFirst()
                .map(User::ToDTO)
                .orElse(null);
    }
}
