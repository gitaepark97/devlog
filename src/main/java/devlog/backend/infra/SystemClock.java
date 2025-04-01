package devlog.backend.infra;

import devlog.backend.application.ClockProvider;
import org.springframework.stereotype.Component;

import java.time.Clock;

@Component
class SystemClock implements ClockProvider {

    @Override
    public Long millis() {
        return Clock.systemUTC().millis();
    }

}
