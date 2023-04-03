package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.user.domain.User;
import kr.codesqaud.cafe.user.repository.MemoryUserRepository;
import kr.codesqaud.cafe.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    UserService userService;
    MemoryUserRepository memoryUserRepository;

    @BeforeEach
    public void beforeEach() {
        memoryUserRepository = new MemoryUserRepository();
        userService = new UserService(memoryUserRepository);
    }

    @Test
    @DisplayName("회원 가입 시 저장 테스트")
    void join() {
        //Given
        User user = new User("userID", "pwd1", "name", "email@eiam.com");

        //When
        String saveId = userService.join(user);

        //Then
        User findUser  = memoryUserRepository.findByID(saveId).get();
        assertEquals(user, findUser);
    }

    @Test
    @DisplayName("회원 아이디 중복일 시 테스트")
    void validateDuplicate() {

        //Given
        User user1 = new User("userID1", "pwd1", "name", "email@eiam.com");
        User user2 = new User("userID1", "pwd1", "name", "email@eiam.com");

        //When
        userService.join(user1);
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> userService.join(user2));//예외가 발생해야 한다.

        //Then
        assertEquals(e.getMessage(),"이미 존재하는 회원입니다.");

    }




}
