package devlog.backend.application;

import devlog.backend.application.dto.Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static devlog.backend.application.fake.FakeIdProvider.EXAMPLE_ID;
import static devlog.backend.application.fake.FakeTokenProvider.EXAMPLE_TOKEN;
import static devlog.backend.application.fake.FakeUUIDProvider.EXAMPLE_UUID;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class AuthServiceTest {

    private AuthService authService;

    @BeforeEach
    void init() {
        TestContainer container = new TestContainer();
        authService = container.getAuthService();
    }

    @Test
    void register_success() {
        // given
        String email = "test@example.com";
        String password = "Qwer1234!";
        String username = "test";

        // when
        authService.register(email, password, username);
    }

    @Test
    void login_success() {
        // given
        String email = "test@example.com";
        String password = "Qwer1234!";
        authService.register(email, password, "test");

        // when
        Token token = authService.login(email, password);

        // then
        assertThat(token.accessToken()).isEqualTo(EXAMPLE_TOKEN);
        assertThat(token.refreshToken()).isEqualTo(EXAMPLE_UUID);
    }

    @Test
    void reissue_success() {
        // given
        String email = "test@example.com";
        String password = "Qwer1234!";
        authService.register(email, password, "test");
        String refreshToken = authService.login(email, password).refreshToken();

        // when
        Token token = authService.reissueToken(refreshToken);

        // then
        assertThat(token.accessToken()).isEqualTo(EXAMPLE_TOKEN);
        assertThat(token.refreshToken()).isEqualTo(refreshToken);
    }

    @Test
    void logout_success() {
        // given
        String email = "test@example.com";
        String password = "Qwer1234!";
        authService.register(email, password, "test");

        // when
        authService.logout(EXAMPLE_ID);

        // then
    }

}