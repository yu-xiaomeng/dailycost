package com.xiaomeng.dailycost.service;

import com.xiaomeng.dailycost.base.ReturnCode;
import com.xiaomeng.dailycost.domain.Category;
import com.xiaomeng.dailycost.domain.CategoryIconRepository;
import com.xiaomeng.dailycost.domain.CategoryRepository;
import com.xiaomeng.dailycost.dto.CategoryDto;
import com.xiaomeng.dailycost.exception.BusinessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    private CategoryRepository categoryRepository;
    private CategoryIconRepository categoryIconRepository;

    public CategoryService(CategoryRepository categoryRepository, CategoryIconRepository categoryIconRepository) {
        this.categoryRepository = categoryRepository;
        this.categoryIconRepository = categoryIconRepository;
    }

    public String create(CategoryDto categoryDto) throws BusinessException{
        Category category = new Category();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        category.setCreatedBy(username);

        if(categoryRepository.findByName(categoryDto.getName(), username).isPresent()) {
            throw new BusinessException(ReturnCode.RC_CATEGORY_NAME_EXIST);
        } else {
            category.setName(categoryDto.getName());
        }

        category.setType(categoryDto.getType());

        if (categoryIconRepository.findById(categoryDto.getIconId()).isPresent()) {
            category.setIconId(categoryDto.getIconId());
        } else {
            throw new BusinessException(ReturnCode.RC_CATEGORY_ICON_NOT_EXIST);
        }

        return categoryRepository.save(category).getId();
    }
}
