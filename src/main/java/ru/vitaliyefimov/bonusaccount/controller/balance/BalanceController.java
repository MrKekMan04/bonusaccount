package ru.vitaliyefimov.bonusaccount.controller.balance;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.vitaliyefimov.bonusaccount.dto.balance.AccrueBonusesRequest;
import ru.vitaliyefimov.bonusaccount.dto.balance.GetBalanceResponse;
import ru.vitaliyefimov.bonusaccount.dto.http.Response;

@Tag(name = "BalanceController")
@Validated
public interface BalanceController {

    @PostMapping("/api/v1/balance")
    @Operation(summary = "Провести начисление")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Бонус успешно начислен"),
        @ApiResponse(responseCode = "400", description = """
            Ошибка валидации:
                - clientId - обязательный непустой параметр
                - amount - обязательный непустой положительный параметр
            """),
        @ApiResponse(responseCode = "422", description = """
            Ошибка данных:
                - клиент забанен
                - клиент тяжелый
            """)
    })
    Response<Void> accrueBonuses(
        @Valid @RequestBody AccrueBonusesRequest request
    );

    @GetMapping("/api/v1/balance/{clientId}")
    @Operation(summary = "Получить баланс бонусного счета")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Баланс успешно получен"),
        @ApiResponse(responseCode = "400", description = """
            Ошибка валидации:
                - clientId - обазательный непустой параметр
            """),
        @ApiResponse(responseCode = "422", description = """
            Ошбика данных:
                - у клиента нет бонусного счета
            """)
    })
    Response<GetBalanceResponse> getBalance(
        @Parameter(description = "Идентификатор клиента")
        @NotBlank(message = "`clientId` не может быть пустым")
        @PathVariable String clientId
    );
}
