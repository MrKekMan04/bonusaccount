package ru.vitaliyefimov.bonusaccount.repository.client.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.vitaliyefimov.bonusaccount.entity.client.Client;
import ru.vitaliyefimov.bonusaccount.repository.client.CustomClientRepository;

import java.util.Collection;

@Repository
@RequiredArgsConstructor
public class CustomClientRepositoryImpl implements CustomClientRepository {

    private static final String UPSERT_SQL = """
        insert into client (client_id, tag_is_banned)
        values (:clientId, :tagIsBanned)
        on conflict (client_id) do update
        set tag_is_banned = :tagIsBanned
        """;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public void upsertAll(Collection<Client> clients) {
        namedParameterJdbcTemplate.batchUpdate(UPSERT_SQL, toSqlParams(clients));
    }

    private SqlParameterSource[] toSqlParams(Collection<Client> clients) {
        return clients.stream()
            .map(client -> new MapSqlParameterSource()
                .addValue("clientId", client.getClientId())
                .addValue("tagIsBanned", client.getTagIsBanned())
            )
            .toArray(SqlParameterSource[]::new);
    }
}
