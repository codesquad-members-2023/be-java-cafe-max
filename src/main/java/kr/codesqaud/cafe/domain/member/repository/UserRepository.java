package kr.codesqaud.cafe.domain.member;

import java.util.Map;


public interface UserRepository {
    default void save(User user, int index){};

    default Map<Integer, User> findAll(){
        return null;
    };

}
