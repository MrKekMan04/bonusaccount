package ru.vitaliyefimov.bonusaccount.scheduler.withdrawrequest;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.vitaliyefimov.bonusaccount.service.withdrawrequest.WithdrawRequestService;

@Component
@RequiredArgsConstructor
public class SendWithdrawRequestJob {

    private final WithdrawRequestService withdrawRequestService;

    @Scheduled(cron = "${schedule.send-withdraw-request.cron}")
    public void sendWithdrawRequests() {
        withdrawRequestService.sendWithdrawRequests();
    }
}
