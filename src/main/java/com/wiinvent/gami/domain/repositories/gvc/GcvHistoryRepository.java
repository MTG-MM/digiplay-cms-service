package com.wiinvent.gami.domain.repositories.gvc;

import com.wiinvent.gami.domain.entities.gvc.GcvHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GcvHistoryRepository extends JpaRepository<GcvHistory, String> {
}