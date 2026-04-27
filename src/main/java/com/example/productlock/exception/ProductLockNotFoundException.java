package com.example.productlock.exception;
public class ProductLockNotFoundException extends RuntimeException {
    
    public ProductLockNotFoundException(String productNumber, String version) {
        super(String.format("Product lock not found for product '%s' version '%s'", productNumber, version));
    }
    
    public ProductLockNotFoundException(Long id) {
        super(String.format("Product lock not found with id '%d'", id));
    }
}