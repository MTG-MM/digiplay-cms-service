package com.wiinvent.gami.domain.service;

import com.wiinvent.gami.domain.dto.payment.PaymentMethodCreateDto;
import com.wiinvent.gami.domain.dto.payment.PaymentMethodUpdateDto;
import com.wiinvent.gami.domain.entities.payment.PaymentMethod;
import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.response.payment.PaymentMethodResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@Log4j2
public class PaymentMethodService extends BaseService{
  public Page<PaymentMethodResponse> findAll(Pageable pageable){
    Page<PaymentMethod> payments = paymentMethodStorage.findAll(pageable);
    return modelMapper.toPagePaymentMethodResponse(payments);
  }

  public PaymentMethodResponse getPaymentMethodDetail(Integer id){
    PaymentMethod paymentMethod = paymentMethodStorage.findById(id);

    return modelMapper.toPaymentMethodResponse(paymentMethod);
  }

  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
  public boolean createPaymentMethod(PaymentMethodCreateDto dto){
    if(dto.getStatus()==null) dto.setStatus(Status.ACTIVE);

    PaymentMethod paymentMethod = modelMapper.toPaymentMethod(dto);

    paymentMethodStorage.save(paymentMethod);
    return true;
  }

  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
  public boolean updatePaymentMethod(PaymentMethodUpdateDto dto){
    PaymentMethod paymentMethod = paymentMethodStorage.findById(dto.getId());
    paymentMethod.from(dto);

    paymentMethodStorage.save(paymentMethod);
    return true;
  }

  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
  public boolean deletePaymentMethod(Integer id){
    PaymentMethod paymentMethod = paymentMethodStorage.findById(id);

    paymentMethodStorage.deleteById(paymentMethod.getId());
    return true;
  }


}
