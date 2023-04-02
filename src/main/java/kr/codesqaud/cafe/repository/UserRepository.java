package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.dto.SignUpDTO;
import kr.codesqaud.cafe.dto.UserDTO;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class UserRepository {
    private List<User> users;
    private int userIdx = 1;

    public UserRepository() {
        users = new ArrayList<>();
    }

    public synchronized void save(SignUpDTO dto) {
        users.add(dto.toUser(userIdx++));
    }

    public List<UserDTO> findAll() {
        return users.stream()
                .map(User::toDto)
                .collect(Collectors.toUnmodifiableList());
    }

}
