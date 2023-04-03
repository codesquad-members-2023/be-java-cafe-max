package kr.codesqaud.cafe.user.service;

import kr.codesqaud.cafe.user.domain.User;
import kr.codesqaud.cafe.user.dto.UserListDto;
import kr.codesqaud.cafe.user.dto.UserProfileDto;
import kr.codesqaud.cafe.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class UserService {


    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //ID 중복 확인 후 회원가입
    public String join(User user) {
        validateDuplicateMember(user); //중복 회원 검증
        userRepository.save(user);
        return user.getUserId();
    }

    //중복 확인(중복이 들어오면 에러 페이지로 감)
    private void validateDuplicateMember(User user) {
        userRepository.findByID(user.getUserId())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }


    //회원목록 조회(+DTO로 필터)
    public List<UserListDto> getUserList() {
        List<User> users = userRepository.findAll();
        List<UserListDto> userListDtos = new ArrayList<>();
        for (User user : users) {
            userListDtos.add(new UserListDto(user.getUserId(), user.getName(), user.getEmail()));
        }
        return userListDtos;
    }


    //특정 회원 조회(+DTO 필터)
    public UserProfileDto getUserProfile(String userId) {
        User user = userRepository.findByID(userId).get();
        return new UserProfileDto(user.getName(), user.getEmail());
    }
}
