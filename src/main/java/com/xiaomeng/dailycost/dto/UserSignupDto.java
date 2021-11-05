package com.xiaomeng.dailycost.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class UserSignupDto {
    @NotNull(message="username must not be null")
    @Size(min=4, max=50, message="username length must between 4 and 50")
    private String username;

    @NotNull(message="password must not be null")
    @Size(min=6, max=20, message = "password length must between 6 and 20")
    private String password;

    @NotNull(message="email must not be null")
    @Size(min=3, max=64, message = "email must between 3 and 64")
    @Pattern(regexp = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$", message = "email format is invalid")
    private String email;

    @Size(max=200, message = "avatar url length must less than 200")
    private String avatar;
}
