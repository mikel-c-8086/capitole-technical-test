package com.example.capitoletechnicaltest.service.impl;

import com.example.capitoletechnicaltest.domain.PricePersistence;
import com.example.capitoletechnicaltest.dto.PriceResponseDTO;
import com.example.capitoletechnicaltest.entity.Price;
import com.example.capitoletechnicaltest.exception.ResourceNotFoundException;
import com.example.capitoletechnicaltest.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
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
     * Dependency for accessing price data persistence operations.
     */
    private final PricePersistence pricePersistence;

    /**
     * Constructor for SimplePriceService.
     * Utilizes dependency injection to provide the required {@link PricePersistence} implementation.
     *
     * @param pricePersistence The persistence layer for managing price data.
     */
    @Autowired
    public SimplePriceService(PricePersistence pricePersistence) {
        this.pricePersistence = pricePersistence;
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
    public Optional<PriceResponseDTO> getApplicablePrice(int brandId, int productId, LocalDateTime applicationDate) {
        return pricePersistence.findApplicablePrices(brandId, productId, applicationDate)
                .stream()
                .findFirst()
                .map(price -> new PriceResponseDTO(
                        price.getProductId(),
                        price.getBrandId(),
                        price.getAmount(),
                        price.getCurr(),
                        price.getStartDate(),
                        price.getEndDate()
                )).or(() -> {
                    throw new ResourceNotFoundException("No applicable price found for the given criteria.");
                });
    }

}
