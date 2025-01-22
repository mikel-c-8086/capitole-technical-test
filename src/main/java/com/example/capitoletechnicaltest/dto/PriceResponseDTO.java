package com.example.capitoletechnicaltest.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for price response.
 */
public class PriceResponseDTO {
    private int productId;
    private int brandId;
    private BigDecimal price;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String curr;

    // Constructors
    public PriceResponseDTO() {
        // Empty constructor
    }

    public PriceResponseDTO(int productId, int brandId, BigDecimal price, String curr, LocalDateTime startDate,
            LocalDateTime endDate) {
        this.productId = productId;
        this.brandId = brandId;
        this.price = price;
        this.curr = curr;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Getters and Setters
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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

}
