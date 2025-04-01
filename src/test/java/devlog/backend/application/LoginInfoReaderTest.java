package devlog.backend.application;

import devlog.backend.domain.UnauthorizedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class LoginInfoReaderTest {

    private LoginInfoReader loginInfoReader;
    private LoginInfoWriter loginInfoWriter;

    @BeforeEach
    void init() {
        TestContainer container = new TestContainer();
        loginInfoWriter = container.getLoginInfoWriter();
        loginInfoReader = container.getLoginInfoReader();
    }

    @Test
    void read_fail_loginInfoNotFound() {
        // given
        String email = "test@example.com";
        String password = "Qwer1234!";

        // when & then
        assertThrows(UnauthorizedException.class, () -> loginInfoReader.read(email, password));
    }

    @Test
    void read_fail_wrongPassword() {
        // given
        String email = "test@example.com";
        String password = "Qwer1234!";
        loginInfoWriter.create(email, password, 1L);

        // when & then
        assertThrows(UnauthorizedException.class, () -> loginInfoReader.read(email, "qWer1234!"));
    }

}