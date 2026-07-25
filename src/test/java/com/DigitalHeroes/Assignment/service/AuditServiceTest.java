package com.DigitalHeroes.Assignment.service;



import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.DigitalHeroes.Assignment.dto.AuditResponse;

public class AuditServiceTest {

    private final AuditService auditService = new AuditService();

    @Test
    void testValidUrl() {

        AuditResponse response = auditService.audit("https://example.com");

        assertEquals(200, response.getStatus());
        assertEquals("Example Domain", response.getTitle());
        assertTrue(response.getWordCount() > 0);
        assertTrue(response.getH1Count() > 0);
    }
    @Test
    void testInvalidUrl() {

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> auditService.audit("google.com"));

        assertEquals("Invalid URL.", exception.getMessage());
    }
    @Test
    void testNonHtmlUrl() {

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> auditService.audit(
                    "https://upload.wikimedia.org/wikipedia/commons/4/47/PNG_transparency_demonstration_1.png"));

        assertEquals("URL does not return HTML content.", exception.getMessage());
    }
}