package com.vuongkma.products.repositories;

import com.vuongkma.products.entities.CategoryProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryProductEntity, Long> {

}
