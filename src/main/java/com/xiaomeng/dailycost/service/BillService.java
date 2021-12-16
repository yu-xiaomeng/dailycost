package com.xiaomeng.dailycost.service;

import com.xiaomeng.dailycost.base.ReturnCode;
import com.xiaomeng.dailycost.domain.*;
import com.xiaomeng.dailycost.dto.BillDto;
import com.xiaomeng.dailycost.dto.BillStatDto;
import com.xiaomeng.dailycost.dto.MonthlyBillDto;
import com.xiaomeng.dailycost.exception.BusinessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class BillService {
    private BillRepository billRepository;
    private CategoryRepository categoryRepository;

    public BillService(BillRepository billRepository, CategoryRepository categoryRepository) {
        this.billRepository = billRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional(rollbackOn = Exception.class)
    public String create(BillDto billDto) throws Exception {
        Bill bill = new Bill();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        bill.setCreatedBy(username);

        String categoryId = billDto.getCategoryId();
        String type = billDto.getType();

        if(!categoryRepository.findByIdUser(categoryId,username).isPresent()) {
            throw new BusinessException(ReturnCode.RC_CATEGORY_ID_NOT_EXIST);
        }

        if(categoryRepository.findByIdTypeUser(categoryId, type, username).isPresent()) {
            bill.setType(type);
        } else throw new BusinessException(ReturnCode.RC_CATEGORY_NOT_MATCH);

        bill.setCategoryId(categoryId);
        bill.setAmount(billDto.getAmount());
        bill.setNote(billDto.getNote());
        bill.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(billDto.getDate()));

        bill.setCreatedTime(System.currentTimeMillis());
        bill.setUpdatedTime(System.currentTimeMillis());

        return billRepository.saveAndFlush(bill).getId();

    }

    public MonthlyBillDto findMonthlyBill(Date date) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Double expense = billRepository.monthlyBillStat(date, "EXPENSE", username);
        Double income = billRepository.monthlyBillStat(date, "INCOME", username);

        MonthlyBillDto m = new MonthlyBillDto();
        m.setDate(new SimpleDateFormat("yyyy-MM").format(date));

        if (expense == null && income == null) {
            return m;
        } else if (expense == null || income == null) {
            expense = (expense == null) ? 0 : expense;
            income = (income == null) ? 0 : income;
        }

        m.setExpense(expense);
        m.setIncome(income);
        m.setBalance((double) Math.round((income-expense)*100)/100);

        return m;
    }

    public List<MonthlyBillDto> findYearlyBill(String year) throws ParseException {
        List<MonthlyBillDto> yearlyBill = new ArrayList<>();

        for (int i = 12; i >= 1; i--) {
            String dateStr = year + "-" + i;
            Date monthDate = new SimpleDateFormat("yyyy-MM").parse(dateStr);

            yearlyBill.add(findMonthlyBill(monthDate));
        }

        return yearlyBill;
    }

    public BillStatDto findBillByDay(Date date) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Bill> bills = billRepository.findByDay(date, username);

//        Map<String, Object> map = new HashMap<>();
        BillStatDto billStatDto = new BillStatDto();

        Double expense = 0.0;
        Double income = 0.0;
        for (Bill bill: bills) {
            if(bill.getType().equals("EXPENSE")) {
                expense += bill.getAmount();
            } else if(bill.getType().equals("INCOME")) {
                income += bill.getAmount();
            }
        }
        billStatDto.setDate(new SimpleDateFormat("yyyy-MM-dd").format(date));
        billStatDto.setExpense(expense);
        billStatDto.setIncome(income);
        billStatDto.setBill(bills);
        return billStatDto;
    }

    public Optional<Bill> findById(String id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Bill> foundBillDetails = billRepository.findById(id);
        if (foundBillDetails.isPresent()) {
            if(foundBillDetails.get().getCreatedBy().equals(username)) {
                return foundBillDetails;
            }
            throw new BusinessException(ReturnCode.RC_NO_DATA_ACCESS_AUTHRITY);
        }
        throw new BusinessException(ReturnCode.RC_ID_NOT_EXIST);
    }

    @Transactional(rollbackOn = Exception.class)
    public String update(BillDto billDto) throws ParseException {
        Optional<Bill> existedBillDetails = billRepository.findById(billDto.getId());
        if (existedBillDetails.isPresent()) {
            Bill bill = new Bill();
            bill.setId(billDto.getId());

            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            if (username.equals(existedBillDetails.get().getCreatedBy()) == false) {
                throw new BusinessException(ReturnCode.RC_NO_DATA_ACCESS_AUTHRITY);
            }
            bill.setCreatedBy(username);

            String categoryId = billDto.getCategoryId();
            String type = billDto.getType();

            if(categoryRepository.findByIdTypeUser(categoryId, type, username).isPresent()) {
                bill.setType(type);
            } else throw new BusinessException(ReturnCode.RC_CATEGORY_NOT_MATCH);

            bill.setCategoryId(categoryId);
            bill.setAmount(billDto.getAmount());
            bill.setNote(billDto.getNote());
            bill.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(billDto.getDate()));

            bill.setCreatedTime(existedBillDetails.get().getCreatedTime());
            bill.setUpdatedTime(System.currentTimeMillis());

            return billRepository.saveAndFlush(bill).getId();
        }
        throw new BusinessException(ReturnCode.RC_ID_NOT_EXIST);
    }

    public void delete(String id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Bill> found = billRepository.findById(id);
        if(found.isPresent()) {
            if(found.get().getCreatedBy().equals(username)) {
                billRepository.deleteById(id);
                return;
            }
            throw new BusinessException(ReturnCode.RC_NO_DATA_ACCESS_AUTHRITY);
        }
        throw new BusinessException(ReturnCode.RC_ID_NOT_EXIST);
    }
}
