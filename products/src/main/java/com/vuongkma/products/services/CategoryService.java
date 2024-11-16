package com.vuongkma.products.services;

import com.vuongkma.products.entities.CategoryProductEntity;
import com.vuongkma.products.exceptions.NotFoundException;
import com.vuongkma.products.repositories.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private KafkaProducerService kafkaProducerService;
    public boolean existsById(Long id) {
        return this.categoryRepository.existsById(id);
    }

    public CategoryProductEntity insert(CategoryProductEntity data) {
        return this.categoryRepository.save(data);
    }

    @Transactional
    public CategoryProductEntity update(Long id, CategoryProductEntity data) {
        var entity = this.categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("This user does not exists"));

        if (data.getName() != null && !data.getName().isBlank()) {
            entity.setName(data.getName());
        }

        if (data.getStatus() != null) {
            entity.setStatus(data.getStatus());
        }

        if (data.getParent_id() != null) {
            entity.setParent_id(data.getParent_id());
        }
        this.categoryRepository.save(entity);
        return entity;
    }

    @Transactional
    public boolean delete(Long id) {
        this.categoryRepository.deleteById(id);

        return true;
    }

    public Page<CategoryProductEntity> search(String search, Pageable pageable) {
        if (search == null || search.isBlank()) {
            return categoryRepository.findAll(pageable);
        }
        return categoryRepository.searchByName(search, pageable);
    }
    public List<CategoryProductEntity> findAll() {
        List<String> message = new ArrayList<>();
        message.add("cccc");
        kafkaProducerService.sendMessage(message);
        return this.categoryRepository.findAll();
    }

    public CategoryProductEntity findOne(Long id) {
        return this.categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Oops! This data does not exists"));
    }
}
