package com.managersystem.admin.server.repositories;

import com.managersystem.admin.server.entities.Account;
import com.managersystem.admin.server.entities.type.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
  Account findByUsername(String username);

  Account findByUsernameAndRole(String username, UserRole userRole);


}
