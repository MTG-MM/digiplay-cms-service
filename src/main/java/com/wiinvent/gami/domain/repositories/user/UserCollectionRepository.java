package com.wiinvent.gami.domain.repositories.user;

import com.wiinvent.gami.domain.entities.user.UserCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface UserCollectionRepository extends JpaRepository<UserCollection, UUID>, JpaSpecificationExecutor<UserCollection> {
}
