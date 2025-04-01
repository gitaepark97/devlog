package devlog.backend.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class TokenProcessorTest {

    private TokenProcessor tokenProcessor;

    @BeforeEach
    void init() {
        TestContainer container = new TestContainer();
        tokenProcessor = container.getTokenProcessor();
    }

    @Test
    void getUserId_success_empty() {
        // given
        String token = "invalid_token";

        // when
        Optional<Long> userId = tokenProcessor.getUserId(token);

        // then
        assertThat(userId).isEmpty();
    }

}