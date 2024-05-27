package io.github.mqdev.front_gestao_vagas.modules.company.service;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import io.github.mqdev.front_gestao_vagas.modules.candidate.dto.JobDTO;

@Service
public class ListAllCompanyJobsService {
    
    public List<JobDTO> listAllCompanyJobs(String token) {
        RestTemplate restTemplate = new RestTemplate();

        String url = "http://34.82.61.35:8080/company/job/";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        var request = new HttpEntity<>(headers);

        ParameterizedTypeReference<List<JobDTO>> responseType = new ParameterizedTypeReference<List<JobDTO>>() {};

        var result = restTemplate.exchange(url, HttpMethod.GET, request, responseType);
        return result.getBody();
    }
}
