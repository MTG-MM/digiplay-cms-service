package com.wiinvent.gami.domain.repositories.gvc;

import com.wiinvent.gami.domain.entities.gvc.GvcHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GcvHistoryRepository extends JpaRepository<GvcHistory, String> {
}