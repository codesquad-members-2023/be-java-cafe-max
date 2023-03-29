package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserFormRepository implements UserRepository {
    private final List<User> userList = new ArrayList<>();
    @Override
    public User save(User user) {
        validateDuplicateMember(user);
        userList.add(user);
        return user;
    }

    private void validateDuplicateMember(User user) {
        findByUserID(user.getUserID())
                .ifPresent(u -> {
                    throw new IllegalStateException("이미 존재하는 회원 아이디입니다.");
                });
        findByEmail(user.getEmail())
                .ifPresent(u -> {
            throw new IllegalStateException("이미 존재하는 회원 이메일입니다.");
                });
        findByNickname(user.getNickname())
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
