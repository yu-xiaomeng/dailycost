package com.xiaomeng.dailycost.dto;

import com.xiaomeng.dailycost.domain.BillDetails;
import lombok.Data;

import java.util.List;

@Data
public class BillDto {
    private String date;
    private Double expense;
    private Double income;
    private List<BillDetails> billDetails;
}
