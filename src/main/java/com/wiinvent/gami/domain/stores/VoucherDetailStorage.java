package com.wiinvent.gami.domain.stores;

import com.wiinvent.gami.domain.entities.VoucherDetail;
import com.wiinvent.gami.domain.entities.type.RewardItemStatus;
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
public class VoucherDetailStorage extends BaseStorage {

  public List<VoucherDetail> findAll() {
    return voucherDetailRepository.findAll();
  }

  public void save(VoucherDetail voucherDetail) {
    voucherDetailRepository.save(voucherDetail);
  }

  public Page<VoucherDetail> findAll(Pageable pageable) {
    return voucherDetailRepository.findAll(pageable);
  }

  public VoucherDetail findById(UUID id) {
    return voucherDetailRepository.findById(id).orElse(null);
  }

  public void saveAll(List<VoucherDetail> voucherDetails) {
    voucherDetailRepository.saveAll(voucherDetails);
  }

  public List<VoucherDetail> getListVoucherDetailByStatus(int voucherStoreId, RewardItemStatus rewardItemStatus, int limit) {
    Pageable pageable = PageRequest.of(0, limit);
    return voucherDetailRepository.getListVoucherDetailByStatus(voucherStoreId, rewardItemStatus, pageable);
  }

  public void updateItemStatus(Long rewardSegmentId, Long rewardItemId, long limit) {
    voucherDetailRepository.updateItemStatus(rewardSegmentId, rewardItemId, limit);
  }

  public Page<VoucherDetail> findVoucherByStoreIdAndLimit(Long storeId, Pageable pageable) {
    return voucherDetailRepository.findByStoreId(storeId, pageable);
  }

  public Page<VoucherDetail> findAllVoucherDetails(Long storeId, String name, String code, Pageable pageable) {
    return voucherDetailRepository.findAll(voucherDetailSpecification(storeId, name, code), pageable);
  }

  public Specification<VoucherDetail> voucherDetailSpecification(Long storeId, String name, String code){
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
  }

  public Integer getListInPollVoucherInGivenInPool(Long rewardSegmentId, Long rewardItemId, long startDateAtVn, long endDateAtVn) {
    return voucherDetailRepository.getListInPollVoucherInGivenInPool(rewardSegmentId, rewardItemId, startDateAtVn, endDateAtVn);
  }

  public Long countVoucherDetailByStoreId(Long id) {
    return voucherDetailRepository.countVoucherDetailByStoreId(id);
  }

  public Long countVoucherDetailByStoreIdAndStatus(Long id, RewardItemStatus status){
    return voucherDetailRepository.countVoucherDetailByStoreIdAndStatus(id, status);
  }

  public List<VoucherDetail> findVoucherByStoreIdAndLimit(Long storeId, Long limit, RewardItemStatus status) {
    return voucherDetailRepository.findByStoreIdAndLimit(storeId, limit, status.name());
  }
}
