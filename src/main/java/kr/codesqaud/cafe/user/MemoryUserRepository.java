package kr.codesqaud.cafe.user;

import org.springframework.stereotype.Repository;

import java.util.*;

//  메모리 회원 저장소 구현체
@Repository
public class MemoryUserRepository implements UserRepository {

    private List<User> users;

    public MemoryUserRepository(List<User> users) {
        this.users = users;
    }

    @Override
    public void save(User user) {
        users.add(user);
    }

    @Override
    public List<User> findAll() {
        return users;
    }

    public void update(User updatedUser) {
        for (User user : users) {
            if (user.getUserId().equals(updatedUser.getUserId())) {
                user.setName(updatedUser.getName());
                user.setPassword(updatedUser.getPassword());
                user.setEmail(updatedUser.getEmail());
            }
        }
    }

    @Override
    public User findById(String userId) {

        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                return user;
            }
        }
        return null;
    }
}
