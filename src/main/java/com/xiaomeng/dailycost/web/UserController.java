package com.xiaomeng.dailycost.web;

import com.xiaomeng.dailycost.dto.UserLoginDto;
import com.xiaomeng.dailycost.dto.UserSignupDto;
import com.xiaomeng.dailycost.exception.BusinessException;
import com.xiaomeng.dailycost.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/user")
    public String create(@Valid @RequestBody UserSignupDto user) throws BusinessException {
        return userService.create(user);
    }

    @PostMapping("/login")
    public String login(@Valid @RequestBody UserLoginDto user) throws BusinessException{

        return userService.login(user.getUsername(), user.getPassword());

    }

    @GetMapping("/user")
    public Map<String, Object> userProfile() {
        Map<String, Object> map = new HashMap<>();
        map.put("avatar", userService.getProfile());
        return map;
    }
}
