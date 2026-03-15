package ru.vitaliyefimov.bonusaccount;

import org.springframework.boot.SpringApplication;

public class TestBonusaccountApplication {

    static void main(String[] args) {
        SpringApplication.from(BonusaccountApplication::main)
            .with(TestcontainersConfiguration.class)
            .run(args);
    }
}
