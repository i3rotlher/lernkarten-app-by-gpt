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

    public Karteibox createKarteibox(Karteibox karteibox) {
        return karteiboxRepository.save(karteibox);
    }

    public List<Karteibox> getKarteiboxenByUser(String userId) {
        return karteiboxRepository.findAllByUserId(userId);
    }
    // Weitere Methoden ...
}
