package com.wiinvent.gami.domain.repositories.user;

import com.wiinvent.gami.domain.entities.user.User;
import com.wiinvent.gami.domain.entities.user.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, UUID> {
  List<UserProfile> findAllByIdIn(List<UUID> ids);

  List<UserProfile> findByDisplayName(String displayName);

  UserProfile findByPhoneNumber(String phoneNumber);
}
