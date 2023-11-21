package com.lernkartenapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.lernkartenapp.model.Karteikarte;
import com.lernkartenapp.service.KarteikarteService;

import java.util.List;

@RestController
@RequestMapping("/karteikarten")
public class KarteikarteController {
    @Autowired
    private KarteikarteService karteikarteService;

    @PostMapping("/")
    public Karteikarte createKarteikarte(@RequestBody Karteikarte karteikarte) {
        return karteikarteService.createKarteikarte(karteikarte);
    }

    @GetMapping("/karteibox/{karteiboxId}")
    public List<Karteikarte> getKarteikartenByKarteibox(@PathVariable String karteiboxId) {
        return karteikarteService.getKarteikartenByKarteibox(karteiboxId);
    }
    // Weitere Endpunkte ...
}
