package com.agile_tracker.user_access_ms.dtos.auth;

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
public class RegisterRequest {

    @NotBlank
    private String nom;

    @NotBlank
    private String prenom;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String username;

    @NotBlank
    @Size(min = 8, max = 72)
    private String password;

    private String competences;

    @NotNull
    @PositiveOrZero
    private Float capaciteHebdo;

    @NotNull
    @PositiveOrZero
    private Float disponibilite;
}
