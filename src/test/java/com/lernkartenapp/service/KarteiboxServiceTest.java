package com.lernkartenapp.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.lernkartenapp.model.Karteibox;
import com.lernkartenapp.model.Karteikarte;
import com.lernkartenapp.repository.KarteiboxRepository;
import com.lernkartenapp.repository.KarteikarteRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class KarteiboxServiceTest {

    @InjectMocks
    private KarteiboxService karteiboxService;

    @Mock
    private KarteiboxRepository karteiboxRepository;

    @Mock
    private KarteikarteService karteikarteService;

    @Mock
    private KarteikarteRepository karteikarteRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateKarteibox() {
        Karteibox karteibox = new Karteibox();
        when(karteiboxRepository.save(karteibox)).thenReturn(karteibox);

        Karteibox result = karteiboxService.createKarteibox(karteibox);

        assertNotNull(result);
        verify(karteiboxRepository, times(1)).save(karteibox);
    }

    @Test
    public void testGetKarteiboxenByUser() {
        String userId = "user123";
        when(karteiboxRepository.findAllByUserId(userId)).thenReturn(Arrays.asList(new Karteibox()));

        List<Karteibox> result = karteiboxService.getKarteiboxenByUser(userId);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(karteiboxRepository, times(1)).findAllByUserId(userId);
    }

    @Test
    public void testDeleteKarteiboxenByUser() {
        String userId = "user123";
        Karteibox karteibox = new Karteibox();
        karteibox.setId("karteibox123");
    
        when(karteiboxRepository.findAllByUserId(userId)).thenReturn(Arrays.asList(karteibox));
    
        karteiboxService.deleteKarteiboxenByUser(userId);
    
        verify(karteikarteService, times(1)).deleteKarteikartenByKarteibox("karteibox123");
        verify(karteiboxRepository, times(1)).delete(karteibox);
    }

    @Test
    public void testDeleteKarteibox() {
        String karteiboxId = "karteibox123";
    
        doNothing().when(karteikarteService).deleteKarteikartenByKarteibox(karteiboxId);
        doNothing().when(karteiboxRepository).deleteById(karteiboxId);
    
        karteiboxService.deleteKarteibox(karteiboxId);
    
        verify(karteikarteService, times(1)).deleteKarteikartenByKarteibox(karteiboxId);
        verify(karteiboxRepository, times(1)).deleteById(karteiboxId);
    }
 
    @Test
    public void testCalculateProgress() {
        String karteiboxId = "karteibox123";
        Karteikarte knownCard = new Karteikarte();
        knownCard.setKnown(true);
    
        when(karteikarteRepository.findAllByKarteiboxId(karteiboxId)).thenReturn(Arrays.asList(knownCard, new Karteikarte()));
    
        double progress = karteiboxService.calculateProgress(karteiboxId);
    
        assertEquals(50.0, progress);
    }

    @Test
    public void testDeleteKarteiboxNotFound() {
        String karteiboxId = "nonexistent";
        when(karteikarteRepository.findAllByKarteiboxId(karteiboxId)).thenReturn(Arrays.asList());
        doNothing().when(karteiboxRepository).deleteById(karteiboxId);

        assertDoesNotThrow(() -> karteiboxService.deleteKarteibox(karteiboxId));
        verify(karteikarteService, times(1)).deleteKarteikartenByKarteibox(karteiboxId);
        verify(karteiboxRepository, times(1)).deleteById(karteiboxId);
    }

    @Test
    public void testCalculateProgressNoCards() {
        String karteiboxId = "karteibox123";
        when(karteikarteRepository.findAllByKarteiboxId(karteiboxId)).thenReturn(Arrays.asList());

        double progress = karteiboxService.calculateProgress(karteiboxId);

        assertEquals(0.0, progress);
    }
    
}
