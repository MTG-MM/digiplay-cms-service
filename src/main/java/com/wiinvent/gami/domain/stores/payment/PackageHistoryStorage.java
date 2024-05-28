package com.wiinvent.gami.domain.stores.payment;

import com.wiinvent.gami.domain.entities.payment.PackageHistory;
import com.wiinvent.gami.domain.response.type.CursorType;
import com.wiinvent.gami.domain.stores.BaseStorage;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class PackageHistoryStorage extends BaseStorage {
  public List<PackageHistory> findAll(UUID userId, UUID transId, Long startDate, Long endDate, Long next, Long pre, int limit, CursorType type) {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<PackageHistory> query = criteriaBuilder.createQuery(PackageHistory.class);
    Root<PackageHistory> root = query.from(PackageHistory.class);
    List<Predicate> conditionList = new ArrayList<>();
    conditionList.add(criteriaBuilder.equal(root.get("userId"), userId));
    if (transId != null){
      conditionList.add(criteriaBuilder.equal(root.get("id"), transId));
    }
    if (startDate != null && endDate != null){
      conditionList.add(criteriaBuilder.and(criteriaBuilder.greaterThan(root.get("createdAt"), startDate),
          criteriaBuilder.lessThan(root.get("createdAt"), endDate)));
    }
    conditionList.add(criteriaBuilder.and(criteriaBuilder.greaterThan(root.get("createdAt"), pre),
        criteriaBuilder.lessThan(root.get("createdAt"), next)));
    if (type == CursorType.NEXT || type == CursorType.FIRST) {
      query.where(criteriaBuilder.and(conditionList.toArray(new Predicate[0])))
          .orderBy(criteriaBuilder.desc(root.get("createdAt")));
    } else if (type == CursorType.PRE) {
      query.where(criteriaBuilder.and(conditionList.toArray(new Predicate[0])))
          .orderBy(criteriaBuilder.asc(root.get("createdAt")));
    }
    TypedQuery<PackageHistory> typedQuery = entityManager.createQuery(query);
    typedQuery.setMaxResults(limit);
    return typedQuery.getResultList();
  }

  public PackageHistory findById(UUID id) {
    return packageHistoryRepository.findPackageHistoryById(id);
  }

  public Long countTotalRevenue(Long start, Long end) {
    return packageHistoryRepository.countTotalRevenue(start, end);
  }

  public Integer countTotalPaidUser(Long start, Long end) {
    return packageHistoryRepository.countTotalPaidUser(start, end);
  }

  public Long countRevenueByPackageCode(Long start, Long end, List<String> packageCode) {
    return packageHistoryRepository.countRevenueByPackageCode(start, end, packageCode);
  }

  public Integer countPackagePyPackageCode(Long start, Long end, List<String> packageCode) {
    return packageHistoryRepository.countPackageByPackageCode(start, end, packageCode);
  }

  public Integer countUserSubByPackageCode(Long start, Long end, List<String> packageCode) {
    return packageHistoryRepository.countUserSubByPackageCode(start, end, packageCode);
  }

  public Integer countNewSub(Long start, Long end, List<String> code) {
    return packageHistoryRepository.countNewSub(start, end, code);
  }

  public Long countTotalRevenueSub(Long start, Long end, List<String> code) {
    return packageHistoryRepository.countTotalRevenueSub(start, end, code);
  }
}
