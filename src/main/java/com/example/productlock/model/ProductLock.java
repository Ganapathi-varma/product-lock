package com.example.productlock.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "product_locks", 
       uniqueConstraints = @UniqueConstraint(columnNames = {"productNumber", "version"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductLock {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String productNumber;
    
    @Column(nullable = false)
    private String version;
    
    @Column(nullable = false)
    private boolean locked;
    
    private String lockedBy;
    
    private String reason;
    
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
