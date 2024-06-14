package com.wiinvent.gami.domain.repositories.leaderboard;

import com.wiinvent.gami.domain.entities.leaderboard.Leaderboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface LeaderboardRepository extends JpaRepository<Leaderboard, UUID>, JpaSpecificationExecutor<Leaderboard> {

}