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

    public UserRepository() {
        users = new ArrayList<>();
    }

    public void save(SignUpDTO dto) {
        int idx = users.size() + 1;
        users.add(dto.convertToUser(idx));
    }

    public List<UserDTO> findAll() {
        return users.stream()
                .map(User::convertToDTO)
                .collect(Collectors.toUnmodifiableList());
    }

}
