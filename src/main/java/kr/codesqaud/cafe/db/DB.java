package kr.codesqaud.cafe.db;

import kr.codesqaud.cafe.model.*;

import java.util.*;

public class DB {
    private static DB instance;

    public Map<TableName, Map<Long, ?>> data = new HashMap<>();
    private Map<TableName, Map<String, String>> metaData = new HashMap<>();

    // 생성자를 private 으로 생성
    private DB() {
        // 테이블 생성
        data.put(TableName.USER, new HashMap<Long, User>());

        // 테이블 메타데이터 생성
        Map<String, String> initUserMeta = new HashMap<>();
        initUserMeta.put("maxId", "0");
        initUserMeta.put("total", "0");

        metaData.put(TableName.USER, initUserMeta);

    }

    public static final DB getInstance() {
        // 최초 1회만 생성
        if (instance == null) {
            instance = new DB();
        }
        // 그 외엔 생성된 객체 사용
        return instance;
    }

    // crud 에서는 read 지만 보통 find 라고 쓰는 듯
    // generic 사용이 어려움 물음표는 리턴에서 안 된다고 함
    // 타입을 생략하는 건??
    public Map getTableMap(TableName tableName) {
        return data.get(tableName);
    }

    // 밸류만 리스트로 바꾸는 게 리스트를 탐색하는 것 보다는 비교 연산이 없으니 낫지 않을까?
    // DB 에 ArrayList 로 저장하는 게 나을까?
    // 그러면 id 를 어떻게 관리하지?
    // 제네릭이 맞는지 모르겠음
    public ArrayList getTableList(TableName tableName) {
        return new ArrayList<>(getTableMap(tableName).values());
    }


    // id 는 null 인 객체를 받아서 maxId 로 추가
    // 여기서 객체의 id 를 설정하기 위해 id 를 가진 상위 엔티티를 만들어야겠다 느낌
    public <T> void create(T t, TableName tableName) {
        Map<Long, T> table = (Map<Long, T>) data.get(tableName);
        BaseEntity obj = (BaseEntity) t;
        long maxId = Long.parseLong(metaData.get(tableName).get("maxId"));
        long total = Long.parseLong(metaData.get(tableName).get("total"));

        obj.setId(maxId + 1);
        table.put(maxId + 1, (T) obj);

        metaData.get(tableName).put("maxId", String.valueOf(maxId + 1));
        metaData.get(tableName).put("total", String.valueOf(total + 1));

    }
}
