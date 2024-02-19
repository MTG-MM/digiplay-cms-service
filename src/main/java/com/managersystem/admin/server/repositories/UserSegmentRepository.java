package com.managersystem.admin.server.repositories;

import com.managersystem.admin.server.entities.User;
import com.managersystem.admin.server.entities.UserSegment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserSegmentRepository extends JpaRepository<UserSegment, Long> {

  List<UserSegment> findByIsDefault(Boolean isDefault);
}
