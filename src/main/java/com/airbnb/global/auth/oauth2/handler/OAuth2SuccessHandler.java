package com.airbnb.global.auth.oauth2.handler;

import com.airbnb.domain.member.dto.response.LogInResponse;
import com.airbnb.global.auth.jwt.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response
        , Authentication authentication) throws IOException {

        String token = jwtUtil.createAccessToken(authentication);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        String jsonString = new ObjectMapper().writeValueAsString(
            new LogInResponse(token, "refresh_token"));
        response.getWriter().write(jsonString);
    }
}
