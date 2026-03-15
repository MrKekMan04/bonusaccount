package ru.vitaliyefimov.bonusaccount.repository.withdrawrequest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vitaliyefimov.bonusaccount.entity.withdrawrequest.WithdrawRequest;

import java.util.UUID;

@Repository
public interface WithdrawRequestRepository extends JpaRepository<WithdrawRequest, UUID> {

}
