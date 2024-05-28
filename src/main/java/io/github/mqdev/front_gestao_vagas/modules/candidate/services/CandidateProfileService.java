package io.github.mqdev.front_gestao_vagas.modules.candidate.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;

import io.github.mqdev.front_gestao_vagas.modules.candidate.dto.CandidateProfileDTO;

import java.util.Map;

@Service
public class CandidateProfileService {

    @Value("${host.api.gestao.vagas}")
    private String hostAPIGestaoVagas;

    public CandidateProfileDTO getCandidateProfile(String token) {
        
        RestTemplate restTemplate = new RestTemplate();

        String url = hostAPIGestaoVagas.concat("/candidate/");

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(headers);
        try{
        return restTemplate.exchange(url, HttpMethod.GET, request, CandidateProfileDTO.class).getBody();
        } catch (Unauthorized e) {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "Token inválido");
        }
    }
    
}
