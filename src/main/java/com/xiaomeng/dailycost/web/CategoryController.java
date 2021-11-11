package com.xiaomeng.dailycost.web;

import com.xiaomeng.dailycost.domain.Category;
import com.xiaomeng.dailycost.dto.CategoryDto;
import com.xiaomeng.dailycost.exception.BusinessException;
import com.xiaomeng.dailycost.service.CategoryService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public Map<String, Object> create(@Valid @RequestBody CategoryDto categoryDto) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", categoryService.create(categoryDto));
        return map;
    }

    @GetMapping("/{id}")
    public Optional<Category> findById(@PathVariable String id) {
        return categoryService.findById(id);
    }
}
