package com.wiinvent.gami.domain.stores.reward;

import com.wiinvent.gami.domain.entities.reward.RewardSegment;
import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.stores.BaseStorage;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RewardSegmentStorage extends BaseStorage {

  public List<RewardSegment> findByStatus(Status status) {
    return rewardSegmentRepository.findByStatus(status);
  }

  public void save(RewardSegment rewardSegment) {
    rewardSegmentRepository.save(rewardSegment);
  }

  public RewardSegment findById(Long id) {
    return rewardSegmentRepository.findById(id).orElse(null);
  }

  public Page<RewardSegment> findAll(String code, Status status, Pageable pageable) {
    return rewardSegmentRepository.findAll(rewardSegmentCondition(code, status), pageable);
  }

  public Specification<RewardSegment> rewardSegmentCondition(String code, Status status){
    return (root, query, criteriaBuilder) -> {
      List<Predicate> conditionList = new ArrayList<>();
      conditionList.add(criteriaBuilder.notEqual(root.get("status"), Status.DELETE));
      if (code != null){
        conditionList.add(criteriaBuilder.like(root.get("code"), "%" + code + "%"));
      }
      if (status != null){
        conditionList.add(criteriaBuilder.equal(root.get("status"), status));
      }
      return criteriaBuilder.and(conditionList.toArray(new Predicate[0]));
    };
  }


  public RewardSegment findByCode(String code) {
    return rewardSegmentRepository.findByCode(code);
  }

  public List<RewardSegment> findAll() {
    return rewardSegmentRepository.findAll();
  }
}
