package kr.codesqaud.cafe.repository;

import java.util.List;

public interface Repository <T, ID> {

    List<T> findAll();

    T findById(Long id);


}
