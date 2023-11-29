package com.lernkartenapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class KarteikartenAppApplicationTests {

    @Autowired
    private ApplicationContext context;

    @Test
    public void contextLoads() {
        // Test, ob der Spring-Anwendungskontext erfolgreich geladen wird
        assertNotNull(context, "Der Anwendungskontext sollte nicht null sein");
    }
}
