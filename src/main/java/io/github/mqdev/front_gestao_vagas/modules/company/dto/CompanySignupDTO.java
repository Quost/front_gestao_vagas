package io.github.mqdev.front_gestao_vagas.modules.company.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanySignupDTO {
    
    private String name;
    private String email;
    private String username;
    private String password;
    private String description;
    private String website;
}
