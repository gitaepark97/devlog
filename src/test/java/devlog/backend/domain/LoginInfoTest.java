package devlog.backend.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class LoginInfoTest {

    @Test
    void create_LoginInfo() {
        // given
        Long id = 1L;
        String email = "test@example.com";
        String password = "Qwer1234!";
        Long userId = 1L;
        Long currentTime = 1735689600000L;

        // when
        LoginInfo loginInfo = LoginInfo.create(id, email, password, userId, currentTime);

        // then
        assertThat(loginInfo.id()).isEqualTo(id);
        assertThat(loginInfo.loginMethod()).isEqualTo(LoginMethod.EMAIL);
        assertThat(loginInfo.loginKey()).isEqualTo(email);
        assertThat(loginInfo.password()).isEqualTo(password);
        assertThat(loginInfo.userId()).isEqualTo(userId);
        assertThat(loginInfo.createTime()).isEqualTo(currentTime);
    }

}