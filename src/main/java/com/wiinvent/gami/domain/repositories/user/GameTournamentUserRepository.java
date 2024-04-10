package com.wiinvent.gami.domain.repositories.user;

import com.wiinvent.gami.domain.entities.user.GameTournamentUser;
import com.wiinvent.gami.domain.response.GameTournamentUserResponse;
import jdk.jfr.Registered;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GameTournamentUserRepository extends JpaRepository<GameTournamentUser, UUID>, JpaSpecificationExecutor<GameTournamentUser> {
  @Query(
      value = "SELECT new com.wiinvent.gami.domain.response.GameTournamentUserResponse(gu.userId, u.firstName, gu.point, gu.coin, gr.rewardItemInfo) "
          + "FROM GameTournamentUser gu "
          + "JOIN UserProfile u ON gu.userId = u.id "
          + "LEFT JOIN GameTournamentReward gr ON gu.userId = gr.userId "
          + "WHERE gu.tournamentEventId = :eventId "
          + "AND (:name IS NULL OR u.firstName LIKE :name%) "
          + "ORDER BY gu.coin DESC"
  )
  Page<GameTournamentUserResponse> getAllOrderByCoin(
      @Param("eventId") String eventId,
      @Param("name") String name,
      Pageable pageable
  );

  @Query(
      value = "SELECT new com.wiinvent.gami.domain.response.GameTournamentUserResponse(gu.userId, u.firstName, gu.point, gu.coin, gr.rewardItemInfo) "
          + "FROM GameTournamentUser gu "
          + "JOIN UserProfile u ON gu.userId = u.id "
          + "LEFT JOIN GameTournamentReward gr ON gu.userId = gr.userId "
          + "WHERE gu.tournamentEventId = :eventId "
          + "AND (:name IS NULL OR u.firstName LIKE :name%) "
          + "ORDER BY gu.point DESC "
  )
  Page<GameTournamentUserResponse> getAllOrderByPoint(
      @Param("eventId") String eventId,
      @Param("name") String name,
      Pageable pageable
  );
}