package com.wiinvent.gami.app.controller;

import com.wiinvent.gami.domain.response.VoucherDetailResponse;
import com.wiinvent.gami.domain.response.base.PageResponse;
import com.wiinvent.gami.domain.service.VoucherDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("v1/portal/voucher-details")
@PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR')")
public class VoucherDetailController {

  @Autowired
  private VoucherDetailService voucherDetailService;

  @GetMapping("{storeId}")
  public ResponseEntity<PageResponse<VoucherDetailResponse>> getVoucherDetail(
      @PathVariable Long storeId,
      @RequestParam(required = false) String name,
      @RequestParam(required = false) String code,
      @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC)
      Pageable pageable) {
    return ResponseEntity.ok(voucherDetailService.getAllVoucherDetails(storeId, name, code, pageable));
  }

  @PostMapping("{storeId}/import")
  public ResponseEntity<Boolean> importVoucher(
      @RequestParam("file") MultipartFile file,
      @PathVariable Long storeId) throws Exception {
    voucherDetailService.importVoucher(file, storeId);
    return ResponseEntity.ok(true);
  }
}
