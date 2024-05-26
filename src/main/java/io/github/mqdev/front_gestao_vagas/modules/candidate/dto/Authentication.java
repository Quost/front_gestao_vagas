package io.github.mqdev.front_gestao_vagas.modules.candidate.dto;

import java.util.List;

import lombok.Data;

@Data
public class Authentication {
    private String accessToken;
    private List<String> roles;
    private Long expiresAt;
}
