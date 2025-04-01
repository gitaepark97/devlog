package devlog.backend.application;

import java.time.Duration;
import java.util.Map;

public interface TokenProvider {

    String issueToken(Map<String, Object> claims, Duration duration);

}
