package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.controller.dto.JoinDTO;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //수정 : User.toUser -> joinDTO.toEntity
    public void signUp(final JoinDTO joinDTO) {
        User user = joinDTO.toEntity();
        userRepository.join(user);
    }

    public boolean checkDuplicate(String userId) {
        return userRepository.findByUserId(userId).isPresent();
    }



    public void modify(final long id, final JoinDTO joinDTO) {
        User originUser = userRepository.findById(id).orElse(null);
        originUser.setName(joinDTO.getName());
        originUser.setPassword(joinDTO.getPassword());
        originUser.setEmail(joinDTO.getEmail());
        userRepository.update(originUser);
    }

    public JoinDTO findOne(final long id) {
        Optional<User> wantedUser = userRepository.findById(id);
        return wantedUser.map(JoinDTO::from).orElse(null);
    }

    public List<JoinDTO> findUsers() {
        List<User> userList = userRepository.findAll();
        List<JoinDTO> joinDTOList = new ArrayList<>();
        for (User user : userList) {
            joinDTOList.add(JoinDTO.from(user));
        }
        return joinDTOList;
    }
}



/*
        if (wantedUser.isPresent()) {
        //Optional 객체를 꺼내 가져오려면 get() 메서드 사용
            return UserReadDTO.toUserReadDTO(wantedUser.get());
        }
        return null;
        을 한줄로 변환하면
        => return wantedUser.map(UserReadDTO::toUserReadDTO).orElse(null);

 */
