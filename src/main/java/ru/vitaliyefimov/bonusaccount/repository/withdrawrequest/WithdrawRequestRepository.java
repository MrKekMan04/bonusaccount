package ru.vitaliyefimov.bonusaccount.repository.withdrawrequest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.vitaliyefimov.bonusaccount.entity.withdrawrequest.WithdrawRequest;
import ru.vitaliyefimov.bonusaccount.entity.withdrawrequest.WithdrawRequestStatus;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface WithdrawRequestRepository extends JpaRepository<WithdrawRequest, UUID> {

    List<WithdrawRequest> findAllByCreatedDateTimeBetweenAndStatus(
        Instant createdDateTimeFrom,
        Instant createdDateTimeTo,
        WithdrawRequestStatus status
    );

    @Modifying
    @Query("""
        update WithdrawRequest wr
        set wr.status = 'SENT'
        where wr.id = :id and wr.status = 'NEW'
        """)
    void setSentById(UUID id);
}
