package devlog.backend.application.fake;

import devlog.backend.application.TokenProvider;

import java.time.Duration;
import java.util.Map;

import static devlog.backend.application.fake.FakeIdProvider.EXAMPLE_ID;

public class FakeTokenProvider implements TokenProvider {

    public static String EXAMPLE_TOKEN = "token";

    @Override
    public String issueToken(Map<String, Object> claims, Duration duration) {
        return EXAMPLE_TOKEN;
    }

    @Override
    public Map<String, Object> getPayload(String token) {
        if (token.equals(EXAMPLE_TOKEN)) {
            return Map.of("userId", EXAMPLE_ID);
        }

        throw new IllegalArgumentException();
    }

}
