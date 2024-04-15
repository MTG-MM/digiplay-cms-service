package com.wiinvent.gami.domain.repositories;

import com.wiinvent.gami.domain.entities.Banner;
import com.wiinvent.gami.domain.entities.type.BannerType;
import com.wiinvent.gami.domain.entities.type.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BannerRepository extends JpaRepository<Banner, Integer> {
  Page<Banner> findBannersByTypeAndStatusIn(BannerType type, List<Status> status, Pageable pageable);
  Banner findBannerByIdAndStatusIn(Integer id, List<Status> status);
}