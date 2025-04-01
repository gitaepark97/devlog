package devlog.backend.web.response;

import devlog.backend.application.dto.Token;

public record LoginResponse(
    String token
) {

    public static LoginResponse from(Token token) {
        return new LoginResponse(token.accessToken());
    }

}
