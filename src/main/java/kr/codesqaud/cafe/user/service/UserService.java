package kr.codesqaud.cafe.user.service;

import kr.codesqaud.cafe.user.domain.User;
import kr.codesqaud.cafe.user.dto.RequestForm;
import kr.codesqaud.cafe.user.dto.ResponsePreview;
import kr.codesqaud.cafe.user.dto.ResponseProfile;
import kr.codesqaud.cafe.user.mapper.UserDtoMapper;
import kr.codesqaud.cafe.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {


    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //ID 중복 확인 후 회원가입
    public String join(RequestForm requestForm) {
        User user = UserDtoMapper.INSTANCE.toUser(requestForm);
        validateDuplicateMember(user); //중복 회원 검증
        userRepository.save(user);
        return user.getUserId();
    }

    //중복 확인(중복이 들어오면 에러 페이지로 감)
    private void validateDuplicateMember(User user) {
        userRepository.findById(user.getUserId()).ifPresent(m-> {
            throw new  IllegalStateException("이미 존재하는 회원입니다.");
        });
    }


    //회원목록 조회(+DTO로 필터)
    public List<ResponsePreview> getUserList() {
        List<User> users = userRepository.findAll();
        List<ResponsePreview> responsePreviews = new ArrayList<>();
        for (User user : users) {
            responsePreviews.add(UserDtoMapper.INSTANCE.toPreviewDto(user));
        }
        return responsePreviews;
    }


    //특정 회원 조회(+DTO 필터)
    public ResponseProfile getUserProfile(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다"));
        return UserDtoMapper.INSTANCE.toProfileDto(user);
    }

    public User findAndAuthenticate(String userId, String password) {
        User user = userRepository.findById(userId).orElse(null);
        if(user != null){
            return user.getPassword().equals(password) ? user : null;
        }
        return null;
    }
}
