package com.example.capitoletechnicaltest;

import com.example.capitoletechnicaltest.entity.Price;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PriceControllerTests {

    @Autowired
    private TestRestTemplate restTemplate;

    private final String baseUrl = "/api/prices";

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    @Test
    public void test1() {
        ResponseEntity<Price> response = restTemplate.getForEntity(
                baseUrl + "?productId=35455&brandId=1&applicationDate=2020-06-14T10:00:00",
                Price.class
        );

        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getBrandId());
        assertEquals(35455, response.getBody().getProductId());
        assertEquals(new BigDecimal("35.50"), response.getBody().getPrice());
    }

    @Test
    public void test2() {
        ResponseEntity<Price> response = restTemplate.getForEntity(
                baseUrl + "?productId=35455&brandId=1&applicationDate=2020-06-14T16:00:00",
                Price.class
        );

        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getBrandId());
        assertEquals(35455, response.getBody().getProductId());
        assertEquals(new BigDecimal("25.45"), response.getBody().getPrice());
    }

    @Test
    public void test3() {
        ResponseEntity<Price> response = restTemplate.getForEntity(
                baseUrl + "?productId=35455&brandId=1&applicationDate=2020-06-14T21:00:00",
                Price.class
        );

        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getBrandId());
        assertEquals(35455, response.getBody().getProductId());
        assertEquals(new BigDecimal("35.50"), response.getBody().getPrice());
    }

    @Test
    public void test4() {
        ResponseEntity<Price> response = restTemplate.getForEntity(
                baseUrl + "?productId=35455&brandId=1&applicationDate=2020-06-15T10:00:00",
                Price.class
        );

        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getBrandId());
        assertEquals(35455, response.getBody().getProductId());
        assertEquals(new BigDecimal("30.50"), response.getBody().getPrice());
    }

    @Test
    public void test5() {
        ResponseEntity<Price> response = restTemplate.getForEntity(
                baseUrl + "?productId=35455&brandId=1&applicationDate=2020-06-16T21:00:00",
                Price.class
        );

        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getBrandId());
        assertEquals(35455, response.getBody().getProductId());
        assertEquals(new BigDecimal("38.95"), response.getBody().getPrice());
    }
}