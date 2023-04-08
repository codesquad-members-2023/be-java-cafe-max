package kr.codesqaud.cafe.dto;
// 참고 https://junyharang.tistory.com/81
// 그룹리뷰 질문:
// @Autowired, DI(의존성 주입) <<< 이게 1단계에서 쓰인 것 감ㅈ는지?
public class User {
    private static int id = 0;
    private String password;

    public User(String id, String password)
    {   this.id += 1;
        this.password = password;
    }

    public void userIdSetter(int id){
        this.id=id;
    }
    public void userPasswordSetter
            (String password){
     this.password=password;
    }

//    @Override
//    public String toString() {
//        return "User{" +
//                "password='" + password + '\'' +
//                '}';
//    }
}
























