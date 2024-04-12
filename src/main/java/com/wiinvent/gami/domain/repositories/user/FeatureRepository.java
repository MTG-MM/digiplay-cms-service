package com.wiinvent.gami.domain.repositories.user;

import com.wiinvent.gami.domain.entities.user.Feature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeatureRepository extends JpaRepository<Feature, Integer> {
}