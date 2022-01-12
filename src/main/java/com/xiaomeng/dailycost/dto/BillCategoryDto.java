package com.xiaomeng.dailycost.dto;

import com.xiaomeng.dailycost.domain.Bill;
import lombok.Data;

@Data
public class BillCategoryDto {
    private String categoryName;
    private String categoryIconUrl;
    private Bill bill;
}
