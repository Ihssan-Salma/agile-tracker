package com.ensam.ms_project.dto;

import lombok.Data;

import java.net.Inet4Address;
import java.time.LocalDate;

@Data
public class ProjetResponseDTO {
        private Integer projetId;
        private String nom;
        private LocalDate dateDebutPlanifiee;
        private LocalDate dateFinPlanifiee;
        private float capaciteSprint;

}
