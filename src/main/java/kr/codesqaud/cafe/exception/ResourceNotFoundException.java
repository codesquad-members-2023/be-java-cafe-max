package kr.codesqaud.cafe.exception;

import org.springframework.dao.DataRetrievalFailureException;

public class ResourceNotFoundException extends DataRetrievalFailureException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
