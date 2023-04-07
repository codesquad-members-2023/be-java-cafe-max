package kr.codesqaud.cafe.user.service;

import kr.codesqaud.cafe.exception.DuplicateKeyException;
import kr.codesqaud.cafe.user.domain.User;
import kr.codesqaud.cafe.user.dto.UserAddForm;
import kr.codesqaud.cafe.user.dto.UserResponse;
import kr.codesqaud.cafe.user.repository.UserJdbcRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserJdbcRepository userJdbcRepository;

    public UserService(UserJdbcRepository userJdbcRepository) {
        this.userJdbcRepository = userJdbcRepository;
    }

    public String add(UserAddForm userAddForm) {
        if (userJdbcRepository.containsUserId(userAddForm.getUserId()) > 0) {
            throw new DuplicateKeyException("중복된 아이디가 이미 존재합니다.");
        }
        return userJdbcRepository.save(userAddForm.toUser());
    }

    public UserResponse getUser(String userId) {
        return userJdbcRepository.findByUserId(userId).toUserResponse();
    }

    public List<UserResponse> getUserList() {
        return userJdbcRepository.findAll().stream().map(User::toUserResponse).collect(Collectors.toList());
    }
}
