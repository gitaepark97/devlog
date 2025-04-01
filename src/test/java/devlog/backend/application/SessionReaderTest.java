package devlog.backend.application;

import devlog.backend.domain.UnauthorizedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class SessionReaderTest {

    private SessionReader sessionReader;

    @BeforeEach
    void init() {
        TestContainer container = new TestContainer();
        sessionReader = container.getSessionReader();
    }

    @Test
    void read_fail_sessionNotFound() {
        // given
        String token = "token";

        // when & then
        assertThrows(UnauthorizedException.class, () -> sessionReader.read(token));
    }

}