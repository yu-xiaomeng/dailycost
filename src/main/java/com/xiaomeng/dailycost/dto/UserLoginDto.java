package com.xiaomeng.dailycost.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserLoginDto {
    @NotNull(message="username must bot be null")
    @Size(min=4, max=50, message="username length must between 4 and 50")
    private String username;

    @NotNull(message="password must bot be null")
    @Size(min=6, max=20, message = "password length must between 6 and 20")
    private String password;
}
