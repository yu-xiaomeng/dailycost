package com.xiaomeng.dailycost.web;

import com.xiaomeng.dailycost.dto.CategoryDto;
import com.xiaomeng.dailycost.exception.BusinessException;
import com.xiaomeng.dailycost.service.CategoryService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

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
}
