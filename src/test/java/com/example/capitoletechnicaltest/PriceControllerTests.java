package com.example.capitoletechnicaltest;

import com.example.capitoletechnicaltest.dto.PriceResponseDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class contains integration tests for the PriceController.
 * It uses Spring's TestRestTemplate to simulate HTTP requests to the API.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PriceControllerTests {

    @Autowired
    private TestRestTemplate restTemplate; // Injects a TestRestTemplate to perform HTTP requests.

    private final String baseUrl = "/api/prices"; // Base URL for the PriceController endpoints.

    @ParameterizedTest
    @CsvSource({
            "2020-06-14T10:00:00, 35455, 1, 35.50",
            "2020-06-14T16:00:00, 35455, 1, 25.45",
            "2020-06-14T21:00:00, 35455, 1, 35.50",
            "2020-06-15T10:00:00, 35455, 1, 30.50",
            "2020-06-16T21:00:00, 35455, 1, 38.95"
    })
    void testGetPrice(String applicationDate, int productId, int brandId, BigDecimal expectedAmount) {
        ResponseEntity<PriceResponseDTO> response = restTemplate.getForEntity(
                baseUrl + "?productId=" + productId + "&brandId=" + brandId + "&applicationDate=" + applicationDate,
                PriceResponseDTO.class
        );

        assertNotNull(response.getBody(), "Response body should not be null");
        assertEquals(brandId, response.getBody().getBrandId(), "Brand ID mismatch");
        assertEquals(productId, response.getBody().getProductId(), "Product ID mismatch");
        assertEquals(expectedAmount, response.getBody().getAmount(), "Price amount mismatch");
    }

    /**
     * Tests the API response for a request with no applicable price.
     */
    @Test
    void testGetApplicablePrice_noPriceFound() {
        ResponseEntity<String> response = restTemplate.getForEntity(
                baseUrl + "?productId=99999&brandId=1&applicationDate=2020-06-14T10:00:00",
                String.class
        );

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().contains("No applicable price found"));
    }

    /**
     * Tests the API response for a request with invalid input.
     */
    @Test
    void testGetApplicablePrice_invalidInput() {
        ResponseEntity<String> response = restTemplate.getForEntity(
                baseUrl + "?productId=invalid&brandId=1&applicationDate=2020-06-14T10:00:00",
                String.class
        );

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().contains("required type 'int'"));
    }

}