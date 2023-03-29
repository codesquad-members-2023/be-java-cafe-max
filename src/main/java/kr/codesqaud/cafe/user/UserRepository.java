package kr.codesqaud.cafe.user;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private List<User> repository = new ArrayList<>();

    public void save(User user) {
        repository.add(user);
    }

    public List<User> getRepository() {
        return repository;
    }
}
