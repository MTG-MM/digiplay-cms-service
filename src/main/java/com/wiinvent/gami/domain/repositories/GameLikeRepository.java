package com.wiinvent.gami.domain.repositories;

import com.wiinvent.gami.domain.entities.game.GameLike;
import com.wiinvent.gami.domain.entities.type.LikeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GameLikeRepository extends JpaRepository<GameLike, Integer> {
  GameLike findByGameIdAndUserId(Integer gameId, UUID userId);

  @Query(value = "UPDATE GameLike SET likeStatus = :likeStatus WHERE id IN :likeIds ")
  @Modifying
  void updateUserLikeStatus(
      @Param("likeIds") List<String> gameLikeIds,
      @Param("likeStatus") LikeStatus likeStatus);

  List<GameLike> findByIdIn(List<String> ids);
}