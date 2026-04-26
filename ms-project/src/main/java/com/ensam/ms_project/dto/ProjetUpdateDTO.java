package com.ensam.ms_project.dto;

import lombok.Data;

import java.time.LocalDate;
@Data
public class ProjetUpdateDTO {
    private LocalDate dateDebutReelle;
    private LocalDate dateFinReelle;
    private LocalDate dateDebutPlanifiee;
    private LocalDate dateFinPlanifiee;
}
