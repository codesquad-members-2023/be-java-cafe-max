package kr.codesqaud.cafe.db;

import java.util.*;

public class DB {
    private static DB instance;
    private Map<String, Map<Long, ArrayList>> dataBase = new HashMap<>();
    private DB() {}

    public static final DB getInstance() {
        if (instance == null) {
            instance = new DB();
        }
        return instance;
    }

    public final Map<Long, ArrayList> read(String tableName) {
        return dataBase.get(tableName);
    }

    public final <T> void create(T t) {
        String tableName = t.getClass().getSimpleName();
        Map<Long, ArrayList> db = dataBase.get(tableName);

        if (dataBase.containsKey(tableName)) {
            Long id = (Long) db.keySet().toArray()[db.size() - 1] + 1;

        } else {
            ArrayList<T> list = new ArrayList<>();
            list.add(t);
            dataBase.put(tableName, list);
        }
    }
}
