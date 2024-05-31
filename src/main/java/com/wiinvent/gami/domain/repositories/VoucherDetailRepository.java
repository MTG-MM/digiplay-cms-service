package com.wiinvent.gami.domain.repositories;

import com.wiinvent.gami.domain.entities.VoucherDetail;
import com.wiinvent.gami.domain.entities.type.RewardItemStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface VoucherDetailRepository extends JpaRepository<VoucherDetail, UUID>, JpaSpecificationExecutor<VoucherDetail> {


  @Query("SELECT vd FROM VoucherDetail vd WHERE vd.storeId = :voucherStoreId and vd.status = :voucherStatus ORDER BY vd.expireAt ASC")
  List<VoucherDetail> getListVoucherDetailByStatus(
      @Param("voucherStoreId") int voucherStoreId,
      @Param("voucherStatus") RewardItemStatus rewardItemStatus,
      Pageable pageable
  );

  @Query(nativeQuery = true, value = "UPDATE voucher_detail set voucher_status = 'NEW', reward_segment_id = NULL, reward_item_id = NULL " +
      "where reward_segment_id = :segmentId and reward_item_id = :itemId LIMIT :limit")
  @Modifying
  void updateItemStatus(
      @Param("segmentId") Long segmentId,
      @Param("itemId") Long itemId,
      @Param("limit") long limit
  );

  Page<VoucherDetail> findByStoreId(Long storeId, Pageable pageable);

  @Query(value = "SELECT COUNT(*) FROM VoucherDetail WHERE rewardSegmentId = :rewardSegmentId " +
      "AND rewardItemId = :rewardItemId " +
      "AND givenToPool >= :startDateAtVn " +
      "AND givenToPool < :endDateAtVn")
  Integer getListInPollVoucherInGivenInPool(
      @Param("rewardSegmentId") Long rewardSegmentId,
      @Param("rewardItemId") Long rewardItemId,
      @Param("startDateAtVn") long startDateAtVn,
      @Param("endDateAtVn") long endDateAtVn);

  Long countVoucherDetailByStoreId(Long id);

  Long countVoucherDetailByStoreIdAndStatus(Long id, RewardItemStatus status);

  @Query(nativeQuery = true,
      value = "SELECT * FROM voucher_detail " +
          "where store_id = :storeId and voucher_status = :status LIMIT :limit ")
  List<VoucherDetail> findByStoreIdAndLimit(
      @Param("storeId") Long storeId,
      @Param("limit") Long limit,
      @Param("status") String status);
}
