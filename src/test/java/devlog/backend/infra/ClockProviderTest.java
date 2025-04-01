package devlog.backend.infra;

import devlog.backend.application.ClockProvider;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ClockProviderTest {

    private final ClockProvider clockProvider = new SystemClock();

    @Test
    void millis_shouldReturnCurrentTime() {
        // given

        // when
        Long currentTime1 = clockProvider.millis();
        Long currentTime2 = clockProvider.millis();

        // then
        assertThat(currentTime2).isGreaterThanOrEqualTo(currentTime1);
    }

}