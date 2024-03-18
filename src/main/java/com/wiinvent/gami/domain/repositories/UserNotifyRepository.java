package com.wiinvent.gami.domain.repositories;

import com.wiinvent.gami.domain.entities.UserNotify;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserNotifyRepository extends JpaRepository<UserNotify, String> {
}