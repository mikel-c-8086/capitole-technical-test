package com.example.capitoletechnicaltest.service;

import com.example.capitoletechnicaltest.dto.PriceResponseDTO;
import com.example.capitoletechnicaltest.entity.Price;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Service interface for managing and retrieving pricing information.
 * Defines the business logic for determining the applicable price
 * based on the input criteria.
 */
public interface PriceService {

    /**
     * Retrieves the applicable price for a specific product, brand, and application date.
     * This method filters the prices based on the provided criteria and applies
     * business rules such as priority to determine the correct price.
     *
     * @param brandId         The ID of the brand associated with the price.
     * @param productId       The ID of the product for which the price is requested.
     * @param applicationDate The date and time for which the price is applicable.
     * @return An {@link Optional} containing the applicable {@link Price} if found,
     *         or an empty Optional if no price matches the criteria.
     */
    Optional<PriceResponseDTO> getApplicablePrice(int brandId, int productId, LocalDateTime applicationDate);
}
