package com.wiinvent.gami.domain.stores;

import com.wiinvent.gami.domain.entities.ProductDetail;
import com.wiinvent.gami.domain.entities.VoucherDetail;
import com.wiinvent.gami.domain.entities.type.RewardItemStatus;
import com.wiinvent.gami.domain.entities.type.StoreType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class ProductDetailStorage extends BaseStorage {

  public List<ProductDetail> findAll() {
    return productDetailRepository.findAll();
  }

  public void save(ProductDetail productDetail) {
    productDetailRepository.save(productDetail);
  }

  public Page<ProductDetail> findAll(Pageable pageable) {
    return productDetailRepository.findAll(pageable);
  }

  public ProductDetail findById(UUID id) {
    return productDetailRepository.findById(id).orElse(null);
  }

  public void saveAll(List<ProductDetail> productDetails) {
    productDetailRepository.saveAll(productDetails);
  }

  public List<ProductDetail> getListProductDetailByStatus(int productStoreId, RewardItemStatus rewardItemStatus, int limit) {
    Pageable pageable = PageRequest.of(0, limit);
    return productDetailRepository.getListProductDetailByStatus(productStoreId, rewardItemStatus, pageable);
  }

  public void updateItemStatus(Long rewardSegmentId, Long rewardItemId, long limit) {
    productDetailRepository.updateItemStatus(rewardSegmentId, rewardItemId, limit);
  }

  public Integer getListInPollProductInGivenInPool(Long rewardSegmentId, Long rewardItemId, long startDateAtVn, long endDateAtVn) {
    return productDetailRepository.getListInPollProductInGivenInPool(rewardSegmentId, rewardItemId, startDateAtVn, endDateAtVn);
  }

  public Long countProductDetailByStoreId(Long id) {
    return productDetailRepository.countProductDetailByStoreId(id);
  }

  public Long countProductDetailByStoreIdAndStatus(Long id, RewardItemStatus status) {
    return productDetailRepository.countProductDetailByStoreIdAndStatus(id, status);
  }

  public Page<ProductDetail> findAlProductDetails(Long storeId ,String name, String code, Pageable pageable) {
    return productDetailRepository.findAll(productDetailSpecification(storeId, name, code), pageable);
  }

  public Specification<ProductDetail> productDetailSpecification(Long storeId, String name, String code){
    return (root, query, criteriaBuilder) -> {
      List<Predicate> conditionLists = new ArrayList<>();
      conditionLists.add(criteriaBuilder.equal(root.get("storeId"), storeId));
      if (name != null){
        conditionLists.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
      }
      if (code != null){
        conditionLists.add(criteriaBuilder.like(root.get("code"), "%" + code + "%"));
      }
      return criteriaBuilder.and(conditionLists.toArray(new Predicate[0]));
    };
  }}
