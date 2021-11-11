package com.xiaomeng.dailycost.service;

import com.xiaomeng.dailycost.domain.CategoryIcon;
import com.xiaomeng.dailycost.domain.CategoryIconRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryIconService {
    private CategoryIconRepository categoryIconRepository;

    public CategoryIconService(CategoryIconRepository categoryIconRepository) {
        this.categoryIconRepository = categoryIconRepository;
    }

    public List<CategoryIcon> getAllCategoryIcon() {
        return categoryIconRepository.findAll();
    }

    public Optional<CategoryIcon> findById(String id) {
        return categoryIconRepository.findById(id);
    }
}
