package com.infobasic.arcade.arcade_rank.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.infobasic.arcade.arcade_rank.model.Videogame;
import com.infobasic.arcade.arcade_rank.repository.VideogameRepository;
import jakarta.validation.Valid;

@Service
@Validated
public class VideogameService {

    private final VideogameRepository videogameRepository;

    public VideogameService(VideogameRepository videogameRepository) {
        this.videogameRepository = videogameRepository;
    }
 

    //Add a videogame
    public Videogame saveVideogame(@Valid Videogame videogame) {
        if (videogameRepository.findByName(videogame.getName()).isPresent()) {
            throw new IllegalArgumentException("This videogame already exists");
        }
        return videogameRepository.save(videogame);
    }

    //Find a videogame by name
    public Optional<Videogame> findByName(String name) {
        return videogameRepository.findByName(name);
    }


    //Find a videogame by id
    public Videogame getVideogameById(Long videogameId) {
        // Usa il metodo findById del repository per recuperare il videogioco
        Optional<Videogame> videogame = videogameRepository.findById(videogameId);
        return videogame.orElseThrow(() -> new IllegalArgumentException("Videogame not found with id " + videogameId));
    }

    //Get all videogames
    public List<Videogame> getAllVideogames() {
        return videogameRepository.findAll();
    }

    public void deleteVideogameById(Long id) {
        videogameRepository.deleteById(id);
    }

}
