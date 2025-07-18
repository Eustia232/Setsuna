package com.setsuna.controller;

import com.setsuna.LoginVO;
import com.setsuna.constants.MessageConstant;
import com.setsuna.dto.LoginDTO;
import com.setsuna.dto.RegisterDTO;
import com.setsuna.entity.UserInfo;
import com.setsuna.result.Result;
import com.setsuna.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public Result<?> login(@RequestBody LoginDTO user) {
        LoginVO token = authService.login(user);
        return Result.success(token);
    }

    @PostMapping("/register")
    public Result<?> register(@RequestBody RegisterDTO user) {
        authService.register(user);
        return Result.success(MessageConstant.REGISTER_SUCCESS);
    }

    @GetMapping("/getInfo")
    public Result<?> getInfo() {
        UserInfo info = authService.getInfo();
        return Result.success(info);
    }

}
