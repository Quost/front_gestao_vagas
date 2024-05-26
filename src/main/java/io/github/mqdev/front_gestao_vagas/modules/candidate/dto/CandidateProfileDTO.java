package io.github.mqdev.front_gestao_vagas.modules.candidate.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CandidateProfileDTO {

    private UUID id;
    private String name;
    private String email;
    private String username;
    private String description;
    
}
