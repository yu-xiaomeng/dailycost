package com.xiaomeng.dailycost.service;

import com.xiaomeng.dailycost.base.ReturnCode;
import com.xiaomeng.dailycost.domain.BillDetails;
import com.xiaomeng.dailycost.domain.BillDetailsRepository;
import com.xiaomeng.dailycost.domain.CategoryRepository;
import com.xiaomeng.dailycost.dto.BillDetailsDto;
import com.xiaomeng.dailycost.exception.BusinessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class BillDetailsService {
    private BillDetailsRepository billDetailsRepository;
    private CategoryRepository categoryRepository;

    public BillDetailsService(BillDetailsRepository billDetailsRepository, CategoryRepository categoryRepository) {
        this.billDetailsRepository = billDetailsRepository;
        this.categoryRepository=categoryRepository;
    }

    @Transactional(rollbackOn = Exception.class)
    public String create(BillDetailsDto billDetailsDto) throws ParseException {
        BillDetails billDetails = new BillDetails();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        billDetails.setCreatedBy(username);

        String categoryId = billDetailsDto.getCategoryId();
        String type = billDetailsDto.getType();

        if(categoryRepository.findByIdTypeUser(categoryId, type, username).isPresent()) {
            billDetails.setType(type);
        } else throw new BusinessException(ReturnCode.RC_CATEGORY_NOT_MATCH);

        billDetails.setCategoryId(categoryId);
        billDetails.setAmount(billDetailsDto.getAmount());
        billDetails.setNote(billDetailsDto.getNote());
        billDetails.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(billDetailsDto.getDate()));

        billDetails.setCreatedTime(System.currentTimeMillis());
        billDetails.setUpdatedTime(System.currentTimeMillis());
        return billDetailsRepository.saveAndFlush(billDetails).getId();

    }

    public List<BillDetails> findCurrentMonthBills(Date date) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return billDetailsRepository.findByDate(date, username);
    }
}
