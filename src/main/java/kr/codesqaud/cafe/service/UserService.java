package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.Repository.user.MemoryUserRepository;
import kr.codesqaud.cafe.Repository.user.UserRepository;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.dto.user.UserRequestDto;
import kr.codesqaud.cafe.dto.user.UserResponseDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void join(final UserRequestDto userRequestDto) {
        checkDuplicateId(userRequestDto.getEmail());
        userRepository.save(userRequestDto.toEntity());
    }

    private void checkDuplicateId(final String email) {
        userRepository.findByEmail(email)
                .map(User::toDto)
                .ifPresent(dto -> {
                    throw new IllegalArgumentException("이미 존재하는 회원입니다.");
                });
    }

    public UserResponseDto findUserById(final String id) {
        final UserResponseDto userResponseDto = userRepository.findById(id)
                        .map(User::toDto).
                orElseThrow( () ->  new IllegalArgumentException("해당 유저가 없습니다."));
        return userResponseDto;

    }

    public List<UserResponseDto> showAllUser() {
        final List<User> users = userRepository.findAllUser();
        final List<UserResponseDto> userResponseDtos = new ArrayList<>();
        for (User user : users) {
            userResponseDtos.add(user.toDto());
        }
        return userResponseDtos;
    }
}
