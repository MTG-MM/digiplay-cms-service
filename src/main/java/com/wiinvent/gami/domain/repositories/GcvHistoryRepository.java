package com.wiinvent.gami.domain.repositories;

import com.wiinvent.gami.domain.entities.GcvHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GcvHistoryRepository extends JpaRepository<GcvHistory, String> {
}