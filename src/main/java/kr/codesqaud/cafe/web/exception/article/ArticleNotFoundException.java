package kr.codesqaud.cafe.web.exception.article;

import kr.codesqaud.cafe.web.exception.BaseException;
import kr.codesqaud.cafe.web.exception.BaseExceptionType;

public class ArticleNotFoundException extends BaseException {

    public ArticleNotFoundException(BaseExceptionType exceptionType) {
        super(exceptionType);
    }
}
