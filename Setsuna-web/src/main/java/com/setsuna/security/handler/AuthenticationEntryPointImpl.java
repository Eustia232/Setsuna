package com.setsuna.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.setsuna.constants.MessageConstant;
import com.setsuna.result.Result;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Result<Void> result = Result.error(MessageConstant.INVALID_TOKEN_ERROR);
        PrintWriter out = response.getWriter();
        out.write(new ObjectMapper().writeValueAsString(result));

    }

}
