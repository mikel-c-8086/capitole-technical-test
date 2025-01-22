package com.example.capitoletechnicaltest.domain;

import com.example.capitoletechnicaltest.entity.Price;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Port interface for accessing price data.
 * Abstracts the repository layer to align with a hexagonal architecture.
 */
public interface PricePersistence {
    List<Price> findApplicablePrices(int brandId, int productId, LocalDateTime applicationDate);
}
