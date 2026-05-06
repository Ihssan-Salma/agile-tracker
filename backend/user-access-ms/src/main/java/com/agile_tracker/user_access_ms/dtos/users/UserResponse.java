package com.agile_tracker.user_access_ms.dtos.users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private Integer id;
    private String nom;
    private String prenom;
    private String email;
    private String username;
    private String competences;
    private Float capaciteHebdo;
    private Float disponibilite;
}
