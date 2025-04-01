package devlog.backend.infra;

import devlog.backend.application.EncoderProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
class BcryptEncoder implements EncoderProvider {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public String encode(String origin) {
        return passwordEncoder.encode(origin);
    }

    @Override
    public boolean matches(String origin, String encoded) {
        return passwordEncoder.matches(origin, encoded);
    }

}
