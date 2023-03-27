package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.controller.dto.UserJoinDto;
import kr.codesqaud.cafe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long join(UserJoinDto userJoinDto) {
        return userRepository.save(userJoinDto.toUser());
    }
}
