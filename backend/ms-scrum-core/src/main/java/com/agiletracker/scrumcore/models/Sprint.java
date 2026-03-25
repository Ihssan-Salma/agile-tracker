package com.agiletracker.scrumcore.models;

import jakarta.persistence.*;

import java.time.LocalDate;

/**
 * @author salma
 **/
@Entity
@Table(name = "sprints")
public class Sprint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private LocalDate dateDebutPlanifiee;
    private LocalDate dateFinPlanifiee;
    private LocalDate dateDebutReelle;
    private LocalDate dateFinReelle;
    private Boolean termine;
    private String objectif;

    @ManyToOne
    @JoinColumn(name = "projet_id")
    private Projet projet;
}