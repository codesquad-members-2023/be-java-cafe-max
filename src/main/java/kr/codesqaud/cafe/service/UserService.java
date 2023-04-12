package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.controller.dto.JoinDTO;
import kr.codesqaud.cafe.controller.dto.ModifiedUserDTO;
import kr.codesqaud.cafe.controller.dto.ProfileDTO;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public void modify(final long id, final ModifiedUserDTO modifiedUserDTO) {
        User originUser = userRepository.findById(id).orElse(null);
        originUser.setName(modifiedUserDTO.getName());
        originUser.setPassword(modifiedUserDTO.getNewPassword());
        originUser.setEmail(modifiedUserDTO.getEmail());
        userRepository.update(originUser);
    }

    public boolean isPasswordRight(long id, ModifiedUserDTO modifiedUserDTO) {
        User originUser = userRepository.findById(id).orElse(null);
        return originUser.getPassword().equals(modifiedUserDTO.getOriginPassword());
    }

    public ProfileDTO findOne(final long id) {
        Optional<User> wantedUser = userRepository.findById(id);
        return wantedUser.map(ProfileDTO::from).orElse(null);
    }

    public List<ProfileDTO> findUsers() {
        return userRepository.findAll().stream()
                .map(ProfileDTO::from)
                .collect(Collectors.toList());
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
