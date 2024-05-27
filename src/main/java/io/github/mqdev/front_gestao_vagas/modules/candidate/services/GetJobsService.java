package io.github.mqdev.front_gestao_vagas.modules.candidate.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;
import org.springframework.web.util.UriComponentsBuilder;

import io.github.mqdev.front_gestao_vagas.modules.candidate.dto.JobDTO;

import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.List;

@Service
public class GetJobsService {

    @Value("@{host.api.gestao.vagas}")
    private String hostAPIGestaoVagas;

    public List<JobDTO> getJobs(String token, String filter) {
        RestTemplate restTemplate = new RestTemplate();

        String url = hostAPIGestaoVagas.concat("/candidate/job");

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(headers);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("filter", filter);

        ParameterizedTypeReference<List<JobDTO>> responseType = new ParameterizedTypeReference<List<JobDTO>>() {
        };

        try {
            return restTemplate.exchange(builder.toUriString(), HttpMethod.GET, request, responseType).getBody();
        } catch (Unauthorized e) {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "Token inválido");
        }
    }
}
