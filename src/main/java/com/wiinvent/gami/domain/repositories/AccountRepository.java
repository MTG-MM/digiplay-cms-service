package com.wiinvent.gami.domain.repositories;

import com.wiinvent.gami.domain.entities.Account;
import com.wiinvent.gami.domain.entities.type.AccountRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
  Account findByUsername(String username);

  Account findByUsernameAndRole(String username, AccountRole accountRole);


}
