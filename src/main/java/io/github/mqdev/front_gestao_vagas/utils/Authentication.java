package io.github.mqdev.front_gestao_vagas.utils;

import java.util.List;

import lombok.Data;

@Data
public class Authentication {
    private String access_token;
    private List<String> roles;
    private Long expires_in;
}
