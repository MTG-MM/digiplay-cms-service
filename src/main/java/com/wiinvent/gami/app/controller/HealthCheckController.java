package com.wiinvent.gami.app.controller;

import com.wiinvent.gami.domain.service.AccountService;
import com.wiinvent.gami.domain.service.ProductDetailService;
import com.wiinvent.gami.domain.service.reward.RewardScheduleService;
import com.wiinvent.gami.domain.service.VoucherDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/vt/cms/cms")
public class HealthCheckController {

  @Autowired
  AccountService accountService;
  @Autowired
  RewardScheduleService rewardScheduleService;
  @Autowired
  VoucherDetailService voucherDetailService;
  @Autowired
  ProductDetailService productDetailService;

  @GetMapping("")
  public ResponseEntity<?> healthCheck() {
    return ResponseEntity.ok("Kết nối thành công");
  }

  @PostMapping("init-data-spin-test")
  public ResponseEntity<?> initDataSpinTest() {
    return ResponseEntity.ok(rewardScheduleService.initData());
  }

  @PostMapping("init-voucher-test")
  public ResponseEntity<?> initVoucherTest() {
    voucherDetailService.initRandomVoucherDetail();
    return ResponseEntity.ok(true);
  }

  @PostMapping("init-product-test")
  public ResponseEntity<?> initProductTest() {
    productDetailService.initRandomProductDetail();
    return ResponseEntity.ok(true);
  }

  @PostMapping("rDeque-test")
  public ResponseEntity<?> rDequeTestPush(@RequestParam String key) {
    return ResponseEntity.ok(rewardScheduleService.pushToRDeque(key));
  }

  @GetMapping("rDeque-test")
  public ResponseEntity<?> rDequeTest(@RequestParam String key) {
    return ResponseEntity.ok(rewardScheduleService.getFromRDeque(key));
  }

  @GetMapping("rDeque-test-first")
  public ResponseEntity<?> rDequePeekFirst(@RequestParam String key) {
    return ResponseEntity.ok(rewardScheduleService.rDequePeekFirst(key));
  }
}
