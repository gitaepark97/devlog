package devlog.backend.application.fake;

import devlog.backend.application.EncoderProvider;

public class FakeEncoderProvider implements EncoderProvider {

    @Override
    public String encode(String origin) {
        return "encoded" + origin;
    }

    @Override
    public boolean matches(String origin, String encoded) {
        return encoded.equals(encode(origin));
    }

}
