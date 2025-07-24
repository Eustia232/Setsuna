package com.setsuna.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.setsuna.LoginVO;
import com.setsuna.constants.MessageConstant;
import com.setsuna.dto.LoginDTO;
import com.setsuna.dto.RegisterDTO;
import com.setsuna.entity.UserInfo;
import com.setsuna.result.Result;
import com.setsuna.service.AuthService;
import com.setsuna.utils.HtmlUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

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
        return Result.success(MessageConstant.LOGIN_SUCCESS, token);
    }

    @PostMapping("/register")
    public Result<?> register(@RequestBody RegisterDTO user) {
        authService.register(user);
        return Result.success(MessageConstant.REGISTER_SUCCESS, null);
    }

    @GetMapping("/getInfo")
    public Result<?> getInfo() {
        UserInfo info = authService.getInfo();
        return Result.success(info);
    }

    @GetMapping("/login/github")
    public void loginWithGithub(HttpServletResponse response) throws IOException {
        response.sendRedirect("/oauth2/authorization/github");
    }
//    @GetMapping("/login/callback")
//    public Result<?> loginWithGithubCallback() throws IOException {
//        LoginVO loginVO = authService.loginWith();
//        return Result.success(MessageConstant.LOGIN_SUCCESS,loginVO);
//    }

    @GetMapping("/login/callback")
    public void loginWithGithubCallback(HttpServletResponse response) throws IOException {
        LoginVO loginVO = authService.loginWith();
        ObjectMapper objectMapper = new ObjectMapper();
        String loginVOJson = objectMapper.writeValueAsString(loginVO);
        String html = HtmlUtil.loginHtml(loginVOJson);
        response.setContentType("text/html;charset=UTF-8");
        response
                .getWriter()
                .write(html);
    }

}
