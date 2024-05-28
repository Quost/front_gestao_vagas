package io.github.mqdev.front_gestao_vagas.modules.company.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import io.github.mqdev.front_gestao_vagas.modules.candidate.dto.JobDTO;

@Service
public class ListAllCompanyJobsService {

    @Value("${host.api.gestao.vagas}")
    private String hostAPIGestaoVagas;
    
    public List<JobDTO> listAllCompanyJobs(String token) {
        RestTemplate restTemplate = new RestTemplate();

        String url = hostAPIGestaoVagas.concat("/company/job/");

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        var request = new HttpEntity<>(headers);

        ParameterizedTypeReference<List<JobDTO>> responseType = new ParameterizedTypeReference<List<JobDTO>>() {};

        var result = restTemplate.exchange(url, HttpMethod.GET, request, responseType);
        return result.getBody();
    }
}
