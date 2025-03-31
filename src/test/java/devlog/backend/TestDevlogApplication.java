package devlog.backend;

import org.springframework.boot.SpringApplication;

public class TestDevlogApplication {

    public static void main(String[] args) {
        SpringApplication.from(DevlogApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
