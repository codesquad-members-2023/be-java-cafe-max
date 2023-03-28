package kr.codesqaud.cafe.user;

// 회원 저장소 인터페이스
public interface UserRepository {
    void save(User user);
    User findById(String id);
}