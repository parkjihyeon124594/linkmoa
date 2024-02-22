package com.knulinkmoa.auth.service.google;

import com.fasterxml.jackson.databind.JsonNode;
import com.knulinkmoa.auth.exception.OAuth2Errorcode;
import com.knulinkmoa.global.exception.GlobalException;
import com.nimbusds.oauth2.sdk.http.HTTPRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class GoogleOAuth2Service {

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String GOOGLE_CLIENT_ID;
    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String GOOGLE_CLIENT_SERCET;
    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String GOOGLE_REDIRECT_URI;
    @Value("${spring.security.oauth2.client.registration.google.scope}")
    private String GOOGLE_SCOPE;

    private String GOOGLE_GRANT_TYPE = "authorization_code";
    private String GOOGLE_TOKEN_URI = "https://oauth2.googleapis.com/token";
    private String GOOGLE_RESOURCE_URI = "https://www.googleapis.com/oauth2/v2/userinfo";

    private final RestTemplate restTemplate;


    /**
     * code를 보내서 accessToken을 획득
     * @param authcode Authorization code
     *
     * @return accessToken
     */
    public String getGoogleAccessToken(String authcode) {

        MultiValueMap<String, String > params = new LinkedMultiValueMap<>();

        params.add("grant_type", GOOGLE_GRANT_TYPE);
        params.add("code", authcode);
        params.add("redirect_uri", GOOGLE_REDIRECT_URI);
        params.add("client_id", GOOGLE_CLIENT_ID);
        params.add("client_secret", GOOGLE_CLIENT_SERCET);
        params.add("scope", GOOGLE_SCOPE);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);

        ResponseEntity<JsonNode> response = restTemplate.exchange(
                GOOGLE_TOKEN_URI,
                HttpMethod.POST,
                entity,
                JsonNode.class
        );

        JsonNode responseBody = response.getBody();

        if (response.getStatusCode() != HttpStatus.OK || responseBody == null) {
            throw new GlobalException(OAuth2Errorcode.FAILED_TO_GET_ACCESS_TOKEN);
        }

        System.out.println("token : " + responseBody.get("access_token").asText());

        return responseBody.get("access_token").asText();
    }

    public String getUserInfo(String acccesToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer + " + acccesToken);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        String body = restTemplate.exchange(
                GOOGLE_RESOURCE_URI,
                HttpMethod.GET,
                entity,
                String.class
        ).getBody();


        return body;
    }
}
