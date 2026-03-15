package ru.vitaliyefimov.bonusaccount.dto.balance;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;

import java.math.BigDecimal;

public record GetBalanceResponse(
    @Schema(description = "Баланс бонусного счета", requiredMode = RequiredMode.REQUIRED)
    BigDecimal balanceAmount,

    @Schema(description = "Всего выведено бонусов", requiredMode = RequiredMode.REQUIRED)
    BigDecimal totalWithdraw
) {

}
