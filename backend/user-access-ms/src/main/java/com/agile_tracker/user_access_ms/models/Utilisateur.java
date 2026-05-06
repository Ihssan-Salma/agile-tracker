package com.agile_tracker.user_access_ms.models;

/**
 * @author salma
 **/
import jakarta.persistence.*;
import lombok.*;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDateTime;

@Entity
@Table(name = "utilisateurs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    private String nom;

    @NotBlank
    private String prenom;

    @Email
    @NotBlank
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank
    @Column(unique = true, nullable = false)
    private String username;

    @NotBlank
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    private String token;

    @Column(name = "token_expiration")
    private LocalDateTime tokenExpiration;

    @Column(name = "competences")
    private String competences;

    @NotNull
    @PositiveOrZero
    @Column(name = "capacite_hebdo")
    private Float capaciteHebdo;

    @NotNull
    @PositiveOrZero
    private Float disponibilite;
}