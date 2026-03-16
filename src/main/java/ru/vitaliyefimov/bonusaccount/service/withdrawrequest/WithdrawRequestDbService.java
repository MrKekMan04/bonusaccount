package ru.vitaliyefimov.bonusaccount.service.withdrawrequest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vitaliyefimov.bonusaccount.entity.withdrawrequest.WithdrawRequest;
import ru.vitaliyefimov.bonusaccount.entity.withdrawrequest.WithdrawRequestStatus;
import ru.vitaliyefimov.bonusaccount.repository.withdrawrequest.WithdrawRequestRepository;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WithdrawRequestDbService {

    private final WithdrawRequestRepository withdrawRequestRepository;

    @Transactional(readOnly = true)
    public Optional<WithdrawRequest> findById(UUID id) {
        return withdrawRequestRepository.findById(id);
    }

    @Transactional
    public void save(WithdrawRequest withdrawRequest) {
        withdrawRequestRepository.save(withdrawRequest);
    }

    @Transactional(readOnly = true)
    public List<WithdrawRequest> findAllByCreatedDateTimeAndStatus(
        Duration searchGap,
        WithdrawRequestStatus withdrawRequestStatus
    ) {
        return withdrawRequestRepository.findAllByCreatedDateTimeBetweenAndStatus(
            Instant.now().minus(searchGap),
            Instant.now(),
            withdrawRequestStatus
        );
    }

    @Transactional
    public void setSentById(UUID id) {
        withdrawRequestRepository.setSentById(id);
    }
}
