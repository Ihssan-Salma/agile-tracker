package com.agile_tracker.user_access_ms.dtos.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequest {

    private String nom;
    private String prenom;

    @Email
    private String email;

    private String username;

    @Size(min = 8, max = 72)
    private String password;

    private String competences;

    @PositiveOrZero
    private Float capaciteHebdo;

    @PositiveOrZero
    private Float disponibilite;
}
