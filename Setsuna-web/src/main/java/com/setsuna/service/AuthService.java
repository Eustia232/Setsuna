package com.setsuna.service;

import com.setsuna.LoginVO;
import com.setsuna.dto.LoginDTO;
import com.setsuna.dto.RegisterDTO;
import com.setsuna.entity.UserInfo;

import java.io.IOException;

public interface AuthService {

    void register(RegisterDTO user);


    LoginVO login(LoginDTO user);

    UserInfo getInfo();

    LoginVO loginWith() throws IOException;

}
