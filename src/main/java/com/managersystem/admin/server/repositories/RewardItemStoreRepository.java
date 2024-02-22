package com.managersystem.admin.server.repositories;

import com.managersystem.admin.server.entities.RewardItemStore;
import com.managersystem.admin.server.entities.type.StoreType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RewardItemStoreRepository extends JpaRepository<RewardItemStore, Long> {


  List<RewardItemStore> findByType(StoreType type);
}
