package com.wiinvent.gami.domain.repositories.payment;

import com.wiinvent.gami.domain.entities.payment.PackageHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PackageHistoryRepository extends JpaRepository<PackageHistory, UUID>
    , JpaSpecificationExecutor<PackageHistory> {
  PackageHistory findPackageHistoryById(UUID id);

  @Query(
      nativeQuery = true,
      value = "select sum(ph.price) from package_history ph " +
          "where ph.created_at >= :timeStartAt " +
          "and ph.created_at <= :timeEndAt "
  )
  Long countTotalRevenue (
      @Param("timeStartAt") long timeStartAt,
      @Param("timeEndAt") long timeEndAt);

  @Query(
      nativeQuery = true,
      value = "select sum(ph.price) from package_history ph " +
          "where ph.created_at >= :timeStartAt " +
          "and ph.created_at <= :timeEndAt " +
          "and ph.package_code in (:packageCode)"
  )
  Long countRevenueByPackageCode (
      @Param("timeStartAt") long timeStartAt,
      @Param("timeEndAt") long timeEndAt,
      @Param("packageCode") List<String> packageCode);

  @Query(
      nativeQuery = true,
      value = "select count(*) from package_history ph " +
          "where ph.created_at >= :timeStartAt " +
          "and ph.created_at <= :timeEndAt " +
          "and ph.package_code in (:packageCode)"
  )
  Integer countPackageByPackageCode (
      @Param("timeStartAt") long timeStartAt,
      @Param("timeEndAt") long timeEndAt,
      @Param("packageCode") List<String> packageCode);

  @Query(
      nativeQuery = true,
      value = "select count(DISTINCT ph.user_id) from package_history ph " +
          "where ph.created_at >= :timeStartAt " +
          "and ph.created_at <= :timeEndAt " +
          "and ph.package_code in (:packageCode)"
  )
  Integer countUserSubByPackageCode (
      @Param("timeStartAt") long timeStartAt,
      @Param("timeEndAt") long timeEndAt,
      @Param("packageCode") List<String> packageCode);

  @Query(
      nativeQuery = true,
      value = "SELECT count(DISTINCT ph.user_id) FROM package_history ph WHERE "
          + "ph.created_at >= :timeStartAt "
          + "AND ph.created_at <= :timeEndAt ")
  Integer countTotalPaidUser (
      @Param("timeStartAt") long timeStartAt,
      @Param("timeEndAt") long timeEndAt);

  @Query(
      nativeQuery = true,
      value = "SELECT count(*) FROM package_history ph WHERE "
          + "ph.created_at >= :timeStartAt "
          + "AND ph.created_at <= :timeEndAt "
          + "AND ph.package_code NOT IN (:packageCode)")
  Integer countNewSub (
      @Param("timeStartAt") long timeStartAt,
      @Param("timeEndAt") long timeEndAt,
      @Param("packageCode") List<String> packageCode);

  @Query(
      nativeQuery = true,
      value = "SELECT sum(ph.price) FROM package_history ph WHERE "
          + "ph.created_at >= :timeStartAt "
          + "AND ph.created_at <= :timeEndAt "
          + "AND ph.package_code NOT IN (:packageCode)")
  Long countTotalRevenueSub (
      @Param("timeStartAt") long timeStartAt,
      @Param("timeEndAt") long timeEndAt,
      @Param("packageCode") List<String> packageCode);
}