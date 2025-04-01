package devlog.backend.infra;

import devlog.backend.application.EncoderProvider;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class EncoderProviderTest {

    private final EncoderProvider encoderProvider = new BcryptEncoder();

    @Test
    void encode_data() {
        // given
        String origin = "origin";

        // when
        String encoded = encoderProvider.encode(origin);

        // then
        assertThat(encoded).isNotEqualTo(origin);
    }

}