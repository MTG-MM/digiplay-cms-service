package com.wiinvent.gami.domain.stores.payment;

import com.wiinvent.gami.domain.entities.payment.PaymentMethod;
import com.wiinvent.gami.domain.exception.base.ResourceNotFoundException;
import com.wiinvent.gami.domain.stores.BaseStorage;
import com.wiinvent.gami.domain.utils.Constant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class PaymentMethodStorage extends BaseStorage {
  public Page<PaymentMethod> findAll(Pageable pageable){
    return paymentMethodRepository.findAll(pageable);
  }

  public PaymentMethod save(PaymentMethod paymentMethod){
    return paymentMethodRepository.save(paymentMethod);
  }
  public PaymentMethod findById(Integer id){
    return paymentMethodRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(Constant.PAYMENT_METHOD_NOT_FOUND));
  }

  public void deleteById(Integer id){
    paymentMethodRepository.deleteById(id);
  }
}