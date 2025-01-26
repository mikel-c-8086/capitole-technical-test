package com.example.capitoletechnicaltest.domain;

import com.example.capitoletechnicaltest.adapters.inbound.PriceResponseDTO;
import com.example.capitoletechnicaltest.adapters.outbound.PriceRepository;
import com.example.capitoletechnicaltest.adapters.outbound.PriceRepositoryImpl;
import com.example.capitoletechnicaltest.ports.PriceRepositoryPort;
import com.example.capitoletechnicaltest.ports.PriceServicePort;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link PriceServiceImpl}.
 * This class validates the behavior of the service logic for retrieving applicable prices.
 */
class PriceServiceImplTest {

    /**
     * Test case for when no price is found for the given criteria.
     * Verifies that the service correctly throws a {@link ResourceNotFoundException}.
     */
    @Test
    void testFindPriceByProductBrandAndDate_WhenNoPriceFound() {
        // Mock de PriceRepository
        PriceRepository mockPriceRepository = mock(PriceRepository.class);

        // Configurar el mock para devolver una lista vacía
        when(mockPriceRepository.findByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
                anyLong(), anyLong(), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Collections.emptyList());

        // Instancia real de PriceRepositoryImpl
        PriceRepositoryPort priceRepositoryPort = new PriceRepositoryImpl(mockPriceRepository);

        // Arrange
        Long brandId = 1L;
        Long productId = 100L;
        LocalDateTime applicationDate = LocalDateTime.of(2023, 12, 25, 15, 0);

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            priceRepositoryPort.findPriceByProductBrandAndDate(productId, brandId, applicationDate);
        });

        assertEquals("No applicable price found for the given criteria.", exception.getMessage());
    }


    /**
     * Test case for when a price is found for the given criteria.
     * Verifies that the service returns the expected {@link PriceResponseDTO}.
     */
    @Test
    void testGetApplicablePrice_WhenPriceFound() {
        // Service instance with a predefined stubbed repository
        PriceServicePort priceService = getPriceService();

        // Arrange
        Long brandId = 1L;
        Long productId = 100L;
        LocalDateTime applicationDate = LocalDateTime.of(2023, 12, 25, 15, 0);

        // Act
        Optional<PriceResponseDTO> result = priceService.findPrice(productId, brandId, applicationDate)
                .map(PriceResponseDTO::fromDomain);

        // Assert
        assertTrue(result.isPresent(), "Expected a price to be found");
        PriceResponseDTO dto = result.get();
        assertEquals(productId, dto.getProductId());
        assertEquals(brandId, dto.getBrandId());
        assertEquals(new BigDecimal("35.50"), dto.getAmount());
        assertEquals("EUR", dto.getCurr());
    }

    /**
     * Helper method to create an instance of {@link PriceServiceImpl} with a predefined stubbed repository.
     *
     * @return A PriceServiceImpl instance with a stubbed {@link PriceRepositoryPort}.
     */
    private static PriceServiceImpl getPriceService() {
        PriceRepositoryPort priceRepositoryPort = (productId, brandId, applicationDate) -> {
            Price price = new Price();
            price.setBrandId(brandId);
            price.setProductId(productId);
            price.setAmount(new BigDecimal("35.50"));
            price.setCurr("EUR");
            price.setStartDate(LocalDateTime.of(2023, 12, 24, 0, 0));
            price.setEndDate(LocalDateTime.of(2023, 12, 26, 23, 59));
            return Optional.of(price);
        };

        return new PriceServiceImpl(priceRepositoryPort);
    }
}
