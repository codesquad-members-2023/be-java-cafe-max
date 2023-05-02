package kr.codesqaud.cafe.model;

public enum TableName {
    USER("User"),
    ARTICLE("Article"),
    COMMENT("Comment");

    private String tableName;

    TableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }
}
