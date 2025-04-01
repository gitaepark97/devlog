package devlog.backend.infra;

import devlog.backend.application.TokenProvider;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class JWTTest {

    private final TokenProvider tokenProvider = new JWT("d0e5344755b504e561cc3da9a74082e26c8b7");

    @Test
    void issueToken_success() {
        // given
        Map<String, Object> claims = Map.of("userId", "1");
        Duration duration = Duration.ofSeconds(30);

        // when
        String token = tokenProvider.issueToken(claims, duration);

        // then
        assertThat(token).isInstanceOf(String.class);
    }

}