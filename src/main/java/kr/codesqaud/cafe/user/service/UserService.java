package kr.codesqaud.cafe.user.service;

import kr.codesqaud.cafe.exception.DuplicateKeyException;
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

    public String addUser(UserAddForm userAddForm) {
        if (!userJdbcRepository.containsUserId(userAddForm.getUserId())) {
            throw new DuplicateKeyException("중복된 아이디가 이미 존재합니다.");
        }
        return userJdbcRepository.save(userAddForm.toUser());
    }

    public UserResponse getUser(String userId) {
        return UserResponse.fromUser(userJdbcRepository.findByUserId(userId));
    }

    public List<UserResponse> getUserList() {
        return userJdbcRepository.findAll().stream().map(UserResponse::fromUser).collect(Collectors.toList());
    }
}
