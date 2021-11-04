package com.xiaomeng.dailycost.dto;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class UserLoginDto {
    @Size(min=4, max=50, message="username length must between 4 and 50")
    private String username;
    @Size(min=6, max=20, message = "password length must between 6 and 20")
    private String password;
}
