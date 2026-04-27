package com.example.productlock.repository;

import com.example.productlock.model.ProductLock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductLockRepository extends JpaRepository<ProductLock, Long> {
    
    Optional<ProductLock> findByProductNumberAndVersion(String productNumber, String version);
    
    List<ProductLock> findByProductNumber(String productNumber);
    
    List<ProductLock> findByLocked(boolean locked);
    
    boolean existsByProductNumberAndVersion(String productNumber, String version);
    
    void deleteByProductNumberAndVersion(String productNumber, String version);
}
