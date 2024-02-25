package com.knulinkmoa.auth.service.google;

import com.fasterxml.jackson.databind.JsonNode;
import com.knulinkmoa.auth.exception.OAuth2Errorcode;
import com.knulinkmoa.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
@RequiredArgsConstructor
@Slf4j
public class GoogleOAuth2Service {

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String GOOGLE_CLIENT_ID;
    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String GOOGLE_CLIENT_SECRET;
    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String GOOGLE_REDIRECT_URL;

    private String GOOGLE_TOKEN_URI = "https://oauth2.googleapis.com/token";
    private String GOOGLE_RESOURCE_URI = "https://www.googleapis.com/oauth2/v2/userinfo";

    private final WebClient webClient;

    public String getGoogleAccessToken(String authcode) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();

        formData.add("code", URLDecoder.decode(authcode, StandardCharsets.UTF_8));
        formData.add("client_id", GOOGLE_CLIENT_ID);
        formData.add("client_secret", GOOGLE_CLIENT_SECRET);
        formData.add("redirect_uri", GOOGLE_REDIRECT_URL);
        formData.add("grant_type", "authorization_code");

        log.info("code={}", authcode);

        System.out.println("authcode = " + authcode);

        JsonNode responseBody = webClient.post()
                .uri(GOOGLE_TOKEN_URI)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block(); // blocking for simplicity, consider using reactive programming

        if (responseBody == null) {
            throw new GlobalException(OAuth2Errorcode.FAILED_TO_GET_ACCESS_TOKEN);
        }

        return responseBody.get("access_token").asText();
    }

    public String getUserInfo(String accessToken) {
        String body = webClient.get()
                .uri(GOOGLE_RESOURCE_URI)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(String.class)
                .block(); // blocking for simplicity, consider using reactive programming

        return body;
    }
}
