package devlog.backend.domain;

import devlog.backend.support.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class ArticleNotFound extends ApplicationException {

    public ArticleNotFound() {
        super(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다.");
    }

}
