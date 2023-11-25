package com.lernkartenapp.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.lernkartenapp.model.Karteikarte;
import com.lernkartenapp.repository.KarteikarteRepository;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class KarteikarteServiceTest {

    @InjectMocks
    private KarteikarteService karteikarteService;

    @Mock
    private KarteikarteRepository karteikarteRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateKarteikarte() {
        Karteikarte karteikarte = new Karteikarte();
        when(karteikarteRepository.save(karteikarte)).thenReturn(karteikarte);
    
        Karteikarte result = karteikarteService.createKarteikarte(karteikarte);
    
        assertNotNull(result);
        verify(karteikarteRepository, times(1)).save(karteikarte);
    }

    @Test
    public void testGetKarteikartenByKarteibox() {
        String karteiboxId = "karteibox123";
        when(karteikarteRepository.findAllByKarteiboxId(karteiboxId)).thenReturn(Arrays.asList(new Karteikarte()));
    
        List<Karteikarte> result = karteikarteService.getKarteikartenByKarteibox(karteiboxId);
    
        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(karteikarteRepository, times(1)).findAllByKarteiboxId(karteiboxId);
    }

    @Test
    public void testDeleteKarteikartenByKarteibox() {
        String karteiboxId = "karteibox123";
        when(karteikarteRepository.findAllByKarteiboxId(karteiboxId)).thenReturn(Arrays.asList(new Karteikarte()));

        karteikarteService.deleteKarteikartenByKarteibox(karteiboxId);

        verify(karteikarteRepository, times(1)).findAllByKarteiboxId(karteiboxId);
        verify(karteikarteRepository, times(1)).deleteAll(anyList());
    }

    @Test
    public void testDeleteKarteikarte() {
        String karteikarteId = "1";
        doNothing().when(karteikarteRepository).deleteById(karteikarteId);
    
        karteikarteService.deleteKarteikarte(karteikarteId);
    
        verify(karteikarteRepository, times(1)).deleteById(karteikarteId);
    }

    @Test
    public void testMarkCardAsKnown() {
        String karteikarteId = "1";
        Karteikarte karteikarte = new Karteikarte();
        karteikarte.setId(karteikarteId);
    
        when(karteikarteRepository.findById(karteikarteId)).thenReturn(Optional.of(karteikarte));
        when(karteikarteRepository.save(karteikarte)).thenReturn(karteikarte);
    
        Karteikarte result = karteikarteService.markCardAsKnown(karteikarteId);
    
        assertTrue(result.getKnown());
        verify(karteikarteRepository, times(1)).save(karteikarte);
    }
    
    @Test
    public void testUpdateKarteikarte() {
        String karteikarteId = "1";
        Karteikarte existingKarteikarte = new Karteikarte();
        Karteikarte updatedKarteikarte = new Karteikarte();
        updatedKarteikarte.setFrage("Neue Frage");

        when(karteikarteRepository.findById(karteikarteId)).thenReturn(Optional.of(existingKarteikarte));
        when(karteikarteRepository.save(any(Karteikarte.class))).thenReturn(updatedKarteikarte);

        Karteikarte result = karteikarteService.updateKarteikarte(karteikarteId, updatedKarteikarte);

        assertEquals("Neue Frage", result.getFrage());
        verify(karteikarteRepository, times(1)).save(any(Karteikarte.class));
    }

    @Test
    public void testMarkCardAsKnownNotFound() {
        String karteikarteId = "nonexistent";
        when(karteikarteRepository.findById(karteikarteId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> karteikarteService.markCardAsKnown(karteikarteId));
    }

    @Test
    public void testUpdateKarteikarteNotFound() {
        String karteikarteId = "nonexistent";
        Karteikarte updatedKarteikarte = new Karteikarte();

        when(karteikarteRepository.findById(karteikarteId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> karteikarteService.updateKarteikarte(karteikarteId, updatedKarteikarte));
    }

}
