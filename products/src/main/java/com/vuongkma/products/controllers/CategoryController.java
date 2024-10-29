package com.vuongkma.products.controllers;

import com.vuongkma.products.dto.CategoryProductDTO;
import com.vuongkma.products.entities.CategoryProductEntity;
import com.vuongkma.products.helpers.APIHelper;
import com.vuongkma.products.helpers.ResponseFormat;
import com.vuongkma.products.services.CategoryService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(APIHelper.restRoot+"categories")
@Slf4j
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    @ResponseBody
    public ResponseEntity<Object> findAll() {
        var data = this.categoryService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseFormat.build(data, "Congrats, all data have been fetched successfully.")
        );
    }

    @GetMapping(path = "{id}")
    @ResponseBody
    public ResponseEntity<Object> findOne(@PathVariable("id") Long id) {
        var data = this.categoryService.findOne(id);
        return ResponseEntity.ok(ResponseFormat.build(
                data,
                "Congrats, the data information has been fetched successfully."
        ));

    }

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
