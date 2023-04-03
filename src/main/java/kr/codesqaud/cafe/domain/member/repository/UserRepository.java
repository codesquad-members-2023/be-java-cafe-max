package kr.codesqaud.cafe.domain.member.repository;

import kr.codesqaud.cafe.domain.member.User;

import java.util.List;
import java.util.Map;


public interface UserRepository {
    // 메모리 저장소
    default void save(User user, int index){}

    default Map<Integer, User> findAll(){
        return null;
    }

    // jdbc
    default void save(User user){}
    default List<User> findAllList(){
        return null;
    }


}
