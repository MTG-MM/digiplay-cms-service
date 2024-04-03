package com.wiinvent.gami.domain.service;

import com.wiinvent.gami.domain.dto.payment.PaymentMethodCreateDto;
import com.wiinvent.gami.domain.dto.payment.PaymentMethodUpdateDto;
import com.wiinvent.gami.domain.entities.payment.PaymentMethod;
import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.exception.BadRequestException;
import com.wiinvent.gami.domain.response.payment.PaymentMethodResponse;
import com.wiinvent.gami.domain.utils.Constant;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Component
@Log4j2
public class PaymentMethodService extends BaseService{
  @Autowired @Lazy private PaymentMethodService self;
  public Page<PaymentMethodResponse> findAll(Pageable pageable){
    Page<PaymentMethod> payments = paymentMethodStorage.findAll(pageable);
    return modelMapper.toPagePaymentMethodResponse(payments);
  }

  public PaymentMethodResponse getPaymentMethodDetail(Integer id){
    PaymentMethod paymentMethod = paymentMethodStorage.findById(id);

    return modelMapper.toPaymentMethodResponse(paymentMethod);
  }

  public boolean createPaymentMethod(PaymentMethodCreateDto dto){
    if(dto.getStatus()==null) dto.setStatus(Status.ACTIVE);

    /*
    * neu payment method type da ton tai
    * */
    if(Objects.nonNull(paymentMethodStorage.findPaymentMethodByPaymentMethodType(dto.getPaymentMethodType())))
      throw new BadRequestException(Constant.PAYMENT_METHOD_EXISTS);

    PaymentMethod paymentMethod = modelMapper.toPaymentMethod(dto);

    self.savePaymentMethod(paymentMethod);
    return true;
  }

  public boolean updatePaymentMethod(PaymentMethodUpdateDto dto){
    PaymentMethod paymentMethod = paymentMethodStorage.findById(dto.getId());

    /*
    * neu thay doi payment method type:
    * neu da ton tai => loi
    * */
    if(
        !dto.getPaymentMethodType().equals(paymentMethod.getPaymentMethodType())
        && Objects.nonNull(paymentMethodStorage.findPaymentMethodByPaymentMethodType(dto.getPaymentMethodType()))
    ) throw new BadRequestException(Constant.PAYMENT_METHOD_EXISTS);

    paymentMethod.from(dto);

    self.savePaymentMethod(paymentMethod);
    return true;
  }

  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
  public void savePaymentMethod(PaymentMethod paymentMethod){
    paymentMethodStorage.save(paymentMethod);
  }

  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
  public boolean deletePaymentMethod(Integer id){
    PaymentMethod paymentMethod = paymentMethodStorage.findById(id);

    paymentMethodStorage.deleteById(paymentMethod.getId());
    return true;
  }


}
