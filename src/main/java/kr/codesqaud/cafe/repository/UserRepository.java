package kr.codesqaud.cafe.repository;
import kr.codesqaud.cafe.domain.User;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
@Repository
public class UserRepository {
    private List<User> dataBase;
    public UserRepository(ArrayList<User> users){
        this.dataBase = users;
    }

    public void save(User user){
        User newUser = user;
        dataBase.add(newUser);
    }
    public List<User> dataBase() {
        return dataBase;
    }
}
