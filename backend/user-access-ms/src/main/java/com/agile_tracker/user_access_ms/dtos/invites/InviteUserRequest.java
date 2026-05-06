package com.agile_tracker.user_access_ms.dtos.invites;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InviteUserRequest {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 8, max = 72)
    private String password;

    private String username;

    private String competences;

    @PositiveOrZero
    private Float capaciteHebdo;

    @PositiveOrZero
    private Float disponibilite;
}
