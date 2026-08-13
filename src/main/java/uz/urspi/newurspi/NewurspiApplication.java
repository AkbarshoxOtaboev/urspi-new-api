package uz.urspi.newurspi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class NewurspiApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewurspiApplication.class, args);
    }

}
