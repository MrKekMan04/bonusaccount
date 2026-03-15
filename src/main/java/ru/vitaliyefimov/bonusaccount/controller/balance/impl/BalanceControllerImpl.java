package ru.vitaliyefimov.bonusaccount.controller.balance.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.vitaliyefimov.bonusaccount.controller.balance.BalanceController;
import ru.vitaliyefimov.bonusaccount.dto.balance.AccrueBonusesRequest;
import ru.vitaliyefimov.bonusaccount.dto.balance.GetBalanceResponse;
import ru.vitaliyefimov.bonusaccount.dto.http.Response;
import ru.vitaliyefimov.bonusaccount.service.balance.BalanceService;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
public class BalanceControllerImpl implements BalanceController {

    private final BalanceService balanceService;

    @Override
    public Response<Void> accrueBonuses(AccrueBonusesRequest request) {
        return Response.success();
    }

    @Override
    public Response<GetBalanceResponse> getBalance(String clientId) {
        return Response.success(new GetBalanceResponse(BigDecimal.ZERO, BigDecimal.ZERO));
    }
}
