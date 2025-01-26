package com.example.capitoletechnicaltest.adapters.inbound;

import com.example.capitoletechnicaltest.domain.Price;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for {@link PriceResponseDTO}.
 * This class validates the behavior of the DTO transformation logic.
 */
class PriceResponseDTOTest {

    /**
     * Test case for the static method {@link PriceResponseDTO#fromDomain}.
     * Verifies that the transformation from {@link Price} to {@link PriceResponseDTO} is accurate.
     */
    @Test
    void testFromDomain() {
        // Arrange
        Price price = new Price();
        price.setProductId(100L);
        price.setBrandId(1L);
        price.setAmount(new BigDecimal("35.50"));
        price.setCurr("EUR");
        price.setStartDate(LocalDateTime.of(2023, 12, 24, 0, 0));
        price.setEndDate(LocalDateTime.of(2023, 12, 26, 23, 59));

        // Act
        PriceResponseDTO dto = PriceResponseDTO.fromDomain(price);

        // Assert
        assertEquals(price.getProductId(), dto.getProductId());
        assertEquals(price.getBrandId(), dto.getBrandId());
        assertEquals(price.getAmount(), dto.getAmount());
        assertEquals(price.getCurr(), dto.getCurr());
        assertEquals(price.getStartDate(), dto.getStartDate());
        assertEquals(price.getEndDate(), dto.getEndDate());
    }

    /**
     * Test case for the default constructor and setters.
     * Verifies that the properties can be set and retrieved correctly.
     */
    @Test
    void testSettersAndGetters() {
        // Arrange
        PriceResponseDTO dto = new PriceResponseDTO();
        dto.setProductId(100L);
        dto.setBrandId(1L);
        dto.setAmount(new BigDecimal("35.50"));
        dto.setCurr("USD");
        dto.setStartDate(LocalDateTime.of(2023, 12, 24, 0, 0));
        dto.setEndDate(LocalDateTime.of(2023, 12, 26, 23, 59));

        // Assert
        assertEquals(100L, dto.getProductId());
        assertEquals(1L, dto.getBrandId());
        assertEquals(new BigDecimal("35.50"), dto.getAmount());
        assertEquals("USD", dto.getCurr());
        assertEquals(LocalDateTime.of(2023, 12, 24, 0, 0), dto.getStartDate());
        assertEquals(LocalDateTime.of(2023, 12, 26, 23, 59), dto.getEndDate());
    }

}
