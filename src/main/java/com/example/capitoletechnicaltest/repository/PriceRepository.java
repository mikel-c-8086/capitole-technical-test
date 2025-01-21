package com.example.capitoletechnicaltest.repository;

import com.example.capitoletechnicaltest.entity.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for managing Price entities.
 * Extends JpaRepository to provide CRUD operations and custom queries.
 */
@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {

    /**
     * Retrieves a list of Price entries based on brand ID, product ID,
     * and an application date that falls within the start and end date range.
     * Results are ordered by priority in descending order.
     *
     * @param brandId         The ID of the brand.
     * @param productId       The ID of the product.
     * @param applicationDate The date and time for which the price is applicable.
     * @param endDate         The end date range.
     * @return A list of Price entities matching the criteria.
     */
    List<Price> findByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
            int brandId, int productId, LocalDateTime applicationDate, LocalDateTime endDate
    );

}
