package com.lernkartenapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lernkartenapp.model.Karteikarte;
import com.lernkartenapp.repository.KarteikarteRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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
    
    public void deleteKarteikartenByKarteibox(String karteiboxId) {
        List<Karteikarte> karteikarten = karteikarteRepository.findAllByKarteiboxId(karteiboxId);
        karteikarteRepository.deleteAll(karteikarten);
    }

    public void deleteKarteikarte(String karteikarteId) {
        karteikarteRepository.deleteById(karteikarteId);
    }

    public Karteikarte markCardAsKnown(String karteikarteId) {
        Optional<Karteikarte> karteikarteOptional = karteikarteRepository.findById(karteikarteId);
        if (karteikarteOptional.isPresent()) {
            Karteikarte karteikarte = karteikarteOptional.get();
            karteikarte.setKnown(true);
            return karteikarteRepository.save(karteikarte);
        } else {
            throw new NoSuchElementException("Karteikarte mit der ID " + karteikarteId + " nicht gefunden");
        }
    }

    public Karteikarte updateKarteikarte(String karteikarteId, Karteikarte updatedKarteikarte) {
        return karteikarteRepository.findById(karteikarteId)
            .map(karteikarte -> {
                karteikarte.setFrage(updatedKarteikarte.getFrage());
                karteikarte.setAntwort(updatedKarteikarte.getAntwort());
                // Setzen Sie hier weitere Felder, die aktualisiert werden sollen
                return karteikarteRepository.save(karteikarte);
            })
            .orElseThrow(() -> new NoSuchElementException("Karteikarte mit der ID " + karteikarteId + " nicht gefunden"));
    } 

}
