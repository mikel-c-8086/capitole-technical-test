package com.example.capitoletechnicaltest.service.impl;

import com.example.capitoletechnicaltest.entity.Price;
import com.example.capitoletechnicaltest.repository.PriceRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class SimplePriceServiceTest {

    private SimplePriceService simplePriceService;

    private AutoCloseable autoCloseable;

    @Mock
    private PriceRepository priceRepository;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        simplePriceService = new SimplePriceService(priceRepository);
    }

    @AfterEach
    void cleanUp() throws Exception {
        autoCloseable.close();
    }

    @Test
    void testGetApplicablePrice_whenPriceExists() {
        // Arrange
        int brandId = 1;
        int productId = 35455;
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);
        Price mockPrice = new Price();
        mockPrice.setId(1L);
        mockPrice.setBrandId(brandId);
        mockPrice.setProductId(productId);
        mockPrice.setStartDate(LocalDateTime.of(2020, 6, 14, 0, 0));
        mockPrice.setEndDate(LocalDateTime.of(2020, 6, 14, 23, 59));
        mockPrice.setPriceList(1);
        mockPrice.setPriority(1);
        mockPrice.setPrice(BigDecimal.valueOf(35.50));
        mockPrice.setCurr("EUR");

        when(priceRepository.findByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
                brandId, productId, applicationDate, applicationDate)).thenReturn(List.of(mockPrice));

        // Act
        Optional<Price> result = simplePriceService.getApplicablePrice(brandId, productId, applicationDate);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(mockPrice, result.get());
        verify(priceRepository, times(1)).findByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
                brandId, productId, applicationDate, applicationDate);
    }

    @Test
    void testGetApplicablePrice_whenNoPriceExists() {
        // Arrange
        int brandId = 1;
        int productId = 35455;
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);

        when(priceRepository.findByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
                brandId, productId, applicationDate, applicationDate)).thenReturn(List.of());

        // Act
        Optional<Price> result = simplePriceService.getApplicablePrice(brandId, productId, applicationDate);

        // Assert
        assertTrue(result.isEmpty());
        verify(priceRepository, times(1)).findByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
                brandId, productId, applicationDate, applicationDate);
    }
}