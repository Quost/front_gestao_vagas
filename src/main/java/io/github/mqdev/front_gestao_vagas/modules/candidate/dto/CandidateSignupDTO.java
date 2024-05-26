package io.github.mqdev.front_gestao_vagas.modules.candidate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CandidateSignupDTO {
    
    private String name;
    private String username;
    private String password;
    private String email;
    private String description;
}
