package com.example.capitoletechnicaltest.service.impl;

import com.example.capitoletechnicaltest.entity.Price;
import com.example.capitoletechnicaltest.repository.PriceRepository;
import com.example.capitoletechnicaltest.service.PriceService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Implementation of the {@link PriceService} interface.
 * Provides the logic to determine the applicable price for a product
 * based on brand, product ID, and application date.
 */
@Service
public class SimplePriceService implements PriceService {

    /**
     * Repository for accessing and managing {@link Price} entities.
     */
    private final PriceRepository priceRepository;

    /**
     * Constructs a new {@link SimplePriceService} with the specified {@link PriceRepository}.
     *
     * @param priceRepository The repository for accessing price data.
     */
    public SimplePriceService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    /**
     * Retrieves the applicable price for a given product, brand, and application date.
     * The method fetches prices from the database, applies filters for the given criteria,
     * and selects the price with the highest priority if multiple matches are found.
     *
     * @param brandId         The ID of the brand associated with the price.
     * @param productId       The ID of the product for which the price is requested.
     * @param applicationDate The date and time for which the price is applicable.
     * @return An {@link Optional} containing the applicable {@link Price} if found,
     *         or an empty Optional if no price matches the criteria.
     */
    @Override
    public Optional<Price> getApplicablePrice(int brandId, int productId, LocalDateTime applicationDate) {
        // Fetch prices matching the criteria, ordered by priority (highest first),
        // and return the first match.
        return priceRepository
                .findByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
                        brandId, productId, applicationDate, applicationDate)
                .stream()
                .findFirst();
    }
}
