package com.vuongkma.products.repositories;

import com.vuongkma.products.entities.CategoryProductEntity;
import com.vuongkma.products.entities.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity,Long> {
    @Query("SELECT c FROM ProductEntity c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<ProductEntity> searchByName(@Param("search") String search, Pageable pageable);
}
