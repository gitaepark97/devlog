package devlog.backend.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class UserTest {

    @Test
    void create_user() {
        // given
        Long id = 1L;
        String email = "test@example.com";
        String username = "test";
        Long currentTime = 1735689600000L;

        // given
        User user = User.create(id, email, username, currentTime);

        // then
        assertThat(user.id()).isEqualTo(id);
        assertThat(user.email()).isEqualTo(email);
        assertThat(user.username()).isEqualTo(username);
        assertThat(user.createTime()).isEqualTo(currentTime);
        assertThat(user.updateTime()).isEqualTo(currentTime);
    }

}