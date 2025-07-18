package com.setsuna.service.impl;

import com.setsuna.LoginVO;
import com.setsuna.constants.MessageConstant;
import com.setsuna.dto.LoginDTO;
import com.setsuna.dto.RegisterDTO;
import com.setsuna.entity.User;
import com.setsuna.entity.UserInfo;
import com.setsuna.exception.ConflictException;
import com.setsuna.mapper.UserMapper;
import com.setsuna.security.entity.LoginUser;
import com.setsuna.service.AuthService;
import com.setsuna.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public void register(RegisterDTO registrant) {
        try {
            User user = User
                    .builder()
                    .username(registrant.getUsername())
                    .password(passwordEncoder.encode(registrant.getPassword()))
                    .build();
            userMapper.insertUser(user);
        } catch (DuplicateKeyException e) {
            throw new ConflictException(MessageConstant.ACCOUNT_EXISTS_ERROR, e);
        }

    }

    @Override
    public LoginVO login(LoginDTO user) {
        Authentication authentication;
        try {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
            authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        } catch (BadCredentialsException e) {
            throw new ConflictException(MessageConstant.ACCOUNT_EXISTS_ERROR, e);
        }
        LoginUser principal = (LoginUser) authentication.getPrincipal();
        Integer id = principal.getId();
        String jwt = jwtUtil.createJWT(id.toString());
        return LoginVO
                .builder()
                .token(jwt)
                .build();
    }

    @Override
    public UserInfo getInfo() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        Integer id = Integer.valueOf(authentication
                .getPrincipal()
                .toString());
        return userMapper.selectUserInfoByUserId(id);
    }

}
