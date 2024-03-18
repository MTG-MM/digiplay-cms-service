package com.wiinvent.gami.domain.stores;

import com.wiinvent.gami.domain.entities.ProductDetail;
import com.wiinvent.gami.domain.entities.type.RewardItemStatus;
import com.wiinvent.gami.domain.stores.base.BaseStorage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

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
}
