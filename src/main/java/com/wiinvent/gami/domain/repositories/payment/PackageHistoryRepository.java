package com.wiinvent.gami.domain.repositories.payment;

import com.wiinvent.gami.domain.entities.payment.PackageHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PackageHistoryRepository extends JpaRepository<PackageHistory, String>
    , JpaSpecificationExecutor<PackageHistory> {
}