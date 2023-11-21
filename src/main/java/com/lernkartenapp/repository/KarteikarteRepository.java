package com.lernkartenapp.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.lernkartenapp.model.Karteikarte;

public interface KarteikarteRepository extends MongoRepository<Karteikarte, String> {
    // Finden von Karteikarten einer bestimmten Karteibox
    List<Karteikarte> findAllByKarteiboxId(String karteiboxId);
}
