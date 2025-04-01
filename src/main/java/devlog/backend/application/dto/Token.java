package devlog.backend.application.dto;

public record Token(
    String accessToken,
    String refreshToken
) {
    
}
