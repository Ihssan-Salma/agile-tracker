package com.ensam.mssprint.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;



// Indique que cette classe est une table en base de données
@Entity

// Lombok génère automatiquement les getters, setters, equals, hashCode, toString
@Data

// Lombok génère un constructeur sans arguments (requis par JPA)
@NoArgsConstructor

// Lombok génère un constructeur avec tous les arguments
@AllArgsConstructor
public class Sprint {

    // Clé primaire auto-incrémentée
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Nom du sprint (ex: "Sprint 1")
    private String nom;

    // Date de début prévue au moment de la planification
    private LocalDate dateDebutPlanifiee;

    // Date de fin prévue au moment de la planification
    private LocalDate dateFinPlanifiee;

    // Date de début réelle — renseignée par le Scrum Master au démarrage
    private LocalDate dateDebutReelle;

    // Date de fin réelle — renseignée par le Scrum Master à la clôture
    private LocalDate dateFinReelle;

    // Nombre de points d'effort planifiés pour ce sprint
    private Integer storyPoints;

    // false = sprint en cours ou planifié, true = sprint clôturé
    private Boolean termine = false;

    // Description de l'objectif du sprint
    private String objectif;

    // Référence logique vers ms-projets (pas de FK réelle en base)
    // Ce champ est une Ext_ID : ms-sprints appelle ms-projets via Feign
    // pour valider que ce projet existe avant de créer le sprint
    private Integer projetId;
}
