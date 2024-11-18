package com.vuongkma.products.controllers;

import com.vuongkma.products.dto.CategoryProductDTO;
import com.vuongkma.products.entities.CategoryProductEntity;
import com.vuongkma.products.helpers.APIHelper;
import com.vuongkma.products.helpers.ResponseFormat;
import com.vuongkma.products.helpers.enums.StatusEnum;
import com.vuongkma.products.services.CategoryService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(APIHelper.restRoot+"categories")
@Slf4j
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ModelMapper modelMapper;


    @GetMapping
    public ResponseEntity<Object> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CategoryProductEntity> result = categoryService.search(search, pageable);
        Map<String, Object> data = new HashMap<>();
        var items = result.getContent();
        data.put("items",items);
        data.put("currentPage", result.getNumber());
        data.put("totalItems", result.getTotalElements());
        data.put("totalPages", result.getTotalPages());
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseFormat.build(data, "Data fetched successfully with search and pagination.")
        );
    }
    private String capitalizeFirstLetter(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(path = "{id}")
    @ResponseBody
    public ResponseEntity<Object> findOne(@PathVariable("id") Long id) {
        var data = this.categoryService.findOne(id);
        return ResponseEntity.ok(ResponseFormat.build(
                data,
                "Congrats, the data information has been fetched successfully."
        ));

    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping(path = "{id}")
    @ResponseBody
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        try {
            var status = this.categoryService.delete(id);
            if (!status) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("We could not delete this data");
            }

            return ResponseEntity.ok(
                    ResponseFormat.build(id, "The data has been deleted successfully.")
            );
        } catch (IllegalStateException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    @PutMapping(path = "{id}")
    @ResponseBody
    public ResponseEntity<Object> update(
            @PathVariable("id") Long id,
            @Valid @RequestBody CategoryProductDTO categoryProductDTO
    ) {
        CategoryProductEntity categoryProductEntity = this.modelMapper.map(categoryProductDTO, CategoryProductEntity.class);

        var user = this.categoryService.update(id, categoryProductEntity);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseFormat.build(
                user,
                "Congrats, the data has been updated with the id " + user.getId() + "  successfully."
        ));
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<Object> create(@Valid @RequestBody CategoryProductDTO categoryProductDTO) {
        //check status
        if (categoryProductDTO.getStatus()!=null){
            categoryProductDTO.setStatus(categoryProductDTO.getStatus().equals("publish")?StatusEnum.PUBLISH.toString():StatusEnum.DRAFT.toString());
        }
        CategoryProductEntity categoryProductEntity = this.modelMapper.map(categoryProductDTO, CategoryProductEntity.class);
        var data = this.categoryService.insert(categoryProductEntity);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseFormat.build(
                        data.getId(),
                        "Congrats, the data has been created with the id " + data.getId() + "  successfully."
                )
        );
    }

}
