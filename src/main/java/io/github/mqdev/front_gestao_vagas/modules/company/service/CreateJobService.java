package io.github.mqdev.front_gestao_vagas.modules.company.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import io.github.mqdev.front_gestao_vagas.modules.company.dto.CreateJobDTO;

@Service
public class CreateJobService {
    public String createJob(CreateJobDTO job, String token) {
        RestTemplate restTemplate = new RestTemplate();

        String url = "http://34.82.61.35:8080/company/job/";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CreateJobDTO> request = new HttpEntity<>(job, headers);

        var result = restTemplate.postForObject(url, request, String.class);
        return result;
    }
}
