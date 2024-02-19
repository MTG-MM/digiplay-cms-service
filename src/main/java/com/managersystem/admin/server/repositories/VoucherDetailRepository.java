package com.managersystem.admin.server.repositories;

import com.managersystem.admin.server.entities.VoucherDetail;
import com.managersystem.admin.server.entities.type.PollItemStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface VoucherDetailRepository extends JpaRepository<VoucherDetail, UUID> {


  @Query("SELECT vd FROM VoucherDetail vd WHERE vd.storeId = :voucherStoreId and vd.status = :voucherStatus ORDER BY vd.expireAt ASC")
  List<VoucherDetail> getListVoucherDetailByStatus(
      @Param("voucherStoreId") int voucherStoreId,
      @Param("voucherStatus") PollItemStatus pollItemStatus,
      Pageable pageable
  );

  @Query(nativeQuery = true, value = "UPDATE voucher_detail set voucher_status = 'NEW', segment_detail_id = NULL where segment_detail_id = :segmentDetailId")
  @Modifying
  void updateItemStatus(
      @Param("segmentDetailId") Long segmentDetailId);

  Page<VoucherDetail> findByStoreId(Long storeId, Pageable pageable);
}
