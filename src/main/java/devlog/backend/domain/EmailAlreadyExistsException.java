package devlog.backend.domain;

import devlog.backend.support.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class EmailAlreadyExistsException extends ApplicationException {

    public EmailAlreadyExistsException() {
        super(HttpStatus.CONFLICT, "이미 사용 중인 이메일입니다.");
    }

}
