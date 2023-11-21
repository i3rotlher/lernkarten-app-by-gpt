package com.lernkartenapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    
    @DeleteMapping("/{karteikarteId}")
    public ResponseEntity<Void> deleteKarteikarte(@PathVariable String karteikarteId) {
        karteikarteService.deleteKarteikarte(karteikarteId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{karteikarteId}/markAsKnown")
    public ResponseEntity<Karteikarte> markCardAsKnown(@PathVariable String karteikarteId) {
        Karteikarte updatedKarteikarte = karteikarteService.markCardAsKnown(karteikarteId);
        return ResponseEntity.ok(updatedKarteikarte);
    }

    @PutMapping("/{karteikarteId}")
    public ResponseEntity<Karteikarte> updateKarteikarte(@PathVariable String karteikarteId, @RequestBody Karteikarte karteikarte) {
        Karteikarte updatedKarteikarte = karteikarteService.updateKarteikarte(karteikarteId, karteikarte);
        return ResponseEntity.ok(updatedKarteikarte);
    }
}
