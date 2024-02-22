package com.knulinkmoa.auth.handler;

import com.knulinkmoa.auth.service.google.GoogleOAuth2Service;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomOAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final GoogleOAuth2Service googleOAuth2Service;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        String accessToken = googleOAuth2Service.getGoogleAccessToken(request.getParameter("code"));
        String userInfo = googleOAuth2Service.getUserInfo(accessToken);

        System.out.println("userInfo = " + userInfo);
    }
}
