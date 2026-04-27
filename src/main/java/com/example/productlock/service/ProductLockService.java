package com.example.productlock.service;

import com.example.productlock.dto.ProductLockRequest;
import com.example.productlock.dto.ProductLockResponse;
import com.example.productlock.exception.ProductLockNotFoundException;
import com.example.productlock.model.ProductLock;
import com.example.productlock.repository.ProductLockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductLockService {
    
    private final ProductLockRepository repository;
    
    /**
     * Create a new product lock
     */
    @Transactional
    public ProductLockResponse createLock(ProductLockRequest request) {
        // Check if lock already exists
        if (repository.existsByProductNumberAndVersion(request.getProductNumber(), request.getVersion())) {
            throw new IllegalStateException(
                String.format("Lock already exists for product %s version %s", 
                    request.getProductNumber(), request.getVersion()));
        }
        
        ProductLock lock = ProductLock.builder()
                .productNumber(request.getProductNumber())
                .version(request.getVersion())
                .locked(request.isLocked())
                .lockedBy(request.getLockedBy())
                .reason(request.getReason())
                .build();
        
        return ProductLockResponse.fromEntity(repository.save(lock));
    }
    
    /**
     * Get lock by product number and version
     */
    public ProductLockResponse getLock(String productNumber, String version) {
        ProductLock lock = repository.findByProductNumberAndVersion(productNumber, version)
                .orElseThrow(() -> new ProductLockNotFoundException(productNumber, version));
        return ProductLockResponse.fromEntity(lock);
    }
    
    /**
     * Get lock by ID
     */
    public ProductLockResponse getLockById(Long id) {
        ProductLock lock = repository.findById(id)
                .orElseThrow(() -> new ProductLockNotFoundException(id));
        return ProductLockResponse.fromEntity(lock);
    }
    
    /**
     * Get all locks for a product number
     */
    public List<ProductLockResponse> getLocksByProductNumber(String productNumber) {
        return repository.findByProductNumber(productNumber).stream()
                .map(ProductLockResponse::fromEntity)
                .toList();
    }
    
    /**
     * Get all locks
     */
    public List<ProductLockResponse> getAllLocks() {
        return repository.findAll().stream()
                .map(ProductLockResponse::fromEntity)
                .toList();
    }
    
    /**
     * Update an existing lock
     */
    @Transactional
    public ProductLockResponse updateLock(String productNumber, String version, ProductLockRequest request) {
        ProductLock lock = repository.findByProductNumberAndVersion(productNumber, version)
                .orElseThrow(() -> new ProductLockNotFoundException(productNumber, version));
        
        lock.setLocked(request.isLocked());
        lock.setLockedBy(request.getLockedBy());
        lock.setReason(request.getReason());
        
        return ProductLockResponse.fromEntity(repository.save(lock));
    }
    
    /**
     * Delete a lock
     */
    @Transactional
    public void deleteLock(String productNumber, String version) {
        if (!repository.existsByProductNumberAndVersion(productNumber, version)) {
            throw new ProductLockNotFoundException(productNumber, version);
        }
        repository.deleteByProductNumberAndVersion(productNumber, version);
    }
    
    /**
     * Check if a product version is locked
     */
    public boolean isLocked(String productNumber, String version) {
        return repository.findByProductNumberAndVersion(productNumber, version)
                .map(ProductLock::isLocked)
                .orElse(false);
    }
}
