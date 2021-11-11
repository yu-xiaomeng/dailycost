package com.xiaomeng.dailycost.service;

import com.xiaomeng.dailycost.domain.Bill;
import com.xiaomeng.dailycost.domain.BillRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BillService {
    private BillRepository billRepository;

    public BillService(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    public Bill findByDate(Date date) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return billRepository.findByDate(date, username);
    }

    public List<Bill> findByMonth(Date date) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return billRepository.findByMonth(date, username);
    }
}
