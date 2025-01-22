package com.example.capitoletechnicaltest.controller;

import com.example.capitoletechnicaltest.dto.PriceResponseDTO;
import com.example.capitoletechnicaltest.service.PriceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * REST controller for managing price-related requests.
 * Exposes endpoints for retrieving applicable prices based on input criteria.
 */
@RestController
@RequestMapping("/api/prices")
public class PriceController {

    /**
     * Service layer to handle the business logic for price management.
     */
    private final PriceService priceService;

    /**
     * Constructs a new {@link PriceController} with the specified {@link PriceService}.
     *
     * @param priceService The service responsible for retrieving applicable prices.
     */
    public PriceController(PriceService priceService) {
        this.priceService = priceService;
    }

    /**
     * Endpoint to retrieve the applicable price for a given product, brand, and application date.
     * Accepts query parameters for the brand ID, product ID, and application date.
     * Returns the price with the highest priority that matches the criteria.
     *
     * @param brandId         The ID of the brand to filter by.
     * @param productId       The ID of the product to filter by.
     * @param applicationDate The date and time to check for applicable prices, in ISO_LOCAL_DATE_TIME format.
     * @return A {@link ResponseEntity} containing the applicable {@link PriceResponseDTO} if found,
     *         or a 404 Not Found status if no price matches the criteria.
     */
    @GetMapping
    public ResponseEntity<PriceResponseDTO> getPrice(
            @RequestParam int brandId,
            @RequestParam int productId,
            @RequestParam String applicationDate) {

        // Parse the applicationDate parameter to a LocalDateTime object
        LocalDateTime dateTime = LocalDateTime.parse(applicationDate, DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        // Retrieve the applicable price using the PriceService
        Optional<PriceResponseDTO> price = priceService.getApplicablePrice(brandId, productId, dateTime);

        // Return the price if found, or a 404 response if not
        return price.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
