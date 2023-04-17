package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.dto.user.UserResponse;
import kr.codesqaud.cafe.dto.user.UserSaveRequest;
import kr.codesqaud.cafe.dto.user.UserUpdateRequest;
import kr.codesqaud.cafe.exception.user.AlreadyUserExistenceException;
import kr.codesqaud.cafe.exception.user.MismatchedPasswordException;
import kr.codesqaud.cafe.exception.user.UserNotFoundException;
import kr.codesqaud.cafe.repository.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@Transactional
@SpringBootTest
class UserServiceTest {

    UserService userService;

    @Qualifier("jdbcUserRepository")
    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        this.userService = new UserService(userRepository);
    }

    @DisplayName("userSaveRequest가 DB에 정상적으로 저장됐는지 확인하는 테스트")
    @Test
    void saveUser() {
        // given
        UserSaveRequest userSaveRequest = new UserSaveRequest();
        userSaveRequest.setUserId("saveRequest1");
        userSaveRequest.setPassword("saveRequest1");
        userSaveRequest.setName("saveRequest1");
        userSaveRequest.setEmail("user@email.com");

        // when
        String userId = userService.saveUser(userSaveRequest);

        // then
        assertAll(
            () -> assertThat(userId).isEqualTo(userSaveRequest.getUserId()),
            () -> assertThatThrownBy(() -> userService.saveUser(userSaveRequest))
                        .isInstanceOf(AlreadyUserExistenceException.class)
                        .hasMessage("이미 사용 중인 아이디입니다."));
    }

    @DisplayName("getAllUsers를 통해 모든 user를 List로 가져오는지 확인하는 테스트")
    @Test
    void getAllUsers() {
        // given
        UserSaveRequest userSaveRequest1 = new UserSaveRequest();
        userSaveRequest1.setUserId("saveRequest1");
        userSaveRequest1.setPassword("saveRequest1");
        userSaveRequest1.setName("saveRequest1");
        userSaveRequest1.setEmail("user@email.com");

        UserSaveRequest userSaveRequest2 = new UserSaveRequest();
        userSaveRequest2.setUserId("saveRequest2");
        userSaveRequest2.setPassword("saveRequest2");
        userSaveRequest2.setName("saveRequest2");
        userSaveRequest2.setEmail("user@email.com");

        String userId1 = userService.saveUser(userSaveRequest1);
        String userId2 = userService.saveUser(userSaveRequest2);

        // when
        List<UserResponse> users = userService.getAllUsers();

        // then
        assertAll(
            () -> assertThat(users.size()).isEqualTo(2),
            () -> assertThat(users.get(0).getUserId()).isEqualTo(userId1),
            () -> assertThat(users.get(1).getUserId()).isEqualTo(userId2));
    }

    @DisplayName("userUpdateRequest를 통해 전달받은 수정 요청 데이터가 정상적으로 DB에 반영되는지 확인하는 테스트")
    @Test
    void updateUser() {
        // given
        UserSaveRequest userSaveRequest = new UserSaveRequest();
        userSaveRequest.setUserId("saveRequest");
        userSaveRequest.setPassword("currentPassword");
        userSaveRequest.setName("saveRequest");
        userSaveRequest.setEmail("user@email.com");
        userService.saveUser(userSaveRequest);

        UserUpdateRequest userUpdateRequest = new UserUpdateRequest();
        userUpdateRequest.setUserId("saveRequest");
        userUpdateRequest.setCurrentPassword("currentPassword");
        userUpdateRequest.setNewPassword("newPassword");
        userUpdateRequest.setName("saveRequest");
        userUpdateRequest.setEmail("user@email.com");

        UserUpdateRequest wrongUserUpdateRequest = new UserUpdateRequest();
        wrongUserUpdateRequest.setUserId("saveRequest");
        wrongUserUpdateRequest.setCurrentPassword("wrongPassword");
        wrongUserUpdateRequest.setNewPassword("newPassword");
        wrongUserUpdateRequest.setName("saveRequest");
        wrongUserUpdateRequest.setEmail("user@email.com");

        // when
        int count = userService.updateUser(userUpdateRequest);

        // then
        assertAll(
            () -> assertThat(count).isEqualTo(1),
            () -> assertThatThrownBy(() -> userService.updateUser(wrongUserUpdateRequest))
                        .isInstanceOf(MismatchedPasswordException.class)
                        .hasMessage("비밀번호가 일치하지 않습니다."));
    }

    @DisplayName("userId가 일치하는 user의 정보를 바탕으로 userResponse를 정상적으로 생성하는지 확인하는 테스트")
    @Test
    void findByUserId() {
        // given
        UserSaveRequest userSaveRequest = new UserSaveRequest();
        userSaveRequest.setUserId("test1234");
        userSaveRequest.setPassword("test1234");
        userSaveRequest.setName("test1234");
        userSaveRequest.setEmail("test@email.com");
        String userId = userService.saveUser(userSaveRequest);

        // when
        UserResponse userResponse = userService.findByUserId(userId);

        // then
        assertAll(
            () -> assertThat(userResponse.getUserId()).isEqualTo(userSaveRequest.getUserId()),
            () -> assertThat(userResponse.getName()).isEqualTo(userSaveRequest.getName()),
            () -> assertThat(userResponse.getEmail()).isEqualTo(userSaveRequest.getEmail()),
            () -> assertThatThrownBy(() -> userService.findByUserId("none"))
                        .isInstanceOf(UserNotFoundException.class)
                        .hasMessage("존재하지 않는 회원입니다."));
        }

    @DisplayName("userId가 일치하는 user의 정보를 바탕으로 userUpdateRequest를 생성하는지 확인하는 테스트")
    @Test
    void makeUserUpdateRequestByUserId() {
        // given
        UserSaveRequest userSaveRequest = new UserSaveRequest();
        userSaveRequest.setUserId("saveRequest1");
        userSaveRequest.setPassword("saveRequest1");
        userSaveRequest.setName("saveRequest1");
        userSaveRequest.setEmail("user@email.com");
        String userId = userService.saveUser(userSaveRequest);

        // when
        UserUpdateRequest userUpdateRequest = userService.makeUserUpdateRequestByUserId(userId);

        // then
        assertAll(
            () -> assertThat(userUpdateRequest.getUserId()).isEqualTo(userSaveRequest.getUserId()),
            () -> assertThat(userUpdateRequest.getName()).isEqualTo(userSaveRequest.getName()),
            () -> assertThat(userUpdateRequest.getEmail()).isEqualTo(userSaveRequest.getEmail()),
            () -> assertThatThrownBy(() -> userService.makeUserUpdateRequestByUserId("none"))
                    .isInstanceOf(UserNotFoundException.class)
                    .hasMessage("존재하지 않는 회원입니다."));
    }
}
