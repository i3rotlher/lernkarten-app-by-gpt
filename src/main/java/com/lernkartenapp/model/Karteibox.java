package com.lernkartenapp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class Karteibox {
    @Id
    private String id;
    private String name;
    private String beschreibung;
    private String userId; // Verknüpfung zum Nutzer
    private List<String> karteikartenIds; // IDs der Karteikarten

    // Konstruktoren
    public Karteibox() {}

    // Getter und Setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<String> getKarteikartenIds() {
        return karteikartenIds;
    }

    public void setKarteikartenIds(List<String> karteikartenIds) {
        this.karteikartenIds = karteikartenIds;
    }
}
