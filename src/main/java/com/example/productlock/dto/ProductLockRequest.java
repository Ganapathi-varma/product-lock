package com.example.productlock.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
@Data
public class ProductLockRequest {
    
    @NotBlank(message = "Product number is required")
    private String productNumber;
    
    @NotBlank(message = "Version is required")
    private String version;
    
    private boolean locked = true;
    
    private String lockedBy;
    
    private String reason;
}