package io.github.mqdev.front_gestao_vagas.modules.candidate.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class CandidateProfileService {

    public String getCandidateProfile(String token) {
        RestTemplate restTemplate = new RestTemplate();

        String url = "http://34.82.61.35:8080/candidate/";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(headers);

        return restTemplate.exchange(url, HttpMethod.GET, request, String.class).getBody();
    }
    
}
