// src/main/java/com/example/productlock/dto/ProductLockResponse.java
package com.example.productlock.dto;

import com.example.productlock.model.ProductLock;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class ProductLockResponse {
    private Long id;
    private String productNumber;
    private String version;
    private boolean locked;
    private String lockedBy;
    private String reason;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public static ProductLockResponse fromEntity(ProductLock entity) {
        return ProductLockResponse.builder()
                .id(entity.getId())
                .productNumber(entity.getProductNumber())
                .version(entity.getVersion())
                .locked(entity.isLocked())
                .lockedBy(entity.getLockedBy())
                .reason(entity.getReason())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
