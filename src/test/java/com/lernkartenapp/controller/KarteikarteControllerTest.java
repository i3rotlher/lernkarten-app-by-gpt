package com.lernkartenapp.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.lernkartenapp.model.Karteikarte;
import com.lernkartenapp.service.KarteikarteService;

import java.util.Arrays;
import java.util.List;

public class KarteikarteControllerTest {

    private MockMvc mockMvc;

    @Mock
    private KarteikarteService karteikarteService;

    @InjectMocks
    private KarteikarteController karteikarteController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(karteikarteController).build();
    }

    @Test
    public void testCreateKarteikarte() throws Exception {
        Karteikarte karteikarte = new Karteikarte();
        karteikarte.setId("1");

        when(karteikarteService.createKarteikarte(any(Karteikarte.class))).thenReturn(karteikarte);

        mockMvc.perform(post("/karteikarten/")
                .contentType(APPLICATION_JSON)
                .content("{\"id\":\"1\",\"frage\":\"Was ist Java?\",\"antwort\":\"Eine Programmiersprache\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"));
    }

    @Test
    public void testGetKarteikartenByKarteibox() throws Exception {
        Karteikarte karteikarte = new Karteikarte();
        karteikarte.setId("1");

        List<Karteikarte> karteikartenListe = Arrays.asList(karteikarte);
        when(karteikarteService.getKarteikartenByKarteibox("karteibox123")).thenReturn(karteikartenListe);

        mockMvc.perform(get("/karteikarten/karteibox/karteibox123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"));
    }

    @Test
    public void testDeleteKarteikarte() throws Exception {
        doNothing().when(karteikarteService).deleteKarteikarte("1");

        mockMvc.perform(delete("/karteikarten/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testMarkCardAsKnown() throws Exception {
        Karteikarte karteikarte = new Karteikarte();
        karteikarte.setId("1");
        karteikarte.setKnown(true);

        when(karteikarteService.markCardAsKnown("1")).thenReturn(karteikarte);

        mockMvc.perform(post("/karteikarten/1/markAsKnown"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.known").value(true));
    }

    @Test
    public void testUpdateKarteikarte() throws Exception {
        Karteikarte updatedKarteikarte = new Karteikarte();
        updatedKarteikarte.setId("1");
        updatedKarteikarte.setFrage("Neue Frage");

        when(karteikarteService.updateKarteikarte(eq("1"), any(Karteikarte.class))).thenReturn(updatedKarteikarte);

        mockMvc.perform(put("/karteikarten/1")
                .contentType(APPLICATION_JSON)
                .content("{\"frage\":\"Neue Frage\",\"antwort\":\"Neue Antwort\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.frage").value("Neue Frage"));
    }
}
