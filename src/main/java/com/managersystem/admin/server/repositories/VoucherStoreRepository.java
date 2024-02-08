package com.managersystem.admin.server.repositories;

import com.managersystem.admin.server.entities.RewardState;
import com.managersystem.admin.server.entities.VoucherStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoucherStoreRepository extends JpaRepository<VoucherStore, Long> {


}
