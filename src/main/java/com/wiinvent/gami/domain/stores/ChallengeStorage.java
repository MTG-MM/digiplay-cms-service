package com.wiinvent.gami.domain.stores;

import com.wiinvent.gami.domain.entities.Challenge;
import com.wiinvent.gami.domain.entities.type.Status;
import com.wiinvent.gami.domain.utils.CacheKey;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ChallengeStorage extends BaseStorage{
  public Challenge findChallengeById(Integer id) {
    return challengeRepository.findById(id).orElse(null);
  }

  public void save(Challenge challenge) {
    challengeRepository.save(challenge);
    remoteCache.del(removeCacheKeys(challenge));
  }

  public Page<Challenge> findAll(Integer gameId, Status status, Pageable pageable) {
    return challengeRepository.findAll(challengeSpecification(gameId, status), pageable);
  }

  public List<String> removeCacheKeys(Challenge challenge) {
    List<String> keys = new ArrayList<>();
    keys.add(cacheKey.genListAllChallenge());
    keys.add(cacheKey.genChallengeById(challenge.getId()));
    keys.add(cacheKey.genPageActiveChallenge(0));
    keys.add(cacheKey.genPageActiveChallenge(1));
    keys.add(cacheKey.genPageActiveChallenge(2));
    keys.add(cacheKey.genPageActiveChallenge(3));
    keys.add(cacheKey.genPageActiveChallenge(4));
    keys.add(cacheKey.genPageActiveChallenge(5));
    return keys;
  }

  public Specification<Challenge> challengeSpecification(Integer gameId, Status status) {
    return (root, query, criteriaBuilder) -> {
      List<Predicate> conditionLists = new ArrayList<>();
      conditionLists.add(criteriaBuilder.notEqual(root.get("status"), Status.DELETE));
      if (gameId != null) {
        conditionLists.add(criteriaBuilder.equal(root.get("gameId"), gameId));
      }
      if (status != null) {
        conditionLists.add(criteriaBuilder.equal(root.get("status"), status));
      }
      return criteriaBuilder.and(conditionLists.toArray(new Predicate[0]));
    };
  }
}
