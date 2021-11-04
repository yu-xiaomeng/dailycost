package com.xiaomeng.dailycost.service;

import com.xiaomeng.dailycost.domain.BillDetails;
import com.xiaomeng.dailycost.domain.BillDetailsRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class BillDetailsService {
    private BillDetailsRepository billDetailsRepository;

    public BillDetailsService(BillDetailsRepository billDetailsRepository) {
        this.billDetailsRepository = billDetailsRepository;
    }

    @Transactional( rollbackOn = Exception.class)
    public BillDetails create(BillDetails billDetails) {
        billDetails.setCreatedBy("molly");
        billDetails.setCreatedTime(System.currentTimeMillis());
        billDetails.setUpdatedTime(System.currentTimeMillis());
        BillDetails createdBillDetails = billDetailsRepository.save(billDetails);
        return createdBillDetails;
    }
}
