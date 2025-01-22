package com.example.capitoletechnicaltest.service.impl;

import com.example.capitoletechnicaltest.domain.PricePersistence;
import com.example.capitoletechnicaltest.dto.PriceResponseDTO;
import com.example.capitoletechnicaltest.entity.Price;
import com.example.capitoletechnicaltest.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for {@link SimplePriceService}.
 * This class validates the behavior of the service logic for retrieving applicable prices.
 */
class SimplePriceServiceTest {

    /**
     * Test case for when no price is found for the given criteria.
     * Verifies that the service correctly returns an empty Optional.
     */
    @Test
    void testGetApplicablePrice_WhenNoPriceFound() {
        // Stub de PricePersistence
        PricePersistence pricePersistence = (brandId, productId, applicationDate) -> Collections.emptyList();

        // Instancia del servicio con el stub
        SimplePriceService simplePriceService = new SimplePriceService(pricePersistence);

        // Arrange
        int brandId = 1;
        int productId = 100;
        LocalDateTime applicationDate = LocalDateTime.of(2023, 12, 25, 15, 0);

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            simplePriceService.getApplicablePrice(brandId, productId, applicationDate);
        });

        assertEquals("No applicable price found for the given criteria.", exception.getMessage());
    }

    /**
     * Test case for when a price is found for the given criteria.
     * Verifies that the service returns the expected PriceResponseDTO.
     */
    @Test
    void testGetApplicablePrice_WhenPriceFound() {
        // Stub de PricePersistence
        SimplePriceService simplePriceService = getSimplePriceService();

        // Arrange
        int brandId = 1;
        int productId = 100;
        LocalDateTime applicationDate = LocalDateTime.of(2023, 12, 25, 15, 0);

        // Act
        Optional<PriceResponseDTO> result = simplePriceService.getApplicablePrice(brandId, productId, applicationDate);

        // Assert
        assertTrue(result.isPresent(), "Expected a price to be found");
        PriceResponseDTO dto = result.get();
        assertEquals(productId, dto.getProductId());
        assertEquals(brandId, dto.getBrandId());
        assertEquals(new BigDecimal("35.50"), dto.getAmount());
        assertEquals("EUR", dto.getCurr());
    }

    /**
     * Helper method to create an instance of {@link SimplePriceService} with a predefined stubbed persistence layer.
     *
     * @return A SimplePriceService instance with a stubbed {@link PricePersistence}.
     */
    private static SimplePriceService getSimplePriceService() {
        PricePersistence pricePersistence = (brandId, productId, applicationDate) -> {
            Price price = new Price();
            price.setBrandId(brandId);
            price.setProductId(productId);
            price.setAmount(new BigDecimal("35.50"));
            price.setCurr("EUR");
            price.setStartDate(LocalDateTime.of(2023, 12, 24, 0, 0));
            price.setEndDate(LocalDateTime.of(2023, 12, 26, 23, 59));
            return List.of(price);
        };

        return new SimplePriceService(pricePersistence);
    }

}
