package com.example.capitoletechnicaltest.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entity class representing the Price information for a product.
 * This class maps to the database table storing pricing details.
 */
@Entity
@Table(name = "PRICES")
public class Price {

    /**
     * Unique identifier for the price entry.
     * Generated automatically using an identity strategy.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Identifier of the brand associated with the price.
     */
    private int brandId;

    /**
     * Start date and time when the price becomes applicable.
     */
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime startDate;

    /**
     * End date and time when the price ceases to be applicable.
     */
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime endDate;

    /**
     * Identifier of the price list or tariff associated with the product.
     */
    private int priceList;

    /**
     * Unique identifier for the product.
     */
    private int productId;

    /**
     * Priority level of the price.
     * Higher values take precedence when multiple prices overlap.
     */
    private int priority;

    /**
     * The monetary value of the price.
     */
    @Column(name = "PRICE")
    private BigDecimal amount;

    /**
     * ISO currency code associated with the price.
     */
    private String curr;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
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

    public int getPriceList() {
        return priceList;
    }

    public void setPriceList(int priceList) {
        this.priceList = priceList;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurr() {
        return curr;
    }

    public void setCurr(String curr) {
        this.curr = curr;
    }

    /**
     * Returns a string representation of the Price object.
     *
     * @return A string with all the Price fields and their values.
     */
    @Override
    public String toString() {
        return "Price{" +
                "id=" + id +
                ", brandId=" + brandId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", priceList=" + priceList +
                ", productId=" + productId +
                ", priority=" + priority +
                ", price=" + amount +
                ", curr='" + curr + '\'' +
                '}';
    }
}