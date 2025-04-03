package devlog.backend.domain;

import devlog.backend.support.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends ApplicationException {

    public UserNotFoundException() {
        super(HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다.");
    }

}
