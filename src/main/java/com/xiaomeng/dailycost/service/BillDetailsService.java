package com.xiaomeng.dailycost.service;

import com.xiaomeng.dailycost.base.ReturnCode;
import com.xiaomeng.dailycost.domain.*;
import com.xiaomeng.dailycost.dto.BillDetailsDto;
import com.xiaomeng.dailycost.exception.BusinessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BillDetailsService {
    private BillDetailsRepository billDetailsRepository;
    private CategoryRepository categoryRepository;
    private BillRepository billRepository;

    public BillDetailsService(BillDetailsRepository billDetailsRepository, CategoryRepository categoryRepository, BillRepository billRepository) {
        this.billDetailsRepository = billDetailsRepository;
        this.categoryRepository = categoryRepository;
        this.billRepository = billRepository;
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

        String billDetailsId = billDetailsRepository.saveAndFlush(billDetails).getId();

        // update or insert to bill
        Bill bill = billRepository.findByDate(billDetails.getDate(), username);

        if (bill != null) {
            if( type.equals("EXPENSE") ) {
                bill.setExpense(bill.getExpense() + billDetails.getAmount());
            } else {bill.setIncome(bill.getIncome() + billDetails.getAmount());}
            bill.setBalance(bill.getIncome() - bill.getExpense());
            billRepository.save(bill);
        } else if( bill == null ){
            Bill newBill = new Bill();
            newBill.setTimeDimension("day");
            newBill.setDate(billDetails.getDate());
            if( type.equals("EXPENSE") ) {
                newBill.setExpense(billDetails.getAmount());
                newBill.setIncome(0.00);
            } else if(type.equals("INCOME")) {
                newBill.setExpense(0.00);
                newBill.setIncome(billDetails.getAmount());
            }
            newBill.setBalance(newBill.getIncome() - newBill.getExpense());
            newBill.setUsername(username);
            billRepository.save(newBill);
        }

        return billDetailsId;

    }

    public List<BillDetails> findByMonth(Date date) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return billDetailsRepository.findByDate(date, username);
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
}
