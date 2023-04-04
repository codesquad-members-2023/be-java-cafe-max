package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MemoryUserRepositoryTest {

    MemoryUserRepository memoryUserRepository;

    @BeforeEach
    public void beforeEach() {
        memoryUserRepository = new MemoryUserRepository();
    }


    @Test
    @DisplayName("회원 가입 시 저장 테스트")
    void save() {
        //Given
        User user = new User("userID", "pwd1", "name", "email@eiam.com");

        //When
        String saveId = memoryUserRepository.save(user);

        //Then
        User findUser  = memoryUserRepository.findByID(saveId).get();
        assertEquals(user, findUser);

    }



    @Test
    void findAll() {
        //Given
        User user1 = new User("userID1", "pwd1", "name", "email@eiam.com");
        User user2 = new User("userID2", "pwd1", "name", "email@eiam.com");
        memoryUserRepository.save(user1);
        memoryUserRepository.save(user2);

        //When
        List<User> result = memoryUserRepository.findAll();

        //Then
        assertEquals(result.size(),2);


    }

    @Test
    void findByID() {

        //Given
        User user = new User("userID", "pwd1", "name", "email@eiam.com");
        String id = memoryUserRepository.save(user);


        //when
        User findUser = memoryUserRepository.findByID(id).get();

        //then
        assertEquals(user, findUser);
    }
}
