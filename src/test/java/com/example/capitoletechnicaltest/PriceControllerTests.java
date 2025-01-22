package com.example.capitoletechnicaltest;

import com.example.capitoletechnicaltest.dto.PriceResponseDTO;
import org.junit.jupiter.api.Test;
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
public class PriceControllerTests {

    @Autowired
    private TestRestTemplate restTemplate; // Injects a TestRestTemplate to perform HTTP requests.

    private final String baseUrl = "/api/prices"; // Base URL for the PriceController endpoints.

    /**
     * Tests the API response for a specific date and time (2020-06-14T10:00:00).
     * Verifies that the price, productId, and brandId match the expected values.
     */
    @Test
    public void test1() {
        ResponseEntity<PriceResponseDTO> response = restTemplate.getForEntity(
                baseUrl + "?productId=35455&brandId=1&applicationDate=2020-06-14T10:00:00",
                PriceResponseDTO.class
        );

        assertNotNull(response.getBody()); // Ensure the response body is not null.
        assertEquals(1, response.getBody().getBrandId()); // Verify the brandId.
        assertEquals(35455, response.getBody().getProductId()); // Verify the productId.
        assertEquals(new BigDecimal("35.50"), response.getBody().getPrice()); // Verify the price.
    }

    /**
     * Tests the API response for a specific date and time (2020-06-14T16:00:00).
     * Verifies that the price, productId, and brandId match the expected values.
     */
    @Test
    public void test2() {
        ResponseEntity<PriceResponseDTO> response = restTemplate.getForEntity(
                baseUrl + "?productId=35455&brandId=1&applicationDate=2020-06-14T16:00:00",
                PriceResponseDTO.class
        );

        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getBrandId());
        assertEquals(35455, response.getBody().getProductId());
        assertEquals(new BigDecimal("25.45"), response.getBody().getPrice());
    }

    /**
     * Tests the API response for a specific date and time (2020-06-14T21:00:00).
     * Verifies that the price, productId, and brandId match the expected values.
     */
    @Test
    public void test3() {
        ResponseEntity<PriceResponseDTO> response = restTemplate.getForEntity(
                baseUrl + "?productId=35455&brandId=1&applicationDate=2020-06-14T21:00:00",
                PriceResponseDTO.class
        );

        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getBrandId());
        assertEquals(35455, response.getBody().getProductId());
        assertEquals(new BigDecimal("35.50"), response.getBody().getPrice());
    }

    /**
     * Tests the API response for a specific date and time (2020-06-15T10:00:00).
     * Verifies that the price, productId, and brandId match the expected values.
     */
    @Test
    public void test4() {
        ResponseEntity<PriceResponseDTO> response = restTemplate.getForEntity(
                baseUrl + "?productId=35455&brandId=1&applicationDate=2020-06-15T10:00:00",
                PriceResponseDTO.class
        );

        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getBrandId());
        assertEquals(35455, response.getBody().getProductId());
        assertEquals(new BigDecimal("30.50"), response.getBody().getPrice());
    }

    /**
     * Tests the API response for a specific date and time (2020-06-16T21:00:00).
     * Verifies that the price, productId, and brandId match the expected values.
     */
    @Test
    public void test5() {
        ResponseEntity<PriceResponseDTO> response = restTemplate.getForEntity(
                baseUrl + "?productId=35455&brandId=1&applicationDate=2020-06-16T21:00:00",
                PriceResponseDTO.class
        );

        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getBrandId());
        assertEquals(35455, response.getBody().getProductId());
        assertEquals(new BigDecimal("38.95"), response.getBody().getPrice());
    }

    /**
     * Tests the API response for a request with no applicable price.
     */
    @Test
    public void testGetApplicablePrice_noPriceFound() {
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
    public void testGetApplicablePrice_invalidInput() {
        ResponseEntity<String> response = restTemplate.getForEntity(
                baseUrl + "?productId=invalid&brandId=1&applicationDate=2020-06-14T10:00:00",
                String.class
        );

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().contains("required type 'int'"));
    }

}