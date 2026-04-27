package com.example.productlock.controller;

import com.example.productlock.dto.ProductLockRequest;
import com.example.productlock.dto.ProductLockResponse;
import com.example.productlock.service.ProductLockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/product-locks")
@RequiredArgsConstructor
public class ProductLockController {
    
    private final ProductLockService service;
    
    /**
     * Create a new product lock
     * POST /api/v1/product-locks
     */
    @PostMapping
    public ResponseEntity<ProductLockResponse> createLock(@Valid @RequestBody ProductLockRequest request) {
        ProductLockResponse response = service.createLock(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    /**
     * Get all locks
     * GET /api/v1/product-locks
     */
    @GetMapping
    public ResponseEntity<List<ProductLockResponse>> getAllLocks() {
        return ResponseEntity.ok(service.getAllLocks());
    }
    
    /**
     * Get lock by ID
     * GET /api/v1/product-locks/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductLockResponse> getLockById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getLockById(id));
    }
    
    /**
     * Get lock by product number and version
     * GET /api/v1/product-locks/product/{productNumber}/version/{version}
     */
    @GetMapping("/product/{productNumber}/version/{version}")
    public ResponseEntity<ProductLockResponse> getLock(
            @PathVariable String productNumber,
            @PathVariable String version) {
        return ResponseEntity.ok(service.getLock(productNumber, version));
    }
    
    /**
     * Get all locks for a product number
     * GET /api/v1/product-locks/product/{productNumber}
     */
    @GetMapping("/product/{productNumber}")
    public ResponseEntity<List<ProductLockResponse>> getLocksByProduct(@PathVariable String productNumber) {
        return ResponseEntity.ok(service.getLocksByProductNumber(productNumber));
    }
    
    /**
     * Check if a product version is locked
     * GET /api/v1/product-locks/product/{productNumber}/version/{version}/status
     */
    @GetMapping("/product/{productNumber}/version/{version}/status")
    public ResponseEntity<Map<String, Boolean>> checkLockStatus(
            @PathVariable String productNumber,
            @PathVariable String version) {
        boolean locked = service.isLocked(productNumber, version);
        return ResponseEntity.ok(Map.of("locked", locked));
    }
    
    /**
     * Update a lock
     * PUT /api/v1/product-locks/product/{productNumber}/version/{version}
     */
    @PutMapping("/product/{productNumber}/version/{version}")
    public ResponseEntity<ProductLockResponse> updateLock(
            @PathVariable String productNumber,
            @PathVariable String version,
            @Valid @RequestBody ProductLockRequest request) {
        return ResponseEntity.ok(service.updateLock(productNumber, version, request));
    }
    
    /**
     * Delete a lock
     * DELETE /api/v1/product-locks/product/{productNumber}/version/{version}
     */
    @DeleteMapping("/product/{productNumber}/version/{version}")
    public ResponseEntity<Void> deleteLock(
            @PathVariable String productNumber,
            @PathVariable String version) {
        service.deleteLock(productNumber, version);
        return ResponseEntity.noContent().build();
    }
}
