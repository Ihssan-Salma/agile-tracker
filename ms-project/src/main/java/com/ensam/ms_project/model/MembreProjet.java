package com.ensam.ms_project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class MembreProjet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer memprojetId;

    @ManyToOne
    @JoinColumn(name="projet_id") //membre qui contient clé etrangere
    @JsonIgnore
    private Projet projet;
    private int utilisateurId;
    @Enumerated(EnumType.STRING)
    private Role role;

}
