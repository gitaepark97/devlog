package devlog.backend.web;

import devlog.backend.application.AuthService;
import devlog.backend.application.dto.Token;
import devlog.backend.domain.Session;
import devlog.backend.web.request.LoginRequest;
import devlog.backend.web.request.RegisterRequest;
import devlog.backend.web.response.LoginResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
class AuthV1Controller {

    private static final String REFRESH_TOKEN_KEY = "refreshToken";

    private final AuthService authService;

    @PostMapping("/register")
    void register(@Valid @RequestBody RegisterRequest request) {
        authService.register(request.email(), request.password(), request.username());
    }

    @PostMapping("/login")
    ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        Token token = authService.login(request.email(), request.password());

        return ResponseEntity.status(HttpStatus.OK)
            .header(HttpHeaders.SET_COOKIE, setRefreshTokenCookie(token.refreshToken()))
            .body(LoginResponse.from(token));
    }

    @PostMapping("/token")
    ResponseEntity<LoginResponse> reissueToken(@CookieValue(REFRESH_TOKEN_KEY) String refreshToken) {
        Token token = authService.reissueToken(refreshToken);

        return ResponseEntity.status(HttpStatus.OK)
            .header(HttpHeaders.SET_COOKIE, setRefreshTokenCookie(token.refreshToken()))
            .body(LoginResponse.from(token));
    }

    @PostMapping("/logout")
    void logout(@AuthenticationPrincipal Long userId) {
        authService.logout(userId);
    }

    private String setRefreshTokenCookie(String refreshToken) {
        return ResponseCookie.from(REFRESH_TOKEN_KEY, refreshToken)
            .httpOnly(true)
            .secure(true)
            .sameSite("None")
            .path("/")
            .maxAge(Session.SESSION_DURATION)
            .build()
            .toString();
    }

}
