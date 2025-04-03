package com.infobasic.arcade.arcade_rank.repository;

import com.infobasic.arcade.arcade_rank.model.Videogame;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideogameRepository extends JpaRepository<Videogame, Long>{
    Optional<Videogame> findByName(String name);
}
