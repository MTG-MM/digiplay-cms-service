package com.wiinvent.gami.domain.repositories;

import com.wiinvent.gami.domain.entities.ProductDetail;
import com.wiinvent.gami.domain.entities.VoucherDetail;
import com.wiinvent.gami.domain.entities.type.RewardItemStatus;
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
public interface ProductDetailRepository extends JpaRepository<ProductDetail, UUID>, JpaSpecificationExecutor<ProductDetail> {


  @Query("SELECT vd FROM ProductDetail vd WHERE vd.storeId = :productStoreId and vd.status = :productStatus ORDER BY vd.expireAt ASC")
  List<ProductDetail> getListProductDetailByStatus(
      @Param("productStoreId") int productStoreId,
      @Param("productStatus") RewardItemStatus rewardItemStatus,
      Pageable pageable
  );

  @Query(nativeQuery = true, value = "UPDATE product_detail set product_status = 'NEW', reward_segment_id = NULL, reward_item_id = NULL " +
      "where reward_segment_id = :segmentId and reward_item_id = :itemId LIMIT :limit")
  @Modifying
  void updateItemStatus(
      @Param("segmentId") Long rewardSegmentId,
      @Param("itemId") Long rewardItemId,
      @Param("limit") long limit
  );

  @Query(value = "SELECT COUNT(*) FROM ProductDetail WHERE rewardSegmentId = :rewardSegmentId " +
      "AND rewardItemId = :rewardItemId " +
      "AND givenToPool >= :startDateAtVn " +
      "AND givenToPool < :endDateAtVn")
  Integer getListInPollProductInGivenInPool(
      @Param("rewardSegmentId") Long rewardSegmentId,
      @Param("rewardItemId") Long rewardItemId,
      @Param("startDateAtVn") long startDateAtVn,
      @Param("endDateAtVn") long endDateAtVn
  );

  Long countProductDetailByStoreId(Long id);

  Long countProductDetailByStoreIdAndStatus(Long id, RewardItemStatus status);
  @Query(nativeQuery = true,
      value = "SELECT * FROM product_detail " +
          "where store_id = :storeId and product_status = :status and limit :limit  ")
  List<ProductDetail> findByStoreIdAndLimit(
      @Param("storeId") Long storeId,
      @Param("limit") long limit,
      @Param("status") String status);
}