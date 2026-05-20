package com.agile_tracker.user_access_ms.dtos.invites;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InviteUserRequest {

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    @NotBlank(message = "Le prénom est obligatoire")
    private String prenom;

    @Email(message = "Email invalide")
    @NotBlank(message = "L'email est obligatoire")
    private String email;

    @NotBlank(message = "Le nom d'utilisateur est obligatoire")
    private String username;

    @NotBlank
    @Size(min = 8, max = 72, message = "Le mot de passe doit contenir entre 8 et 72 caractères")
    private String password;

    private String competences;

    @PositiveOrZero
    private Float capaciteHebdo;

    @PositiveOrZero
    private Float disponibilite;

    @NotNull(message = "L'ID du projet est obligatoire pour l'affectation")
    private Integer projetId;

    @NotBlank(message = "Le nom du projet est nécessaire pour l'email d'invitation")
    private String projectName;

    @NotBlank(message = "Le rôle dans le projet est obligatoire")
    private String role;
}