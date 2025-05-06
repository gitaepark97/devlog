package devlog.backend.domain;

import devlog.backend.support.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class ForbiddenException extends ApplicationException {

    public ForbiddenException() {
        super(HttpStatus.FORBIDDEN, "권한이 없습니다.");
    }

}
