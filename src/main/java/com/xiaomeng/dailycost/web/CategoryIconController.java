package com.xiaomeng.dailycost.web;

import com.xiaomeng.dailycost.domain.CategoryIcon;
import com.xiaomeng.dailycost.service.CategoryIconService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/category/icon")
@SecurityRequirement(name = "dailycostapi")
public class CategoryIconController {
    @Autowired
    private CategoryIconService categoryIconService;

    @Operation(summary = "get all category icon info list")
    @GetMapping("/list")
    public List<CategoryIcon> findAllIcon() {
        return categoryIconService.getAllCategoryIcon();
    }

    @Operation(summary = "get one category icon by its id")
    @GetMapping("/{id}")
    public Optional<CategoryIcon> findById(@PathVariable String id) {
        return categoryIconService.findById(id);
    }
}
