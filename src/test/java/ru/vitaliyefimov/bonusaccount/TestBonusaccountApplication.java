package ru.vitaliyefimov.bonusaccount;

import org.springframework.boot.SpringApplication;

public class TestBonusaccountApplication {

    public static void main(String[] args) {
        SpringApplication.from(BonusaccountApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
