package com.vending.machine.util;

import lombok.RequiredArgsConstructor;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

@RequiredArgsConstructor(staticName = "bearerToken")
public class BearerTokenRequestPostProcessor implements RequestPostProcessor {

    private final String token;

    @Override
    public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
        request.addHeader("Authorization", "Bearer " + this.token);
        return request;
    }

}
