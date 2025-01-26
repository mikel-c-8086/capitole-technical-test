package com.example.capitoletechnicaltest.adapters.outbound;

import com.example.capitoletechnicaltest.domain.Price;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Integration tests for {@link PriceRepository}.
 * Validates that the custom query works as expected with a database.
 */
@DataJpaTest
class PriceRepositoryTest {

    @Autowired
    private PriceRepository priceRepository;

    @Test
    void testFindByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc() {
        // Arrange: Insert test data into the H2 database
        Price price1 = new Price();
        price1.setBrandId(1L);
        price1.setProductId(100L);
        price1.setAmount(new BigDecimal("50.00"));
        price1.setCurr("USD");
        price1.setStartDate(LocalDateTime.of(2023, 1, 1, 0, 0));
        price1.setEndDate(LocalDateTime.of(2023, 12, 31, 23, 59));
        price1.setPriority(1);

        Price price2 = new Price();
        price2.setBrandId(1L);
        price2.setProductId(100L);
        price2.setAmount(new BigDecimal("35.50"));
        price2.setCurr("USD");
        price2.setStartDate(LocalDateTime.of(2023, 1, 1, 0, 0));
        price2.setEndDate(LocalDateTime.of(2023, 12, 31, 23, 59));
        price2.setPriority(2);

        priceRepository.save(price1);
        priceRepository.save(price2);

        // Act: Execute the custom query
        List<Price> results =
                priceRepository.findByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
                        1L, 100L, LocalDateTime.of(2023, 6, 15, 12, 0), LocalDateTime.of(2023, 6, 15, 12, 0)
                );

        // Assert: Validate the results
        assertEquals(2, results.size(), "Expected two prices to be returned");
        assertEquals(2, results.get(0).getPriority(), "Expected the highest priority price to be returned first");
        assertEquals(1, results.get(1).getPriority(), "Expected the lower priority price to be returned second");
        assertTrue(results.stream().allMatch(price -> price.getBrandId().equals(1L)),
                "All prices should match the brand ID");
        assertTrue(results.stream().allMatch(price -> price.getProductId().equals(100L)),
                "All prices should match the product ID");
    }
}
