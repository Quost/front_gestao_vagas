package io.github.mqdev.front_gestao_vagas.modules.candidate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobDTO {
    private UUID id;
    private String title;
    private String description;
    private String benefits;
    private String level;
    private String salary;
    private String location;
    private String requirements;
    private UUID companyId;
    private LocalDateTime createdAt;
}
