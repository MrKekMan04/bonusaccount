package ru.vitaliyefimov.bonusaccount.dto.balance;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record AccrueBonusesRequest(
    @Schema(description = "Идентификатор клиента", requiredMode = RequiredMode.REQUIRED)
    @NotBlank(message = "`clientId` не может быть пустым")
    String clientId,

    @Schema(description = "Сумма", requiredMode = RequiredMode.REQUIRED)
    @NotNull(message = "`amount` не может быть пустым")
    @Positive(message = "`amount` должен быть больше 0")
    BigDecimal amount
) {

}
