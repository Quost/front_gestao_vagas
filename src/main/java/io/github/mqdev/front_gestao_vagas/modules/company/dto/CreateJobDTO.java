package io.github.mqdev.front_gestao_vagas.modules.company.dto;

import lombok.Data;

@Data
public class CreateJobDTO {
    private String description;
    private String benefits;
    private String level;
}
