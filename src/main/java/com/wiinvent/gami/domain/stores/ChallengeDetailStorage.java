package com.wiinvent.gami.domain.stores;

import com.wiinvent.gami.domain.entities.ChallengeDetail;
import com.wiinvent.gami.domain.entities.type.Status;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ChallengeDetailStorage extends BaseStorage{
  public ChallengeDetail findChallengeDetailById(Integer id){
    return challengeDetailRepository.findById(id).orElse(null);
  }

  public void save(ChallengeDetail challengeDetail){
    challengeDetailRepository.save(challengeDetail);
    remoteCache.del(removeCacheKey(challengeDetail));
  }

  public Page<ChallengeDetail> findAll(Integer challengeId, Integer level, Status status, Pageable pageable){
    return challengeDetailRepository.findAll(challengeDetailSpecification(challengeId, level, status), pageable);
  }

  public Specification<ChallengeDetail> challengeDetailSpecification(Integer challengeId, Integer level, Status status){
    return (root, query, criteriaBuilder) -> {
      List<Predicate> conditionLists = new ArrayList<>();
      conditionLists.add(criteriaBuilder.equal(root.get("challengeId"), challengeId));
      conditionLists.add(criteriaBuilder.notEqual(root.get("status"), Status.DELETE));
      if(level != null){
        conditionLists.add(criteriaBuilder.equal(root.get("level"), level));
      }
      if(status != null){
        conditionLists.add(criteriaBuilder.equal(root.get("status"), status));
      }
      return criteriaBuilder.and(conditionLists.toArray(new Predicate[0]));
    };
  }

  public List<String> removeCacheKey(ChallengeDetail challengeDetail) {
    List<String> removeKey = new ArrayList<>();
    removeKey.add(cacheKey.genChallengeDetailById(challengeDetail.getId()));
    removeKey.add(cacheKey.genListAllChallengeDetailActive());
    removeKey.add(cacheKey.genChallengeDetailByChallengeId(challengeDetail.getChallengeId()));
    removeKey.add(cacheKey.genListChallengeDetailByStatusOrderByPriority(Status.ACTIVE));
    removeKey.add(cacheKey.genListChallengeDetailByStatusOrderByPriority(Status.INACTIVE));
    removeKey.add(cacheKey.genListChallengeDetailByStatusOrderByPriority(Status.DELETE));
    removeKey.add(cacheKey.genChallengeDetailByCode(challengeDetail.getCode()));
    return removeKey;
  }
}
