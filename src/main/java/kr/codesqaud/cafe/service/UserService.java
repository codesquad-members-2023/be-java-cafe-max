package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.MemoryUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final MemoryUserRepository memoryUserRepository;

    @Autowired
    public UserService(MemoryUserRepository memoryUserRepository){
        this.memoryUserRepository = memoryUserRepository;
    }
    public User join(User user){
        memoryUserRepository.save(user);
        return user;
    }

    public List<User> fineUsers(){
        return memoryUserRepository.findAll();
    }
}
