package ru.ftc.library.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("ru.ftc.library.api.jpa")
@EntityScan("ru.ftc.library.api.jpa")
@ComponentScan("ru.ftc.library.api")
@SpringBootApplication
public class LibraryApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryApiApplication.class, args);
    }

}
