package com.managersystem.admin.server.repositories;

import com.managersystem.admin.server.entities.ProductDetail;
import com.managersystem.admin.server.entities.ProductDetail;
import com.managersystem.admin.server.entities.type.PollItemStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetail, UUID> {


  @Query("SELECT vd FROM ProductDetail vd WHERE vd.storeId = :productStoreId and vd.status = :productStatus ORDER BY vd.expireAt ASC")
  List<ProductDetail> getListProductDetailByStatus(
      @Param("productStoreId") int productStoreId,
      @Param("productStatus") PollItemStatus pollItemStatus,
      Pageable pageable
  );
  @Query(nativeQuery = true, value = "UPDATE product_detail set product_status = 'NEW', segment_detail_id = NULL where segment_detail_id = :segmentDetailId")
  @Modifying
  void updateItemStatus(
      @Param("segmentDetailId") Long rewardSegmentDetailId);
}
