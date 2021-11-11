package com.xiaomeng.dailycost.web;

import com.xiaomeng.dailycost.domain.CategoryIcon;
import com.xiaomeng.dailycost.service.CategoryIconService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/category/icon")
public class CategoryIconController {
    @Autowired
    private CategoryIconService categoryIconService;

    @GetMapping("/list")
    public List<CategoryIcon> findAllIcon() {
        return categoryIconService.getAllCategoryIcon();
    }

    @GetMapping("/{id}")
    public Optional<CategoryIcon> findById(@PathVariable String id) {
        return categoryIconService.findById(id);
    }
}
