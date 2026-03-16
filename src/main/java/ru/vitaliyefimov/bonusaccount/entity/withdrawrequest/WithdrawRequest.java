package ru.vitaliyefimov.bonusaccount.entity.withdrawrequest;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "withdraw_request")
@EntityListeners(AuditingEntityListener.class)
public class WithdrawRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String clientId;

    @Enumerated(EnumType.STRING)
    private WithdrawRequestStatus status;

    private BigDecimal amount;

    private String accountNumber;

    @CreatedDate
    @Column(name = "created_dttm", updatable = false)
    private Instant createdDateTime;
}
