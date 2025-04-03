package com.infobasic.arcade.arcade_rank.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infobasic.arcade.arcade_rank.model.Player;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    Optional<Player> findByUsername(String username);
    Optional<Player> deleteByUsername(String username);
}
