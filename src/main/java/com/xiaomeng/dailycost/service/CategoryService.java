package com.xiaomeng.dailycost.service;

import com.xiaomeng.dailycost.base.ReturnCode;
import com.xiaomeng.dailycost.domain.BillRepository;
import com.xiaomeng.dailycost.domain.Category;
import com.xiaomeng.dailycost.domain.CategoryIconRepository;
import com.xiaomeng.dailycost.domain.CategoryRepository;
import com.xiaomeng.dailycost.dto.CategoryDto;
import com.xiaomeng.dailycost.exception.BusinessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private CategoryRepository categoryRepository;
    private CategoryIconRepository categoryIconRepository;
    private BillRepository billRepository;

    public CategoryService(CategoryRepository categoryRepository, CategoryIconRepository categoryIconRepository, BillRepository billRepository) {
        this.categoryRepository = categoryRepository;
        this.categoryIconRepository = categoryIconRepository;
        this.billRepository = billRepository;
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

    public Optional<Category> findById(String id) {
        return categoryRepository.findById(id);
    }

    public List<Category> findAllByUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return categoryRepository.findAllByUser(username);
    }

    @Transactional(rollbackOn = Exception.class)
    public void delete(String id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Category> found = categoryRepository.findById(id);
        if(found.isPresent()) {
            if(found.get().getCreatedBy().equals(username)) {
                categoryRepository.deleteById(id);
                billRepository.deleteByCategoryId(id);
                return;
            }
            throw new BusinessException(ReturnCode.RC_NO_DATA_ACCESS_AUTHRITY);
        }
        throw new BusinessException(ReturnCode.RC_ID_NOT_EXIST);
    }
}
