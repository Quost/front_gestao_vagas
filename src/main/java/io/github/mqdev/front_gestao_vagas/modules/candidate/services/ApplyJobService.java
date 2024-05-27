package io.github.mqdev.front_gestao_vagas.modules.candidate.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApplyJobService {

    @Value("@{host.api.gestao.vagas}")
    private String hostAPIGestaoVagas;

    public String applyJob(String token, UUID jobId) {
        RestTemplate restTemplate = new RestTemplate();

        String url = hostAPIGestaoVagas.concat("/candidate/job/apply");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        HttpEntity<UUID> request = new HttpEntity<>(jobId, headers);

        return restTemplate.postForObject(url, request, String.class);
    }

}
