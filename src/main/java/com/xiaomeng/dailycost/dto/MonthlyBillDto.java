package com.xiaomeng.dailycost.dto;

import lombok.Data;

@Data
public class MonthlyBillDto {
    private String date;
    private Double expense;
    private Double income;
    private Double balance;
}
