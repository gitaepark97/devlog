package devlog.backend.infra;

import devlog.backend.application.TokenProvider;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JWTTest {

    private final TokenProvider tokenProvider = new JWT("d0e5344755b504e561cc3da9a74082e26c8b7");

    @Test
    void issueToken_success() {
        // given
        Map<String, Object> payload = Map.of("userId", "1");
        Duration duration = Duration.ofSeconds(30);

        // when
        String token = tokenProvider.issueToken(payload, duration);

        // then
        assertThat(token).isInstanceOf(String.class);
    }

    @Test
    void getPayload_success() {
        // given
        Map<String, Object> payload1 = Map.of("userId", "1");
        Duration duration = Duration.ofSeconds(30);
        String token = tokenProvider.issueToken(payload1, duration);

        // when
        Map<String, Object> payload2 = tokenProvider.getPayload(token);

        // then
        assertThat(payload2.get("userId")).isEqualTo(payload1.get("userId"));
    }

    @Test
    void getPayload_fail_tokenInvalid() {
        // given
        String token = "token";

        // when & then
        assertThrows(Exception.class, () -> tokenProvider.getPayload(token));
    }

    @Test
    void getPayload_fail_tokeExpired() {
        // given
        Map<String, Object> payload1 = Map.of("userId", "1");
        Duration duration = Duration.ofSeconds(-10);
        String token = tokenProvider.issueToken(payload1, duration);

        // when & then
        assertThrows(Exception.class, () -> tokenProvider.getPayload(token));
    }

}