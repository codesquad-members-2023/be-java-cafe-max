package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.controller.dto.UserJoinDto;
import kr.codesqaud.cafe.controller.dto.UserReadDto;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.exception.user.UserJoinFailedException;
import kr.codesqaud.cafe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long join(final UserJoinDto userJoinDto) {
        if (userRepository.findByUserId(userJoinDto.getUserId()).isPresent()) {
            throw new UserJoinFailedException("이미 존재하는 아이디입니다", userJoinDto, "error-userId");
        }
        if (userRepository.findByEmail(userJoinDto.getEmail()).isPresent()) {
            throw new UserJoinFailedException("이미 존재하는 이메일입니다.", userJoinDto, "error-email");
        }

        return userRepository.save(userJoinDto.toUser());
    }

    public UserReadDto find(final Long id) {
        final User user = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다."));

        return new UserReadDto(user);
    }

    public List<UserReadDto> findAll() {
        return userRepository.findAll().stream()
                .map(UserReadDto::new)
                .collect(Collectors.toList());
    }
}
