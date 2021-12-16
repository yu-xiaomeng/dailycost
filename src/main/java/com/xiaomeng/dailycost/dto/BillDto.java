package com.xiaomeng.dailycost.dto;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class BillDto {
    private String id;

    @NotNull(message="categoryId must not be null")
    private String categoryId;

    @NotNull(message="type must not be null")
    @Pattern(regexp="^EXPENSE$|^INCOME$",message="type must be EXPENSE or INCOME")
    private String type;

    @NotNull(message="amount must not be null")
    @DecimalMin(value="0.0", message = "amount must be greater than or equal to 0.0")
    private double amount;

    @Size(max=100, message="note length must less than 100")
    private String note;

    @NotNull(message="date must not be null")
    @Pattern(regexp = "^\\d{4}-\\d{1,2}-\\d{1,2}", message="invalid date format")
    private String date;
}
