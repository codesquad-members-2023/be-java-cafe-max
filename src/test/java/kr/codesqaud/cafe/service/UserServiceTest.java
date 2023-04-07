package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.controller.dto.ProfileEditDTO;
import kr.codesqaud.cafe.controller.dto.UserDTO;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.exception.InvalidPasswordException;
import kr.codesqaud.cafe.exception.UserNotFoundException;
import kr.codesqaud.cafe.repository.UserRepository;
import kr.codesqaud.cafe.repository.impl.MemoryUserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;

class UserServiceTest {
    private UserService userService;

    @BeforeEach
    void initUserService(){
        UserRepository userRepository = new MemoryUserRepository();
        userService = new UserService(userRepository);
    }

    @Test
    @DisplayName("user 추가 성공 테스트")
    void addUser() {
        // given
        User user = new User("charlie","aaa@naver.com","password","testId");

        // when & then
        assertThatCode(() ->userService.addUser(user.toUserDTO())).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("user 조회 성공 테스트")
    void getUserList_success_test() {
        //given
        userService.addUser(new UserDTO("charlie","aaa@naver.com","password","testId1"));
        userService.addUser(new UserDTO("charlie","aaa@naver.com","password","testId2"));
        userService.addUser(new UserDTO("charlie","aaa@naver.com","password","testId3"));

        //when & then
        Assertions.assertThat(userService.getUserList().size() == 3).isTrue();
    }

    @Test
    @DisplayName("user 조회 실패 테스트")
    void getUserList_fail_test() {
        //given
        userService.addUser(new UserDTO("charlie1","aaa@naver.com","password","testId1"));
        userService.addUser(new UserDTO("charlie2","aaa@naver.com","password","testId2"));
        userService.addUser(new UserDTO("charlie3","aaa@naver.com","password","testId3"));

        //when & then
        Assertions.assertThat(userService.getUserList().size() == 4).isFalse();
    }

    @Test
    @DisplayName("유저 검색을 성공한 경우 예외가 발생하지 않는다.")
    void getUserByUserId_success_test() {

        //given
        userService.addUser(new UserDTO("charlie","aaa@naver.com","password","testId"));

        //when & then
        Assertions.assertThatCode(() ->userService.getUserById("testId")).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("유저 검색을 실패한 경우 예외가 발생한다.")
    void getUserByUserId_fail_test() {
        //given
        userService.addUser(new UserDTO("charlie","aaa@naver.com","password","testId"));

        //when & then
        Assertions.assertThatThrownBy(()-> userService.getUserById("hello")).isInstanceOf(UserNotFoundException.class);
    }



    @Test
    @DisplayName("입력한 비밀번호가 일치할때 user의 정보를 바꿀수있다.")
    void updateUserByUserId_success_test() {
        //given
        userService.addUser(new UserDTO("charlie", "aaa@naver.com", "password", "testId"));
        ProfileEditDTO profileEditDTO = new ProfileEditDTO("newName", "newEmail@naver.com", "newPassword", "password","testId");

        //when
        Assertions.assertThatCode(() -> userService.updateUserByUserId(profileEditDTO)).doesNotThrowAnyException();

        //then
        UserDTO changedUser = userService.getUserById("testId");
        Assertions.assertThat(changedUser.getNickName().equals(profileEditDTO.getNickName())).isTrue();
        Assertions.assertThat(changedUser.getEmail().equals(profileEditDTO.getEmail())).isTrue();
        Assertions.assertThat(changedUser.getPassword().equals(profileEditDTO.getNewPassword())).isTrue();
    }

    @Test
    @DisplayName("입력한 비밀번호가 일치하지 않으면 user의 정보를 바꿀수없다.")
    void updateUserByUserId_fail_test() {
        //given
        userService.addUser(new UserDTO("charlie","aaa@naver.com","password","testId"));
        ProfileEditDTO profileEditDTO = new ProfileEditDTO("newName","newEmail@naver.com","newPassword","password123","testId");

        //when
        Assertions.assertThatThrownBy(() -> userService.updateUserByUserId(profileEditDTO)).isInstanceOf(InvalidPasswordException.class);

        //then
        UserDTO changedUser = userService.getUserById("testId");
        Assertions.assertThat(changedUser.getNickName().equals(profileEditDTO.getNickName())).isFalse();
        Assertions.assertThat(changedUser.getEmail().equals(profileEditDTO.getEmail())).isFalse();
        Assertions.assertThat(changedUser.getPassword().equals(profileEditDTO.getNewPassword())).isFalse();
    }
}