package com.vuongkma.products.services;

import com.vuongkma.products.dto.ProductRequestDTO;
import com.vuongkma.products.entities.CategoryProductEntity;
import com.vuongkma.products.entities.ProductEntity;
import com.vuongkma.products.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryService categoryService;
    public Page<ProductEntity> search(String search, Pageable pageable) {
        if (search == null || search.isBlank()) {
            return productRepository.findAll(pageable);
        }
        return productRepository.searchByName(search, pageable);
    }
    @Transactional
    public ProductEntity create(ProductRequestDTO productRequestDTO){
        //handle entity
        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(productRequestDTO.getName());
        productEntity.setDescription(productRequestDTO.getDescription());
        productEntity.setPrice(productRequestDTO.getPrice());
        productEntity.setStockQuantity(productRequestDTO.getStock_quantity());
        productEntity.setStatus(productRequestDTO.getStatus().name());
        productEntity.setThumbnail(productRequestDTO.getThumbnail().get(0));
        productEntity.setImages(String.join(",", productRequestDTO.getImages()));
        if(!productRequestDTO.getCategory().isEmpty()){
            Set<CategoryProductEntity> categories = new HashSet<>();
            categories.add(categoryService.findOne(Long.valueOf(productRequestDTO.getCategory())));
            productEntity.setCategories(categories);
        }

        return this.productRepository.save(productEntity);
    }
}
