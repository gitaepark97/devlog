package devlog.backend.infra;

import devlog.backend.application.IdProvider;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class IdProviderTest {

    private final IdProvider encoderProvider = new Snowflake();

    @Test
    void nextId_shouldReturnUniqueId() {
        // given

        // when
        Long id1 = encoderProvider.nextId();
        Long id2 = encoderProvider.nextId();

        // then
        assertThat(id2).isNotEqualTo(id1);
        assertThat(id2).isGreaterThan(id1);
    }

}