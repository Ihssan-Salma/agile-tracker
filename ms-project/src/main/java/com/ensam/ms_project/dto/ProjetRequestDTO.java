package com.ensam.ms_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjetRequestDTO {
    private String nom;
    private LocalDate dateDebutPlanifiee;
    private LocalDate dateFinPlanifiee;
    private float capaciteSprint;
}
