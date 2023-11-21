package com.lernkartenapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lernkartenapp.model.Karteikarte;
import com.lernkartenapp.repository.KarteikarteRepository;

import java.util.List;

@Service
public class KarteikarteService {
    @Autowired
    private KarteikarteRepository karteikarteRepository;

    public Karteikarte createKarteikarte(Karteikarte karteikarte) {
        return karteikarteRepository.save(karteikarte);
    }

    public List<Karteikarte> getKarteikartenByKarteibox(String karteiboxId) {
        return karteikarteRepository.findAllByKarteiboxId(karteiboxId);
    }
    // Weitere Methoden ...
}
