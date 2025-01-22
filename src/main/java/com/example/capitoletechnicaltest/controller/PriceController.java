package com.example.capitoletechnicaltest.controller;

import com.example.capitoletechnicaltest.dto.PriceResponseDTO;
import com.example.capitoletechnicaltest.service.PriceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
     * or a 404 Not Found status if no price matches the criteria.
     */
    @Operation(
            summary = "Get applicable price",
            description = "Retrieves the applicable price for a product, brand, and application date."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Price retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No applicable price found"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @GetMapping
    public ResponseEntity<PriceResponseDTO> getPrice(
            @Parameter(description = "Brand ID", example = "1") @RequestParam int brandId,
            @Parameter(description = "Product ID", example = "35455") @RequestParam int productId,
            @Parameter(description = "Application Date in ISO format",
                    example = "2020-06-14T10:00:00") @RequestParam String applicationDate) {

        // Parse the applicationDate parameter to a LocalDateTime object
        LocalDateTime dateTime = LocalDateTime.parse(applicationDate, DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        // Retrieve the applicable price using the PriceService
        Optional<PriceResponseDTO> price = priceService.getApplicablePrice(brandId, productId, dateTime);

        // Return the price if found, or a 404 response if not
        return price.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
