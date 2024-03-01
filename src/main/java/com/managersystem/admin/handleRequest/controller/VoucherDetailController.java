package com.managersystem.admin.handleRequest.controller;

import com.managersystem.admin.handleRequest.controller.response.VoucherDetailResponse;
import com.managersystem.admin.handleRequest.controller.response.base.PageResponse;
import com.managersystem.admin.server.service.VoucherDetailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/mos/cms/voucher-details")
public class VoucherDetailController {

  @Autowired
  private VoucherDetailService voucherDetailService;

  @GetMapping("{storeId}")
  public ResponseEntity<PageResponse<VoucherDetailResponse>> getVoucherDetail(@PathVariable Long storeId, Pageable pageable) {
    return ResponseEntity.ok(voucherDetailService.getAllVoucherDetails(storeId, pageable));
  }

}
