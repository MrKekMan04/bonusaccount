package ru.vitaliyefimov.bonusaccount.service.card;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vitaliyefimov.bonusaccount.dto.card.CardEvent;
import ru.vitaliyefimov.bonusaccount.service.balance.BalanceService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CardEventProcessor {

    private static final String BLACK_LOYALTY_PROGRAM_CODE = "Black";
    private static final String SUCCESS_EVENT = "success";

    private final BalanceService balanceService;

    public void process(List<CardEvent> cardEvents) {
        cardEvents.stream()
            .filter(event ->
                BLACK_LOYALTY_PROGRAM_CODE.equals(event.loyaltyProgramCode())
                    && SUCCESS_EVENT.equals(event.event())
            )
            .forEach(balanceService::hold);
    }
}
