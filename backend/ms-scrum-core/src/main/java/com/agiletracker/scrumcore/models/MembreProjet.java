package com.agiletracker.scrumcore.models;
import jakarta.persistence.*;
/**
 * @author salma
 **/
@Entity @Table(name = "membres_projet")
public class MembreProjet {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long projetId;      // Lien vers Projet
    private Long utilisateurId; // Lien externe vers Utilisateur
    private String role;
}
