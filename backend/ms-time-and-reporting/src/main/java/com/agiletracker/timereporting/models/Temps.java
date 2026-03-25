package com.agiletracker.timereporting.models;

import jakarta.persistence.*;

import java.time.LocalDate;

/**
 * @author salma
 **/
@Entity
@Table(name = "temps")
public class Temps {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private Float nbHeures;
    private Boolean valide;

    // IDs Externes (Microservices différents)
    private Long projetId;
    private Long tacheId;
    private Long utilisateurId;
}
