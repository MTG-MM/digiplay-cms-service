package com.wiinvent.gami.domain.repositories;

import com.wiinvent.gami.domain.entities.reward.RewardItemStore;
import com.wiinvent.gami.domain.entities.type.StoreType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RewardItemStoreRepository extends JpaRepository<RewardItemStore, Long> {


  List<RewardItemStore> findByType(StoreType type);
}
