package com.agiletracker.useraccess.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author salma
 **/
@Entity
@Table(name = "utilisateurs")
@Getter
@Setter
@NoArgsConstructor
public class Utilisateur {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    @Column(unique = true)
    private String username;
    private String passwordHash;
    private String token;
    private LocalDateTime tokenExpiration;
    private String competences;
    private Float capaciteHebdo;
    private Float disponibilite;
}