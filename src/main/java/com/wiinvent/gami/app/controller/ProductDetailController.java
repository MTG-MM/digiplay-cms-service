package com.wiinvent.gami.app.controller;

import com.wiinvent.gami.domain.response.ProductDetailResponse;
import com.wiinvent.gami.domain.response.base.PageResponse;
import com.wiinvent.gami.domain.service.ProductDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("v1/portal/product-detail")
@PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR')")
public class ProductDetailController {
  @Autowired
  private ProductDetailService productDetailService;
  @GetMapping("")
  public ResponseEntity<PageResponse<ProductDetailResponse>> getProductDetail(
      @RequestParam Long storeId,
      @RequestParam(required = false) String name,
      @RequestParam(required = false) String code,
      @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC)
      @PageableDefault
      Pageable pageable) {
    return ResponseEntity.ok(productDetailService.getAllProductDetails(storeId, name, code, pageable));
  }

  @PostMapping("{storeId}/import")
  public ResponseEntity<Boolean> importVoucher(
      @RequestParam("file") MultipartFile file,
      @PathVariable Long storeId) throws Exception {
    productDetailService.importProduct(file, storeId);
    return ResponseEntity.ok(true);
  }
}
