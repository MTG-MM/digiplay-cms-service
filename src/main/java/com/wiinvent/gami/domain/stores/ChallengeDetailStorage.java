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
    remoteCache.del(cacheKey.genChallengeDetailById(challengeDetail.getId()));
  }

  public Page<ChallengeDetail> findAll(Integer level, Status status, Pageable pageable){
    return challengeDetailRepository.findAll(challengeDetailSpecification(level, status), pageable);
  }

  public Specification<ChallengeDetail> challengeDetailSpecification(Integer level, Status status){
    return (root, query, criteriaBuilder) -> {
      List<Predicate> conditionLists = new ArrayList<>();
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
}
