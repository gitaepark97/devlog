package devlog.backend.infra;

import devlog.backend.application.UUIDProvider;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class SystemUUIDTest {

    private final UUIDProvider uuidProvider = new SystemUUID();

    @Test
    void random_success() {
        // given

        // when
        String uuid1 = uuidProvider.random();
        String uuid2 = uuidProvider.random();

        // then
        assertThat(uuid1).isNotEqualTo(uuid2);

    }

}