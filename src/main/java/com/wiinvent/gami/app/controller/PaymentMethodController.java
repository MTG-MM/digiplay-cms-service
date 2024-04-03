package com.wiinvent.gami.app.controller;

import com.wiinvent.gami.domain.dto.payment.PaymentMethodCreateDto;
import com.wiinvent.gami.domain.dto.payment.PaymentMethodUpdateDto;
import com.wiinvent.gami.domain.service.PaymentMethodService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vt/cms/payment-method")
public class PaymentMethodController extends BaseController{
  @Autowired private PaymentMethodService paymentMethodService;

  @GetMapping("")
  public ResponseEntity<?> findAll(Pageable pageable){
    return ResponseEntity.ok(paymentMethodService.findAll(pageable));
  }

  @GetMapping("{id}")
  public ResponseEntity<?> getPaymentMethodDetail(@PathVariable Integer id){
    return ResponseEntity.ok(paymentMethodService.getPaymentMethodDetail(id));
  }

  @PostMapping("")
  public ResponseEntity<?> createPaymentMethod(@RequestBody @Valid PaymentMethodCreateDto dto){
    return ResponseEntity.ok(paymentMethodService.createPaymentMethod(dto));
  }

  @PutMapping("")
  public ResponseEntity<?> updatePaymentMethod(@RequestBody @Valid PaymentMethodUpdateDto dto){
    return ResponseEntity.ok(paymentMethodService.updatePaymentMethod(dto));
  }

  @DeleteMapping("{id}")
  public ResponseEntity<?> deletePaymentMethod(@PathVariable Integer id){
    return ResponseEntity.ok(paymentMethodService.deletePaymentMethod(id));
  }
}