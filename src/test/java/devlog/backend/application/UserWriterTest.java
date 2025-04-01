package devlog.backend.application;

import devlog.backend.domain.EmailAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class UserWriterTest {

    private UserWriter userWriter;

    @BeforeEach
    void init() {
        TestContainer container = new TestContainer();
        userWriter = container.getUserWriter();
    }

    @Test
    void create_fail_emailAlreadyUsed() {
        // given
        String email = "test@example.com";
        String username = "test";
        userWriter.create(email, username);

        // when & then
        assertThrows(EmailAlreadyExistsException.class, () -> userWriter.create(email, username));
    }

}