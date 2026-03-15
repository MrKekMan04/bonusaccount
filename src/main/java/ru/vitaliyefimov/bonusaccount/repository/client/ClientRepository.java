package ru.vitaliyefimov.bonusaccount.repository.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vitaliyefimov.bonusaccount.entity.client.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {

}
