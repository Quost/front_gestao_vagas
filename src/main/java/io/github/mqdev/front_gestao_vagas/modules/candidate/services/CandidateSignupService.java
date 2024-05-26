package io.github.mqdev.front_gestao_vagas.modules.candidate.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import io.github.mqdev.front_gestao_vagas.modules.candidate.dto.CandidateSignupDTO;

@Service
public class CandidateSignupService {
    
    public void signup(CandidateSignupDTO candidateSignupDTO) {
        RestTemplate restTemplate = new RestTemplate();

        String url = "http://34.82.61.35:8080/candidate/";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CandidateSignupDTO> request = new HttpEntity<>(candidateSignupDTO, headers);

        restTemplate.postForObject(url, request, String.class);
    }
}
