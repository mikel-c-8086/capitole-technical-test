package com.example.capitoletechnicaltest.ports;

import com.example.capitoletechnicaltest.domain.Price;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PriceRepositoryPort {
    Optional<Price> findPriceByProductBrandAndDate(Long productId, Long brandId, LocalDateTime applicationDate);
}
