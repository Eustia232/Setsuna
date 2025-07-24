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
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

    @Autowired
    public AuthServiceImpl(PasswordEncoder passwordEncoder, UserMapper userMapper,
                           AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void register(RegisterDTO registrant) {
        try {
            User user = User
                    .builder()
                    .username(registrant.getUsername())
                    .password(passwordEncoder.encode(registrant.getPassword()))
                    .build();
            userMapper.insertUser(user);
            user = userMapper.selectUserByUsername(registrant.getUsername());
            userMapper.InsertUserInfo(UserInfo
                    .builder()
                    .user_id(user.getId())
                    .build());
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
            throw new ConflictException(MessageConstant.PASSWORD_ERROR, e);
        }
        LoginUser principal = (LoginUser) authentication.getPrincipal();
        Integer id = principal.getId();
        String jwt = jwtUtil.createJWT(id.toString());
        return LoginVO
                .builder()
                .token(jwt)
                .firstLogin(false)
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

    @Override
    public LoginVO loginWith() {
        if (!(SecurityContextHolder
                .getContext()
                .getAuthentication() instanceof OAuth2AuthenticationToken token)) {
            return null;
        }
        boolean firstLogin = false;
        String jwt = null;
        String authorizedClientRegistrationId = token.getAuthorizedClientRegistrationId();
        if (authorizedClientRegistrationId.equals("github")) {
            String username = token
                    .getPrincipal()
                    .getAttribute("login");
            User user = userMapper.selectUserByUsernameAndLoginType(username, authorizedClientRegistrationId);
            //如果该授权账号第一次登录
            if (user == null) {
                //向数据库添加该用户
                User newUser = User
                        .builder()
                        .username(username)
                        .loginType(authorizedClientRegistrationId)
                        .build();
                userMapper.insertUser(newUser);
                //把第一次登录的flag改为true，便于前端消息提示
                firstLogin = true;
                //更新用户id
                user = userMapper.selectUserByUsernameAndLoginType(username, authorizedClientRegistrationId);
                //TODO
//                //把头像保存到本地
//                String avatar_url = token
//                        .getPrincipal()
//                        .getAttribute("avatar_url");
//                String avatarLocalUrl = null;
//                if (avatar_url != null) {
//                    avatarLocalUrl = FileUtil.uploadImage(new URL(avatar_url));
//                }
                UserInfo userInfo = UserInfo
                        .builder()
                        .user_id(user.getId())
//                        .avatar(avatarLocalUrl)
                        .build();
                userMapper.InsertUserInfo(userInfo);
            }
            String id = Integer.toString(user.getId());
            jwt = jwtUtil.createJWT(id);
        }
        return LoginVO
                .builder()
                .token(jwt)
                .firstLogin(firstLogin)
                .build();
    }

}
