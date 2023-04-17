package com.sparta.spring_lv1_assignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing //timestamped
@SpringBootApplication
public class SpringLv1AssignmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringLv1AssignmentApplication.class, args);
    }

}
