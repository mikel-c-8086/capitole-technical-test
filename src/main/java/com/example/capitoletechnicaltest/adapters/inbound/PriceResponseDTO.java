package com.example.capitoletechnicaltest.adapters.inbound;

import com.example.capitoletechnicaltest.domain.Price;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for price response.
 */
public class PriceResponseDTO {
    private Long productId;
    private Long brandId;
    private BigDecimal amount;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String curr;

    // Constructors
    public PriceResponseDTO() {
        // Empty constructor
    }

    public PriceResponseDTO(Long productId, Long brandId, BigDecimal price, String curr, LocalDateTime startDate,
            LocalDateTime endDate) {
        this.productId = productId;
        this.brandId = brandId;
        this.amount = price;
        this.curr = curr;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Getters and Setters
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getCurr() {
        return curr;
    }

    public void setCurr(String curr) {
        this.curr = curr;
    }

    public static PriceResponseDTO fromDomain(Price price) {
        return new PriceResponseDTO(price.getProductId(),
                price.getBrandId(),
                price.getAmount(),
                price.getCurr(),
                price.getStartDate(),
                price.getEndDate());
    }

}
