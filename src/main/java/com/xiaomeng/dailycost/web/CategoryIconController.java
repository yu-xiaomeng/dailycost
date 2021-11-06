package com.xiaomeng.dailycost.web;

import com.xiaomeng.dailycost.domain.CategoryIcon;
import com.xiaomeng.dailycost.service.CategoryIconService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category/icon/list")
public class CategoryIconController {
    @Autowired
    private CategoryIconService categoryIconService;

    @GetMapping
    public List<CategoryIcon> findAllIcon() {
        return categoryIconService.getAllCategoryIcon();
    }
}
