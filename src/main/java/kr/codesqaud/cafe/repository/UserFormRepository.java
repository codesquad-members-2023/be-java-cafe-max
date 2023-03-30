package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.dto.UserDto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserFormRepository implements UserRepository {
    private final List<User> userList = new ArrayList<>();
    private long index = 0L;
    @Override
    public User save(UserDto userDto) {
        validateDuplicateMember(userDto);
        User user = new User(++index, userDto.getUserID()
                , userDto.getEmail(), userDto.getNickname(), userDto.getPassword(), signUpDate());
        userList.add(user);
        return user;
    }

    private String signUpDate() {
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        return  now.format(formatter);
    }

    private void validateDuplicateMember(UserDto userDto) {
        findByUserID(userDto.getUserID())
                .ifPresent(u -> {
                    throw new IllegalStateException("이미 존재하는 회원 아이디입니다.");
                });
        findByEmail(userDto.getEmail())
                .ifPresent(u -> {
            throw new IllegalStateException("이미 존재하는 회원 이메일입니다.");
                });
        findByNickname(userDto.getNickname())
                .ifPresent(u -> {
            throw new IllegalStateException("이미 존재하는 회원 닉네임입니다.");
        });
    }

    @Override
    public Optional<User> findByUserID(String uerID) {
        return userList.stream()
                .filter(user -> user.getUserID().equals(uerID))
                .findAny();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userList.stream()
                .filter(user -> user.getEmail().equals(email))
                .findAny();
    }

    @Override
    public Optional<User> findByNickname(String nickname) {
        return userList.stream()
                .filter(user -> user.getNickname().equals(nickname))
                .findAny();
    }

    @Override
    public Optional<User> findByPassword(String password) {
        return userList.stream()
                .filter(user -> user.getPassword().equals(password))
                .findAny();
    }

    @Override
    public List<User> findAll() {
        return userList;
    }
}
