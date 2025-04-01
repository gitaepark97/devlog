package devlog.backend.domain;

import devlog.backend.support.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class UnauthorizedException extends ApplicationException {

    public UnauthorizedException() {
        super(HttpStatus.UNAUTHORIZED, "인증에 실패하였습니다.");
    }

}
