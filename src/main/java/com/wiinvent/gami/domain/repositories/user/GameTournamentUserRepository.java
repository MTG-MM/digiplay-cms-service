package com.wiinvent.gami.domain.repositories.user;

import com.wiinvent.gami.domain.entities.user.GameTournamentUser;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameTournamentUserRepository extends JpaRepository<GameTournamentUser, String> {
}