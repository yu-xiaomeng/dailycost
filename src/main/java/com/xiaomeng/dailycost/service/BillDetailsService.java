package com.xiaomeng.dailycost.service;

import com.xiaomeng.dailycost.base.ReturnCode;
import com.xiaomeng.dailycost.domain.*;
import com.xiaomeng.dailycost.dto.BillDetailsDto;
import com.xiaomeng.dailycost.dto.BillDto;
import com.xiaomeng.dailycost.exception.BusinessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class BillDetailsService {
    private BillDetailsRepository billDetailsRepository;
    private CategoryRepository categoryRepository;

    public BillDetailsService(BillDetailsRepository billDetailsRepository, CategoryRepository categoryRepository) {
        this.billDetailsRepository = billDetailsRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional(rollbackOn = Exception.class)
    public String create(BillDetailsDto billDetailsDto) throws Exception {
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

    public List<BillDetails> findByMonth(Date date) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return billDetailsRepository.findByDate(date, username);
    }

    public BillDto findBillByDay(Date date) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<BillDetails> billDetails = billDetailsRepository.findByDay(date, username);

//        Map<String, Object> map = new HashMap<>();
        BillDto billDto = new BillDto();

        Double expense = 0.0;
        Double income = 0.0;
        for (BillDetails bill: billDetails) {
            if(bill.getType().equals("EXPENSE")) {
                expense += bill.getAmount();
            } else if(bill.getType().equals("INCOME")) {
                income += bill.getAmount();
            }
        }
        billDto.setDate(new SimpleDateFormat("yyyy-MM-dd").format(date));
        billDto.setExpense(expense);
        billDto.setIncome(income);
        billDto.setBillDetails(billDetails);
        return billDto;
    }

    public Optional<BillDetails> findById(String id) {
        //Todo: user can only access their own data
        Optional<BillDetails> foundBillDetails = billDetailsRepository.findById(id);
        if (foundBillDetails.isPresent()) {
            return foundBillDetails;
        }
        throw new BusinessException(ReturnCode.RC_ID_NOT_EXIST);
    }

    @Transactional(rollbackOn = Exception.class)
    public String update(BillDetailsDto billDetailsDto) throws ParseException {
        Optional<BillDetails> existedBillDetails = billDetailsRepository.findById(billDetailsDto.getId());
        if (existedBillDetails.isPresent()) {
            BillDetails billDetails = new BillDetails();
            billDetails.setId(billDetailsDto.getId());

            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            if (username.equals(existedBillDetails.get().getCreatedBy()) == false) {
                throw new BusinessException(ReturnCode.RC_NO_DATA_ACCESS_AUTHRITY);
            }
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

            billDetails.setCreatedTime(existedBillDetails.get().getCreatedTime());
            billDetails.setUpdatedTime(System.currentTimeMillis());

            return billDetailsRepository.saveAndFlush(billDetails).getId();
        }
        throw new BusinessException(ReturnCode.RC_ID_NOT_EXIST);
    }

    public void delete(String id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<BillDetails> found = billDetailsRepository.findById(id);
        if(found.isPresent()) {
            if(found.get().getCreatedBy().equals(username)) {
                billDetailsRepository.deleteById(id);
                return;
            }
            throw new BusinessException(ReturnCode.RC_NO_DATA_ACCESS_AUTHRITY);
        }
        throw new BusinessException(ReturnCode.RC_ID_NOT_EXIST);
    }
}
