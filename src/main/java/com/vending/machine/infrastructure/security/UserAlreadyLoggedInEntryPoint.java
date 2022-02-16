package com.vending.machine.infrastructure.security;

import com.vending.machine.api.model.ErrorResponse;
import com.vending.machine.application.util.JsonUtil;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserAlreadyLoggedInEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(
            HttpServletRequest request, HttpServletResponse response, AuthenticationException authException
    ) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_CONFLICT);
        ErrorResponse errorResponse = ErrorResponse.anErrorResponse(authException.getMessage());
        response.getOutputStream().println(JsonUtil.toString(errorResponse));
    }
}
