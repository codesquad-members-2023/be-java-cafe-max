package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.Dto.UserListDto;
import kr.codesqaud.cafe.Dto.UserProfileDto;
import kr.codesqaud.cafe.repository.MemoryUserRepository;
import kr.codesqaud.cafe.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {


    private final MemoryUserRepository userRepository;

    @Autowired
    public UserService(MemoryUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //회원가입
    public String join(User user) {
        validateDuplicateMember(user); //중복 회원 검증
        userRepository.save(user);
        return user.getUserId();
    }
    private void validateDuplicateMember(User user) {
        userRepository.findByID(user.getUserId())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    //회원목록 조회
    public List<UserListDto> getUserList() {
        List<User> users = userRepository.findAll();
        List<UserListDto> userListDtos = new ArrayList<>();
        for(User user : users){
            userListDtos.add(new UserListDto(user.getUserId(), user.getName(), user.getEmail()));
        }
        return userListDtos;
    }


    public UserProfileDto getUserProfile(String userId) {
        User user = userRepository.findByID(userId).get();
        return new UserProfileDto(user.getName(), user.getEmail());
    }
}
