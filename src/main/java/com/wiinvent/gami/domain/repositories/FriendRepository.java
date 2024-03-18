package com.wiinvent.gami.domain.repositories;

import com.wiinvent.gami.domain.entities.Friend;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FriendRepository extends JpaRepository<Friend, String> {

  Friend findByUserIdAndFriendId(UUID userId, UUID friendUserId);
}