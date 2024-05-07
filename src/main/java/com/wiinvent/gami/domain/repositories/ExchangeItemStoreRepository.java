package com.wiinvent.gami.domain.repositories;

import com.wiinvent.gami.domain.entities.ExchangeItemStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeItemStoreRepository extends JpaRepository<ExchangeItemStore, Long>,
    JpaSpecificationExecutor<ExchangeItemStore> {
}
