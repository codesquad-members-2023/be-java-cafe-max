package kr.codesqaud.cafe.controller.dto;

public class Pageable {
    private int startId;

    private int size;

    public Pageable(int startId, int size) {
        this.startId = startId;
        this.size = size;
    }

    public int getStartId() {
        return startId;
    }

    public int getSize() {
        return size;
    }

    public static Pageable of(int startId, int size) {
        return new Pageable(startId, size);
    }
}
