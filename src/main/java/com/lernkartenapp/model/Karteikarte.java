package com.lernkartenapp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Karteikarte {
    @Id
    private String id;
    private String frage;
    private String antwort;
    private String karteiboxId; // Verknüpfung zur Karteibox
    private boolean known = false;

    // Konstruktoren
    public Karteikarte() {}

    // Getter und Setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFrage() {
        return frage;
    }

    public void setFrage(String frage) {
        this.frage = frage;
    }

    public String getAntwort() {
        return antwort;
    }

    public void setAntwort(String antwort) {
        this.antwort = antwort;
    }

    public String getKarteiboxId() {
        return karteiboxId;
    }

    public void setKarteiboxId(String karteiboxId) {
        this.karteiboxId = karteiboxId;
    }

    public boolean getKnown() {
        return known;
    }

    public void setKnown(boolean known) {
        this.known = known;
    }
}
