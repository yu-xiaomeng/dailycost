package com.xiaomeng.dailycost.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class CategoryDto {
    @NotNull(message="category name must not be null")
    @Size(min=4, max=50, message="category name length must between 4 and 20")
    private String name;

    @NotNull(message="type must not be null")
    @Pattern(regexp="^EXPENSE$|^INCOME$",message="type must be EXPENSE or INCOME")
    private String type;

    @NotNull(message="category icon must not be null")
    @Size(min=4, max=100, message="category name length must between 4 and 100")
    private String iconId;
}
