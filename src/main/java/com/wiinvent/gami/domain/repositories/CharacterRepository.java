package com.wiinvent.gami.domain.repositories;

import com.wiinvent.gami.domain.entities.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CharacterRepository extends JpaRepository<Character, Integer>, JpaSpecificationExecutor<Character> {
  List<Character> findAllByIdIn(List<Integer> ids);
}