package com.ensam.ms_project.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Projet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer projetId;
    private String nom;
    private LocalDate dateDebutPlanifiee;
    private LocalDate dateFinPlanifiee;
    private LocalDate dateDebutReelle;
    private LocalDate  dateFinReelle;
    private float capaciteSprint;
    @OneToMany(mappedBy = "projet")
    List<MembreProjet> membre; //MembreProjet qui contient clé etrangere (va voir l'attribut projet mappdby)
}
