package devlog.backend.application;

public interface EncoderProvider {

    String encode(String origin);

    boolean matches(String origin, String encoded);

}
