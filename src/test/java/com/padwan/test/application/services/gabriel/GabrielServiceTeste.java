package com.padwan.test.application.services.gabriel;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class GabrielServiceTest {

    private final GabrielService gabrielService = new GabrielService();

    @Test
    void testSkills() {
        List<String> skills = gabrielService.skills();

        assertNotNull(skills, "A lista de skills não deve ser nula");

        assertEquals(8, skills.size(), "A lista deve conter 8 elementos");

        assertTrue(skills.contains("java"), "A lista deve conter 'java'");
        assertTrue(skills.contains("spring"), "A lista deve conter 'spring'");
        assertTrue(skills.contains("spring boot"), "A lista deve conter 'spring boot'");
        assertTrue(skills.contains("javascript"), "A lista deve conter 'javascript'");
        assertTrue(skills.contains("node.js"), "A lista deve conter 'node.js'");
        assertTrue(skills.contains("postgre"), "A lista deve conter 'postgre'");
        assertTrue(skills.contains("mysql"), "A lista deve conter 'mysql'");
        assertTrue(skills.contains("mongodb"), "A lista deve conter 'mongodb'");
    }
}