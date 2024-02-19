package com.managersystem.admin.server.repositories;

import com.managersystem.admin.server.entities.RewardItemStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RewardItemStoreRepository extends JpaRepository<RewardItemStore, Long> {


}
