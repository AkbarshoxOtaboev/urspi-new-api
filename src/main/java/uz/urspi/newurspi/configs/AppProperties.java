package uz.urspi.newurspi.configs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private Seed seed = new Seed();
    private Cors cors = new Cors();

    @Getter
    @Setter
    public static class Seed {
        private boolean enabled = true;
        private Admin admin = new Admin();
    }

    @Getter
    @Setter
    public static class Admin {
        private String username = "admin";
        private String password = "admin";
        private String fullName = "Super Admin";
        private String phone;
    }

    @Getter
    @Setter
    public static class Cors {
        private List<String> allowedOrigins = new ArrayList<>(List.of("http://localhost:5173"));
    }
}
