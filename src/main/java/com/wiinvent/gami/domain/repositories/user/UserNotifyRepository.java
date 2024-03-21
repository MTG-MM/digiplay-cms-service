package com.wiinvent.gami.domain.repositories.user;

import com.wiinvent.gami.domain.entities.user.UserNotify;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserNotifyRepository extends JpaRepository<UserNotify, String> {
}