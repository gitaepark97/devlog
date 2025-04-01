package devlog.backend.application.fake;

import devlog.backend.application.TokenProvider;

import java.time.Duration;
import java.util.Map;

public class FakeTokenProvider implements TokenProvider {

    public static String EXAMPLE_TOKEN = "token";

    @Override
    public String issueToken(Map<String, Object> claims, Duration duration) {
        return EXAMPLE_TOKEN;
    }

}
