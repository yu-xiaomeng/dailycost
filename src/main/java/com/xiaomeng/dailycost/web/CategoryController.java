package com.xiaomeng.dailycost.web;

import com.xiaomeng.dailycost.domain.Category;
import com.xiaomeng.dailycost.dto.CategoryDto;
import com.xiaomeng.dailycost.exception.BusinessException;
import com.xiaomeng.dailycost.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/category")
@SecurityRequirement(name = "dailycostapi")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Operation(summary = "create a new category")
    @PostMapping
    public Map<String, Object> create(@Valid @RequestBody CategoryDto categoryDto) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", categoryService.create(categoryDto));
        return map;
    }

    @Operation(summary = "get category info by its id")
    @GetMapping("/{id}")
    public Optional<Category> findById(@PathVariable String id) {
        return categoryService.findById(id);
    }

    @Operation(summary = "get category list")
    @GetMapping
    public List<Category> findAllByUser() {
        return categoryService.findAllByUser();
    }

    @Operation(summary = "delete one category by its id")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        categoryService.delete(id);
    }
}
