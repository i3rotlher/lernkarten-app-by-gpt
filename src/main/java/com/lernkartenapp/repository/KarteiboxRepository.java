package com.lernkartenapp.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.lernkartenapp.model.Karteibox;

public interface KarteiboxRepository extends MongoRepository<Karteibox, String> {
    // Finden von Karteiboxen eines bestimmten Nutzers
    List<Karteibox> findAllByUserId(String userId);
}
