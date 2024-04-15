package com.wiinvent.gami.domain.stores.payment;

import com.wiinvent.gami.domain.entities.game.GameType;
import com.wiinvent.gami.domain.entities.payment.PaymentMethod;
import com.wiinvent.gami.domain.entities.type.PaymentMethodType;
import com.wiinvent.gami.domain.exception.base.ResourceNotFoundException;
import com.wiinvent.gami.domain.stores.BaseStorage;
import com.wiinvent.gami.domain.utils.Constant;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class PaymentMethodStorage extends BaseStorage {
  public Page<PaymentMethod> findAll(Pageable pageable){
    return paymentMethodRepository.findAll(pageable);
  }

  public void save(PaymentMethod paymentMethod){
    paymentMethodRepository.save(paymentMethod);
    remoteCache.deleteKey(cacheKey.genAllPaymentMethod());
  }
  public PaymentMethod findById(Integer id){
    return paymentMethodRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(Constant.PAYMENT_METHOD_NOT_FOUND));
  }

  public void deleteById(Integer id){
    paymentMethodRepository.deleteById(id);
  }

  public PaymentMethod findPaymentMethodByPaymentMethodType(PaymentMethodType paymentMethodType){
    return paymentMethodRepository.findOne(specification(null, paymentMethodType)).orElseThrow(()->new ResourceNotFoundException(Constant.PAYMENT_METHOD_NOT_FOUND));
  }

  private Specification<PaymentMethod> specification(Integer id, PaymentMethodType paymentMethodType) {
    return (Root<PaymentMethod> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
      List<Predicate> predicates = new ArrayList<>();
      if(Objects.nonNull(id)) predicates.add(criteriaBuilder.equal(root.get("id"), id));
      if(Objects.nonNull(paymentMethodType)) predicates.add(criteriaBuilder.equal(root.get("paymentMethodType"), paymentMethodType));
      return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    };
  }
}