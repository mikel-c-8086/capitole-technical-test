package com.example.capitoletechnicaltest.domain;

import com.example.capitoletechnicaltest.adapters.outbound.PriceRepository;
import com.example.capitoletechnicaltest.ports.PriceRepositoryPort;
import com.example.capitoletechnicaltest.ports.PriceServicePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Implementation of the {@link PriceServicePort} interface.
 * Provides the logic to determine the applicable price for a product
 * based on brand, product ID, and application date.
 */
@Service
public class PriceServiceImpl implements PriceServicePort {

    /**
     * Dependency for accessing price data persistence operations.
     */
    private final PriceRepository priceRepository;

    /**
     * Constructor for SimplePriceService.
     * Utilizes dependency injection to provide the required {@link PriceRepository} implementation.
     *
     * @param priceRepository The persistence layer for managing price data.
     */
    public PriceServiceImpl(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    /**
     * Retrieves the applicable price for a given product, brand, and application date.
     * The method fetches prices from the database, applies filters for the given criteria,
     * and selects the price with the highest priority if multiple matches are found.
     *
     * @param productId       The ID of the product for which the price is requested.
     * @param brandId         The ID of the brand associated with the price.
     * @param applicationDate The date and time for which the price is applicable.
     * @return An {@link Optional} containing the applicable {@link Price} if found,
     * or an empty Optional if no price matches the criteria.
     */
    @Override
    public Optional<Price> findPrice(Long productId, Long brandId, LocalDateTime applicationDate) {
        return priceRepository
                .findByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
                        brandId, productId, applicationDate, applicationDate)
                .stream()
                .findFirst()
                .or(() -> {
                    throw new ResourceNotFoundException("No applicable price found for the given criteria.");
                });
    }
}
