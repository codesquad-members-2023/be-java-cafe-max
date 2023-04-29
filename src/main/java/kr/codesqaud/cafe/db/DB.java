package kr.codesqaud.cafe.db;

import kr.codesqaud.cafe.model.Article;
import kr.codesqaud.cafe.model.Reply;
import kr.codesqaud.cafe.model.User;

import java.util.*;

public class DB {
    private static DB instance;

    // Map<테이블명, Map<id, 객체>> 랑 고민
    // ArrayList<User> 와 같이 객체 지정하려면 어떻게? (리턴할 때 애매)
    // 찾기 편하게 Map 으로 -> Map<Long, Map<String, String>>
    // 근데 가장 많이 쓰는 find 에서 리스트로 매번 변환하는 게 비효율적인 것 같아서
    // 다시 List 로 바꿀 예정
    private Map<Long, Map<String, String>> user = new HashMap<>();
    private Map<Long, Map<String, String>> article = new HashMap<>();
    private Map<Long, Map<String, String>> reply = new HashMap<>();

    // 생성자를 private 으로 생성
    private DB() {}

    public static final DB getInstance() {
        // 최초 1회만 생성
        if (instance == null) {
            instance = new DB();
        }
        // 그 외엔 생성된 객체 사용
        return instance;
    }

    // generic 사용?
    // crud 에서는 read 지만 보통 find 라고 쓰는 듯
    public final Map<Long,Map<String, String>> getMaps(String tableName) {
        switch (tableName) {
            case "User":
                return user;
            case "Article":
                return article;
            case "Comment":
                return reply;
            default:
                return null;
        }
    }

    // 차라리 DB 에 ArrayList 로 저장하는 게 나을 것 같은데
    public <T> List<T> findAll(String tableName) {
        switch (tableName) {
            case "User":
                List<User> userList = new ArrayList<>();
                for (Map<String, String> userMap : user.values()) {
                    User userDto = new User(
                            Long.parseLong(userMap.get("id")),
                            userMap.get("email"),
                            userMap.get("nickname"),
                            userMap.get("password")
                    );
                    userList.add(userDto);
                }
                return (List<T>) userList;
            case "Article":
                break;
            case "Reply":
                break;
        }
        return null;
    }

    public final <T> void create(T t, String tableName) {
        switch (tableName) {
            case "User":
                User newUser = (User) t;
                long maxId = this.user.keySet().stream().max(Long::compareTo).orElse(0L);
                newUser.setId(maxId + 1);
                Map<String, String> userMap = newUser.toMap();
                user.put(maxId + 1, userMap);
                break;
            case "Article":
                break;
            case "Reply":
                break;
            default:
                break;
        }
    }
}
