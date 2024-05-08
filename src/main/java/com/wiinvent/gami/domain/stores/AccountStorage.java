package com.wiinvent.gami.domain.stores;

import com.wiinvent.gami.domain.entities.Account;
import com.wiinvent.gami.domain.entities.type.AccountRole;
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
import java.util.UUID;

@Component
public class AccountStorage extends BaseStorage {

  public void save(Account account) {
    accountRepository.save(account);
  }

  public Account findById(UUID id) {
    return accountRepository.findById(id).orElse(null);

  }

  public Account findByUsername(String username) {
    return accountRepository.findByUsername(username);
  }

  public Page<Account> findPageAccount(String username, UUID teamId, Pageable pageable) {
    return accountRepository.findAll(specificationAccount(username, teamId), pageable);
  }

  public List<Account> findAccountByAccountRole() {
    return accountRepository.findAccountByRole(AccountRole.PUBLISHER);
  }

  private Specification<Account> specificationAccount(String username, UUID teamId) {
    return (Root<Account> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
      List<Predicate> predicates = new ArrayList<>();

      if(username != null){
        predicates.add(criteriaBuilder.equal(root.get("username"), username));
      }
      if(teamId != null){
        predicates.add(criteriaBuilder.equal(root.get("teamId"), teamId));
      }

      return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    };
  }

  public void delete(Account account) {
    accountRepository.delete(account);
  }

  public Account findByUserName(String username) {
    return accountRepository.findByUsername(username);
  }
}
