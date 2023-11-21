package com.lernkartenapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.lernkartenapp.model.Karteibox;
import com.lernkartenapp.service.KarteiboxService;

import java.util.List;

@RestController
@RequestMapping("/karteiboxen")
public class KarteiboxController {
    @Autowired
    private KarteiboxService karteiboxService;

    @PostMapping("/")
    public Karteibox createKarteibox(@RequestBody Karteibox karteibox) {
        // Hole das Authentication-Objekt
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName(); // Hier wird die Benutzer-ID oder der Benutzername des eingeloggten Benutzers abgerufen

        // Überprüfe, ob der eingeloggte Benutzer berechtigt ist, diese Karteibox zu erstellen
        if (!currentPrincipalName.equals(karteibox.getUserId())) {
            throw new SecurityException("Nicht autorisiert, diese Karteibox zu erstellen");
        }

        return karteiboxService.createKarteibox(karteibox);
    }

    @GetMapping("/user/{userId}")
    public List<Karteibox> getKarteiboxenByUser(@PathVariable String userId, Authentication authentication) {
        // Hole das Authentication-Objekt
        String currentPrincipalName = authentication.getName();

        // Überprüfe, ob der eingeloggte Benutzer derjenige ist, der die Karteiboxen anfordert
        if (!currentPrincipalName.equals(userId)) {
            throw new SecurityException("Nicht autorisiert, Karteiboxen dieses Benutzers zu sehen");
        }

        return karteiboxService.getKarteiboxenByUser(userId);
    }
    // Weitere Endpunkte ...
}
