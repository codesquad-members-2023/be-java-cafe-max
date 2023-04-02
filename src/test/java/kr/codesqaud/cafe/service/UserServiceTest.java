package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.controller.dto.ProfileEditDTO;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.exception.PasswordNotFoundException;
import kr.codesqaud.cafe.exception.UserNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserServiceTest {
    private UserService userService;

    @BeforeEach
    void initUserService(){
        userService = new UserService();
    }

    @Test
    @DisplayName("user add 성공 테스트")
    void addUser() {
        // given
        User user = new User("charlie","aaa@naver.com","password","testId");

        // when & then
        Assertions.assertDoesNotThrow(() -> userService.addUser(user));
    }

    @Test
    @DisplayName("user 조회 성공 테스트")
    void getUserList_success_test() {
        //given
        userService.addUser(new User("charlie","aaa@naver.com","password","testId1"));
        userService.addUser(new User("charlie","aaa@naver.com","password","testId2"));
        userService.addUser(new User("charlie","aaa@naver.com","password","testId3"));

        //when & then
        Assertions.assertTrue(userService.getUserList().size() == 3);
    }

    @Test
    @DisplayName("user 조회 실패 테스트")
    void getUserList_fail_test() {
        //given
        userService.addUser(new User("charlie1","aaa@naver.com","password","testId"));
        userService.addUser(new User("charlie2","aaa@naver.com","password","testId"));
        userService.addUser(new User("charlie3","aaa@naver.com","password","testId"));

        //when & then
        Assertions.assertFalse(userService.getUserList().size() == 4);
    }

    @Test
    @DisplayName("저장된 user id를 검색했을때 예외가 발생하지 않는다.")
    void getUserByUserId_success_test() {
        //given
        userService.addUser(new User("charlie","aaa@naver.com","password","testId"));

        //when & then
        Assertions.assertDoesNotThrow(() ->userService.getUserByUserId("testId"));
    }

    @Test
    @DisplayName("저장되지 않는 user id를 검색했을때 예외가 발생한다.")
    void getUserByUserId_fail_test() {
        //given
        userService.addUser(new User("charlie","aaa@naver.com","password","testId"));

        //when & then
        Assertions.assertThrows(UserNotFoundException.class,()-> userService.getUserByUserId("hello"));
    }



    @Test
    @DisplayName("password가 일치할때 user의 정보를 바꿀수있다.")
    void updateUserByUserId_success_test() {
        //given
        userService.addUser(new User("charlie", "aaa@naver.com", "password", "testId"));
        ProfileEditDTO profileEditDTO = new ProfileEditDTO("newName", "newEmail@naver.com", "newPassword", "password");

        //when
        Assertions.assertDoesNotThrow(() -> userService.updateUserByUserId(profileEditDTO, "testId"));

        //then
        User changedUser = userService.getUserByUserId("testId");
        Assertions.assertTrue(changedUser.getNickName().equals(profileEditDTO.getNickName()));
        Assertions.assertTrue(changedUser.getEmail().equals(profileEditDTO.getEmail()));
        Assertions.assertTrue(changedUser.getPassword().equals(profileEditDTO.getNewPassword()));
    }

    @Test
    @DisplayName("password가 일치하지 않으면 user의 정보를 바꿀수없다.")
    void updateUserByUserId_fail_test() {
        //given
        userService.addUser(new User("charlie","aaa@naver.com","password","testId"));
        ProfileEditDTO profileEditDTO = new ProfileEditDTO("newName","newEmail@naver.com","newPassword","password123");

        //when
        Assertions.assertThrows(PasswordNotFoundException.class,() -> userService.updateUserByUserId(profileEditDTO, "testId"));

        //then
        User changedUser = userService.getUserByUserId("testId");
        Assertions.assertFalse(changedUser.getNickName().equals(profileEditDTO.getNickName()));
        Assertions.assertFalse(changedUser.getEmail().equals(profileEditDTO.getEmail()));
        Assertions.assertFalse(changedUser.getPassword().equals(profileEditDTO.getNewPassword()));
    }
}