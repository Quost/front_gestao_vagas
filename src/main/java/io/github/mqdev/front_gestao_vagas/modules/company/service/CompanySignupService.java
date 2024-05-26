package io.github.mqdev.front_gestao_vagas.modules.company.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import io.github.mqdev.front_gestao_vagas.modules.company.dto.CompanySignupDTO;

@Service
public class CompanySignupService {
    
    public String signup(CompanySignupDTO companySignupDTO) {
        RestTemplate restTemplate = new RestTemplate();

        String url = "http://34.82.61.35:8080/company/";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CompanySignupDTO> request = new HttpEntity<>(companySignupDTO, headers);

        return restTemplate.postForObject(url, request, String.class);
    }
}
