package com.xiaomeng.dailycost.service;

import com.xiaomeng.dailycost.domain.BillDetails;
import com.xiaomeng.dailycost.domain.BillDetailsRepository;
import com.xiaomeng.dailycost.dto.BillDetailsDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Service
public class BillDetailsService {
    private BillDetailsRepository billDetailsRepository;

    public BillDetailsService(BillDetailsRepository billDetailsRepository) {
        this.billDetailsRepository = billDetailsRepository;
    }

    @Transactional(rollbackOn = Exception.class)
    public String create(BillDetailsDto billDetailsDto) throws ParseException {
        BillDetails billDetails = new BillDetails();

        billDetails.setCategoryId(billDetailsDto.getCategoryId());
        billDetails.setAmount(billDetailsDto.getAmount());
        billDetails.setType(billDetailsDto.getType());
        billDetails.setNote(billDetailsDto.getNote());
        billDetails.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(billDetailsDto.getDate()));

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        billDetails.setCreatedBy(username);

        billDetails.setCreatedTime(System.currentTimeMillis());
        billDetails.setUpdatedTime(System.currentTimeMillis());
        return billDetailsRepository.saveAndFlush(billDetails).getId();

    }
}
