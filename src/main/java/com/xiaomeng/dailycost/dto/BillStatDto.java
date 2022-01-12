package com.xiaomeng.dailycost.dto;

import com.xiaomeng.dailycost.domain.Bill;
import lombok.Data;

import java.util.List;

@Data
public class BillStatDto {
    private String date;
    private Double expense;
    private Double income;
    private List<BillCategoryDto> bill;

}
