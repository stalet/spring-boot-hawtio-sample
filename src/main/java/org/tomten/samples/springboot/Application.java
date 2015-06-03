package org.tomten.samples.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class Application {

    /**
     * Application main.
     *
     * @param args application arguments.
     */
    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }

}