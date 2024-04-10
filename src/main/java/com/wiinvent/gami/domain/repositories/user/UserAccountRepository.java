package com.wiinvent.gami.domain.repositories.user;

import com.wiinvent.gami.domain.entities.user.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, UUID> {
  List<UserAccount> findUserAccountsByIdIn(List<UUID> ids);
}