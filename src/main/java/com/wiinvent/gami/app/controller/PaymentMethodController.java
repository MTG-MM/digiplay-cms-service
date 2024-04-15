package com.wiinvent.gami.app.controller;

import com.wiinvent.gami.domain.dto.payment.PaymentMethodCreateDto;
import com.wiinvent.gami.domain.dto.payment.PaymentMethodUpdateDto;
import com.wiinvent.gami.domain.response.base.PageResponse;
import com.wiinvent.gami.domain.response.payment.PaymentMethodResponse;
import com.wiinvent.gami.domain.service.PaymentMethodService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/vt/cms/payment-method")
public class PaymentMethodController extends BaseController{
  @Autowired private PaymentMethodService paymentMethodService;

  @GetMapping("")
  public ResponseEntity<PageResponse<PaymentMethodResponse>> findAll(Pageable pageable){
    return ResponseEntity.ok(
        PageResponse.createFrom(paymentMethodService.findAll(pageable))
    );
  }

  @GetMapping("{id}")
  public ResponseEntity<PaymentMethodResponse> getPaymentMethodDetail(@PathVariable Integer id){
    return ResponseEntity.ok(paymentMethodService.getPaymentMethodDetail(id));
  }

  @PostMapping("")
  public ResponseEntity<Boolean> createPaymentMethod(@RequestBody @Valid PaymentMethodCreateDto dto){
    return ResponseEntity.ok(paymentMethodService.createPaymentMethod(dto));
  }

  @PutMapping("")
  public ResponseEntity<Boolean> updatePaymentMethod(@RequestBody @Valid PaymentMethodUpdateDto dto){
    return ResponseEntity.ok(paymentMethodService.updatePaymentMethod(dto));
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Boolean> deletePaymentMethod(@PathVariable Integer id){
    return ResponseEntity.ok(paymentMethodService.deletePaymentMethod(id));
  }
}