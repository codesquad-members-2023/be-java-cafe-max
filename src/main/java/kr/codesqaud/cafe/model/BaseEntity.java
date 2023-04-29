package kr.codesqaud.cafe.model;

public class BaseEntity {
    private Long id;

    public Long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public Object getColumn(String column) {
        return null;
    }
}
