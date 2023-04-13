package kr.codesqaud.cafe.repository;
import kr.codesqaud.cafe.domain.User;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
@Repository
public class UserRepository {
    private List<User> dataBase;
    public UserRepository(){
        this.dataBase = new ArrayList<>();
    }

    public void save(User user){
        User newUser = user;
        dataBase.add(newUser);
    }
    public List<User> getDataBase() {
        return dataBase;
    }
}
