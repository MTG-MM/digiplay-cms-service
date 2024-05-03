package com.wiinvent.gami.domain.repositories;

import com.wiinvent.gami.domain.entities.ChallengeDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ChallengeDetailRepository extends JpaRepository<ChallengeDetail, Integer>,
    JpaSpecificationExecutor<ChallengeDetail> {
}
