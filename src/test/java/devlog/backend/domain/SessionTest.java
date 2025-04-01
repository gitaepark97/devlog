package devlog.backend.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class SessionTest {

    @Test
    void checkBlocked_fail_isBlocked() {
        // given
        Session session = Session.builder()
            .id(1L)
            .userId(1L)
            .token("token")
            .isBlock(true)
            .expireTime(234L)
            .createTime(123L)
            .build();

        // when & then
        assertThrows(UnauthorizedException.class, session::checkBlocked);
    }

    @Test
    void checkExpired_fail_isExpired() {
        // given
        Session session = Session.builder()
            .id(1L)
            .userId(1L)
            .token("token")
            .isBlock(true)
            .expireTime(234L)
            .createTime(123L)
            .build();

        // when & then
        assertThrows(UnauthorizedException.class, () -> session.checkExpired(345L));
    }

}