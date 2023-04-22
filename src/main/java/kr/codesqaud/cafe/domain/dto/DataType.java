package kr.codesqaud.cafe.domain.dto;

public enum DataType {
    USERS("USERS"),
    ARTICLES("ARTICLES"),
    REPLIES("REPLIES");

    private final String type;

    DataType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
