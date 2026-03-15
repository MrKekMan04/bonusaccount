package ru.vitaliyefimov.bonusaccount.repository.balance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vitaliyefimov.bonusaccount.entity.balance.Balance;

@Repository
public interface BalanceRepository extends JpaRepository<Balance, String> {

}
