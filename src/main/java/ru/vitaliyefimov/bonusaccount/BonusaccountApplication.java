package ru.vitaliyefimov.bonusaccount;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BonusaccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(BonusaccountApplication.class, args);
    }

}
