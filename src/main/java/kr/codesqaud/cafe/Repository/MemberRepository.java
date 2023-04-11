package kr.codesqaud.cafe.Repository;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.domain.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    public void save(User user);


    public Optional<User> findById(String id);


    public Optional<User> findByEmail(String email);


    public List<User> findAllUser();


}
