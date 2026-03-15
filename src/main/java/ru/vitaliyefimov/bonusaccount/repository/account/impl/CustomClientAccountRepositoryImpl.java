package ru.vitaliyefimov.bonusaccount.repository.account.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.vitaliyefimov.bonusaccount.entity.account.ClientAccount;
import ru.vitaliyefimov.bonusaccount.repository.account.CustomClientAccountRepository;

import java.util.Collection;

@Repository
@RequiredArgsConstructor
public class CustomClientAccountRepositoryImpl implements CustomClientAccountRepository {

    private static final String UPSERT_SQL = """
        insert into client_account (client_id, account_number, loyalty_program_code)
        values (:clientId, :accountNumber, :loyaltyProgramCode)
        on conflict (client_id, account_number) do update
        set loyalty_program_code = :loyaltyProgramCode
        """;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public void upsertAll(Collection<ClientAccount> accounts) {
        namedParameterJdbcTemplate.batchUpdate(UPSERT_SQL, toSqlParams(accounts));
    }

    private SqlParameterSource[] toSqlParams(Collection<ClientAccount> accounts) {
        return accounts.stream()
            .map(account -> new MapSqlParameterSource()
                .addValue("clientId", account.getId().getClientId())
                .addValue("accountNumber", account.getId().getAccountNumber())
                .addValue("loyaltyProgramCode", account.getLoyaltyProgramCode())
            )
            .toArray(SqlParameterSource[]::new);
    }
}
