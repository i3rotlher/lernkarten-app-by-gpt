package com.lernkartenapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lernkartenapp.model.Karteibox;
import com.lernkartenapp.repository.KarteiboxRepository;

import java.util.List;

@Service
public class KarteiboxService {
    @Autowired
    private KarteiboxRepository karteiboxRepository;
    @Autowired
    private KarteikarteService karteikarteService;
    
    public Karteibox createKarteibox(Karteibox karteibox) {
        return karteiboxRepository.save(karteibox);
    }

    public List<Karteibox> getKarteiboxenByUser(String userId) {
        return karteiboxRepository.findAllByUserId(userId);
    }
    
    public void deleteKarteiboxenByUser(String userId) {
        List<Karteibox> karteiboxen = karteiboxRepository.findAllByUserId(userId);
        for (Karteibox karteibox : karteiboxen) {
            karteikarteService.deleteKarteikartenByKarteibox(karteibox.getId()); // Lösche zuerst die Karteikarten der Karteibox
            karteiboxRepository.delete(karteibox);
        }
    }

    public void deleteKarteibox(String karteiboxId) {
        karteikarteService.deleteKarteikartenByKarteibox(karteiboxId);
        karteiboxRepository.deleteById(karteiboxId);
    }
}
