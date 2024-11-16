package com.vuongkma.products.repositories;

import com.vuongkma.products.entities.CategoryProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryProductEntity, Long> {
    @Query("SELECT c FROM CategoryProductEntity c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<CategoryProductEntity> searchByName(@Param("search") String search, Pageable pageable);

    @Query("SELECT c FROM CategoryProductEntity c")
    Page<CategoryProductEntity> findAllWithCustomQuery(Pageable pageable);
}
