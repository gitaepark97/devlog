package devlog.backend.application;

import devlog.backend.domain.LoginInfoNotFound;
import devlog.backend.domain.UnauthorizedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static devlog.backend.application.fake.FakeIdProvider.EXAMPLE_ID;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LoginInfoWriterTest {

    private LoginInfoWriter loginInfoWriter;

    @BeforeEach
    void init() {
        TestContainer container = new TestContainer();
        loginInfoWriter = container.getLoginInfoWriter();
    }

    @Test
    void updatePassword_fail_loginInfoNotFound() {
        // given
        Long userId = EXAMPLE_ID;
        String oldPassword = "Qwer1234!";
        String newPassword = "qWer1234!";

        // when & then
        assertThrows(LoginInfoNotFound.class, () -> loginInfoWriter.updatePassword(userId, oldPassword, newPassword));
    }

    @Test
    void updatePassword_fail_wrongPassword() {
        // given
        Long userId = EXAMPLE_ID;
        String oldPassword = "QWer1234!";
        String newPassword = "qWer1234!";

        loginInfoWriter.create("test@example.com", "Qwer1234!", userId);

        // when & then
        assertThrows(UnauthorizedException.class, () -> loginInfoWriter.updatePassword(userId, oldPassword, newPassword));
    }

}