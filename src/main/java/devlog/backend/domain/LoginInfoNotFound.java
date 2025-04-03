package devlog.backend.domain;

import devlog.backend.support.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class LoginInfoNotFound extends ApplicationException {

    public LoginInfoNotFound() {
        super(HttpStatus.NOT_FOUND, "로그인 정보를 찾을 수 없습니다.");
    }

}
