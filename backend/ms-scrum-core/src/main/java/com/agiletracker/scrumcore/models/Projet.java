package com.agiletracker.scrumcore.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

/**
 * @author salma
 **/
@Entity
@Table(name = "projets")
public class Projet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private LocalDate dateDebutPlanifiee;
    private LocalDate dateFinPlanifiee;
    private LocalDate dateDebutReelle;
    private LocalDate dateFinReelle;
    private Float capaciteSprint;

    @OneToMany(mappedBy = "projet")
    private List<Sprint> sprints;
}