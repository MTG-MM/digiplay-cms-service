package com.wiinvent.gami.app.controller;

import com.wiinvent.gami.domain.response.RwItemStoreDetailResponse;
import com.wiinvent.gami.domain.response.base.PageResponse;
import com.wiinvent.gami.domain.service.RwItemStoreDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("v1/portal/reward-item-stores/detail")
@PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR')")
public class RwItemStoreDetailController {
  @Autowired
  private RwItemStoreDetailService rwItemStoreDetailService;

  @GetMapping("{storeId}")
  public ResponseEntity<PageResponse<RwItemStoreDetailResponse>> getAllRwItemStoreDetails(
      @PathVariable Long storeId,
      @RequestParam(required = false) String name,
      @RequestParam(required = false) String code,
      @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC)
      Pageable pageable) {
    return ResponseEntity.ok(rwItemStoreDetailService.getAllRwItemStoreDetails(storeId, name, code, pageable));
  }

  @PostMapping("{storeId}/import")
  public ResponseEntity<Boolean> importVoucher(
      @RequestParam("file") MultipartFile file,
      @PathVariable Long storeId) throws Exception {
    rwItemStoreDetailService.importRwItemStoreDetail(file, storeId);
    return ResponseEntity.ok(true);
  }
}
