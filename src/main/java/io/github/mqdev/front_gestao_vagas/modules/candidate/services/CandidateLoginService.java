package io.github.mqdev.front_gestao_vagas.modules.candidate.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import io.github.mqdev.front_gestao_vagas.utils.Authentication;

import java.util.HashMap;
import java.util.Map;

@Service
public class CandidateLoginService {

    public Authentication login(String username, String password) {
        RestTemplate restTemplate = new RestTemplate();

        String url = "http://34.82.61.35:8080/candidate/auth";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> data = new HashMap<>();
        data.put("username", username);
        data.put("password", password);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(data);

        return restTemplate.postForObject(url, request, Authentication.class);
    }
    
}
