package com.wiinvent.gami.domain.repositories.user;

import com.wiinvent.gami.domain.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {
  Integer countUserByCreatedAtBetween(long start, long end);

  Integer countUserByLastLoginBetween(long start, long end);
}
